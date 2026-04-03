package com.lavendersalem.game.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.entities.*;

public class SistemaColisiones {
    private final Array<Rectangle> tilesSolidos;
    private final Array<Rectangle> tilesPeligro;
    private final Array<Enemy> enemigos;
    private final Array<Caja> cajas;

    public SistemaColisiones(Array<Rectangle> tilesSolidos, Array<Rectangle> tilesPeligro,
                             Array<Enemy> enemigos, Array<Caja> cajas) {
        this.tilesSolidos = tilesSolidos;
        this.tilesPeligro = tilesPeligro;
        this.enemigos = enemigos;
        this.cajas = cajas;
    }
    // ACTUALIZAR COLISIONES PLAYER
    public void actualizarPlayer(Player2 player, float delta, float spawnX, float spawnY) {
        if (!player.isVivo()) return;
        player.update(delta); // Para input mov y gravedad
        // Si cae demasiado muere
        if (player.getPosicion().y < -80f) {
            player.morir(spawnX, spawnY);
            return;
        }
        // En X
        player.movEnX(delta);
        Rectangle tileEnX = obtenerTileColision(player, tilesSolidos);
        if (tileEnX != null) colisionEnX(player, tileEnX);
        for (Caja c : cajas) colisionPlayerCaja(player, c);
        // En Y
        player.setOnSuelo(false);
        player.movEnY(delta);
        if (player.getPosicion().y < -80f) {
            player.morir(spawnX, spawnY);
            return;
        }
        Rectangle tileEnY = obtenerTileColision(player, tilesSolidos);
        if (tileEnY != null) colisionesEnY(player, tileEnY);
        for (Caja c : cajas) colisionPlayerCaja(player, c);
        if (verificarPeligro(player, spawnX, spawnY));
    }
    // SISTEMA DE COLISIONES BASICO
    private Rectangle obtenerTileColision(Entity entidad, Array<Rectangle> tiles) {
        for (Rectangle tile : tiles) {
            if (entidad.getBounds().overlaps(tile)){
                return tile; //Devuelve el tile con el que choca
            }
        }
        return null;
    }
    private void colisionEnX(Player2 player, Rectangle tile) {
        Rectangle perso = player.getBounds();
        // Ver de donde viene tomando el centro del tile y la pos del perso
        if (perso.x + (perso.width / 2) < tile.x + (tile.width / 2)) {
            player.setPosicionX(tile.x - perso.width);// Si viene por la izq, lo empuja
        } else {
            player.setPosicionX(tile.x + tile.width); // Lo empuja hacia la der
        }
        player.setVelocidadX(0); // Para que no pueda traspaar el tile
    }
    private void colisionesEnY(Player2 player, Rectangle tile) {
        Rectangle perso = player.getBounds();
        // Sobre el tile
        if ((perso.y + perso.height / 2) > (tile.y + tile.height / 2)) {
            player.setPosicionY(tile.y + tile.height);
            player.setVelocidadY(0); // Para que no traspase el tile
            player.setOnSuelo(true);
        } else { // Techo
            player.setPosicionY(tile.y - perso.height);
            player.setVelocidadY(0);
            player.setOnSuelo(false);// Solo se aplica gravedad
        }
    }
    // PARA TILES PELIGRO
    private boolean verificarPeligro(Player2 player, float spawnX, float spawnY) {
        Rectangle perso = player.getBounds();
        for (Rectangle peligro : tilesPeligro) {
            // Zona de peligro expandida hacia arriba hasta el nivel del suelo
            Rectangle zonaPeligro = new Rectangle( // Se puede ajustar de acuerdo a donde este el tile
                peligro.x, peligro.y, peligro.width, peligro.height
            );
            if (perso.overlaps(zonaPeligro) && (player instanceof Salem2 salem)) {
                salem.morir(spawnX, spawnY);
                return true;
            }
        }
        return false;
    }
    // PARA ENEMIGOS
    public void actualizarEnemigo(Enemy enemigo, float delta) {
        enemigo.update(delta);
        if (!enemigo.isActivo()) return;

        Rectangle tileEnX = obtenerTileColision(enemigo, tilesSolidos);
        if (tileEnX != null) {
            Rectangle enemigoBounds = enemigo.getBounds();
            if (enemigoBounds.x + (enemigoBounds.width / 2) < tileEnX.x + (tileEnX.width / 2)) {
                enemigo.setPosicionX(tileEnX.x - enemigoBounds.width);
            } else {
                enemigo.setPosicionX(tileEnX.x + tileEnX.width);
            }
            // Invertir dirección al chocar con pared
            enemigo.setVelocidadX(-enemigo.getVelocidad().x);
        }
        for (Caja c : cajas) colisionEnemigoCaja(enemigo, c);
    }
    public void verificarEnemigo(Enemy enemigo, Player2 player, float spawnX, float spawnY) {
        if (!player.isVivo() || !enemigo.isActivo()) return; // Si player esta muerto no hace nada

        if (enemigo.getBounds().overlaps(player.getBounds())) {
            if (player instanceof Lavender2) {
                player.tocadoPorEnemigo();
            } else {
                player.morir(spawnX, spawnY);
            }
        }
    }
    // PARA CAJA MOVIL
    public void actualizarCaja(Caja caja, Lavender2 lavender, float delta) {
        caja.update(delta);
        //Colisiones en x
        caja.movEnX(delta);
        Rectangle tileEnX = obtenerTileColision(caja, tilesSolidos);
        if (tileEnX != null) {
            Rectangle cajaBounds = caja.getBounds();
            if ((cajaBounds.x + cajaBounds.width / 2f) > (tileEnX.x + tileEnX.height / 2f)) {
                caja.setPosicionX(tileEnX.x - cajaBounds.width);
            } else {
                caja.setPosicionX(tileEnX.x + tileEnX.width);
            }
            caja.setVelocidadX(0);
        }
        caja.movEnY(delta);
        caja.setOnSuelo(false);
        Rectangle tileEnY = obtenerTileColision(caja, tilesSolidos);
        if (tileEnY != null) {
            Rectangle cajaBounds = caja.getBounds();
            if ((cajaBounds.y + cajaBounds.height / 2f) > (tileEnY.y + tileEnY.height / 2f)) {
                caja.setPosicionY(tileEnY.y + tileEnY.height);
                caja.setVelocidadY(0);
                caja.setOnSuelo(true);
            } else {
                caja.setPosicionY(tileEnY.y - cajaBounds.height);
                caja.setVelocidadY(0);
            }
        }
        empujeCaja(lavender, caja);
    }
    private void empujeCaja(Lavender2 lavender, Caja caja) {
        Rectangle cuerpoLav = lavender.getBounds();
        Rectangle cuerpoCaja = caja.getBounds();

        if(!cuerpoLav.overlaps(cuerpoCaja)) return;

        float centroLav = cuerpoLav.getX() + cuerpoLav.getWidth() / 2f;
        float centroCaja = cuerpoCaja.getX() + cuerpoCaja.getWidth() / 2f;

        if (centroLav < centroCaja) { //Lavender por la izq
            caja.setPosicionX(cuerpoLav.x + cuerpoLav.width);
            caja.setVelocidadX(lavender.getVelocidad().x);
        } else {
            caja.setPosicionX(cuerpoLav.x - cuerpoCaja.width);
            caja.setVelocidadX(-lavender.getVelocidad().x);
        }
        caja.setVelocidadX(0f);
        // Verificar si la caja chocó con un tile después del empuje
        Rectangle tileEnX = obtenerTileColision(caja, tilesSolidos);
        if (tileEnX != null) {
            Rectangle bounds = caja.getBounds();
            if (bounds.x + bounds.width / 2 < tileEnX.x + tileEnX.width / 2) {
                caja.setPosicionX(tileEnX.x - bounds.width);
            } else {
                caja.setPosicionX(tileEnX.x + tileEnX.width);
            }
            caja.setVelocidadX(0);
            // También detiene a Lavender para que no atraviese la caja
            lavender.setVelocidadX(0);
            lavender.setPosicionX(centroLav < centroCaja ? caja.getBounds().x - cuerpoLav.width
                : caja.getBounds().x + caja.getBounds().width);
        }
    }
    private void colisionPlayerCaja(Player2 player, Caja caja) {
        if (!player.isVivo()) return;

        Rectangle boundsPlayer = player.getBounds();
        Rectangle boundsCaja = caja.getBounds();
        if (!boundsPlayer.overlaps(boundsCaja)) return;

        float altoCaja = boundsCaja.y + boundsCaja.height;

        if (boundsPlayer.y > boundsCaja.height) {
            player.setPosicionY(altoCaja);
            player.setVelocidadY(0f);
            player.setOnSuelo(true);
        } else {
            if (player instanceof Lavender2 lavender) {
                empujeCaja(lavender, caja);
                return;
            }
            float centroPlayerX = boundsPlayer.x + boundsPlayer.width / 2f;
            float centroCajaX = boundsCaja.x + boundsCaja.width / 2f;

            if (centroPlayerX > centroCajaX) {
                player.setPosicionX(boundsCaja.x + boundsCaja.width);
            } else {
                player.setPosicionX(boundsCaja.x - boundsPlayer.width);
            }
            player.setVelocidadX(0f);
        }
    }
    private void colisionEnemigoCaja(Enemy enemigo, Caja caja) {
        if (!enemigo.isActivo()) return;
        Rectangle boundEnemigo = enemigo.getBounds();
        Rectangle boundsCaja = caja.getBounds();

        if (!boundEnemigo.overlaps(boundsCaja)) return;
        float centroEnemigo = boundEnemigo.x + boundEnemigo.width / 2f;
        float centroCaja = boundsCaja.x + boundsCaja.width / 2f;

        if (centroEnemigo > centroCaja) {
            enemigo.setPosicionX(boundsCaja.x + boundsCaja.width);
        } else {
            enemigo.setPosicionX(boundsCaja.x - boundEnemigo.width);
        }
        enemigo.setVelocidadX(-enemigo.getVelocidad().x);
    }
}
