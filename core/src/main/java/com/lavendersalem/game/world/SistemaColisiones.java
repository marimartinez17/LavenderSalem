package com.lavendersalem.game.world;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.entities.Player;

public class SistemaColisiones {
    private final Array<Rectangle> tilesSolidos;
    private final Array<Rectangle> tilesPeligro;

    public SistemaColisiones(Array<Rectangle> tilesSolidos, Array<Rectangle> tilesPeligro) {
        this.tilesSolidos = tilesSolidos;
        this.tilesPeligro = tilesPeligro;
    }

    public void actualizarPlayer(Player player, float delta, float spawnX, float spawnY) {
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

        // En Y
        player.setOnSuelo(false);
        player.movEnY(delta);
        if (player.getPosicion().y < -80f) {
            player.morir(spawnX, spawnY);
            return;
        }
        Rectangle tileEnY = obtenerTileColision(player, tilesSolidos);
        if (tileEnY != null) colisionesEnY(player, tileEnY);
        if (verificarPeligro(player, spawnX, spawnY)) return;
    }
    // SISTEMA DE COLISIONES
    private Rectangle obtenerTileColision(Player player, Array<Rectangle> tiles) {
        for (Rectangle tile : tiles) {
            if (player.getBounds().overlaps(tile)){
                return tile; //Devuelve el tile con el que choca
            }
        }
        return null;
    }
    private void colisionEnX(Player player, Rectangle tile) {
        Rectangle perso = player.getBounds();
        // Ver de donde viene tomando el centro del tile y la pos del perso
        if (perso.x + (perso.width / 2) < tile.x + (tile.width / 2)) {
            player.setPosicionX(tile.x - perso.width);// Si viene por la izq, lo empuja
        } else {
            player.setPosicionX(tile.x + tile.width); // Lo empuja hacia la der
        }
        player.setVelocidadX(0); // Para que no pueda traspaar el tile
    }
    private void colisionesEnY(Player player, Rectangle tile) {
        Rectangle perso = player.getBounds();
        // Sobre el tile
        if (perso.y + (perso.height / 2) > tile.y + (tile.height / 2)) {
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
    private boolean verificarPeligro(Player player, float spawnX, float spawnY) {
        Rectangle perso = player.getBounds();
        for (Rectangle peligro : tilesPeligro) {
            // Zona de peligro expandida hacia arriba hasta el nivel del suelo
            Rectangle zonaPeligro = new Rectangle(
                peligro.x,
                peligro.y,
                peligro.width,
                peligro.height // Se puede ajustar altura de acuerdo a donde este el tile
            );
            if (perso.overlaps(zonaPeligro)) {
                player.morir(spawnX, spawnY);
                return true;
            }
        }
        return false;
    }
}
