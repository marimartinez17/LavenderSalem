package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.utils.Constants;

import static com.lavendersalem.game.utils.Constants.MIN_TIEMPO_SALTO;

public class Salem extends Player{
    private boolean dobleSalto; // Para establecer el doble salto
    // Constructor para crear a Salem
    public Salem(float x, float y) {
        super(x,y,16f,16f, 3);
        this.moviEnX = Constants.SALEM_VELOX;
        this.fuerzaSalto = Constants.SALEM_VELOY;
        this.dobleSalto = false; // No puede hacer doble salto hasta intentar el primero
        // Spritsheets
        sheetIdle = new Texture(Gdx.files.internal("sprites/salem/salem-idle-sheet.png"));
        sheetCaminarIzq = new Texture(Gdx.files.internal("sprites/salem/salem-walkleft-sheet.png"));
        sheetCaminarDer = new Texture(Gdx.files.internal("sprites/salem/salem-walkright-sheet.png"));
        sheetSaltoIzq = new Texture(Gdx.files.internal("sprites/salem/salem-jumpleft-sheet.png"));
        sheetSaltoDer = new Texture(Gdx.files.internal("sprites/salem/salem-jumpright-sheet.png"));
        sheetInteractIzq = new Texture(Gdx.files.internal("sprites/salem/salem-interactleft-sheet.png"));
        sheetInteractDer = new Texture(Gdx.files.internal("sprites/salem/salem-interactright-sheet.png"));
        // Crear animaciones en 0 porque hay una sola fila y con 0.15f tiempo de frame
        animIdle = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 16)[0]);
        animCaminarIzq = new Animation<>(0.2f, TextureRegion.split(sheetCaminarIzq, 16, 16)[0]);
        animCaminarDer = new Animation<>(0.2f, TextureRegion.split(sheetCaminarDer, 16, 16)[0]);
        animSaltarIzq = new Animation<>(0.2f, TextureRegion.split(sheetSaltoIzq, 16, 16)[0]);
        animSaltarDer = new Animation<>(0.2f, TextureRegion.split(sheetSaltoDer, 16, 16)[0]);
        animInteractuarIzq = new Animation<>(0.2f, TextureRegion.split(sheetInteractIzq, 16, 16)[0]);
        animInteractuarDer = new Animation<>(0.2f, TextureRegion.split(sheetInteractDer, 16, 16)[0]);
        // Se config el frame inicial
        currentFrame = TextureRegion.split(sheetIdle, 16, 16)[0][0];

    }
    @Override
    protected void configMov() {
        velocidad.x = 0;
        if (Gdx.input.isKeyPressed(Constants.SALEM_RIGHT)){
            velocidad.x += moviEnX;
            miraDer = true;
        }
        if (Gdx.input.isKeyPressed(Constants.SALEM_LEFT)){
            velocidad.x -= moviEnX;
            miraDer = false;
        }
        if (Gdx.input.isKeyJustPressed(Constants.SALEM_UP)) {
            tiempoBufferSalto = MAX_BUFFER_SALTO;
        }
        // Para optimizar salto
        if (tiempoBufferSalto > 0) {
            if (onSuelo) {
                velocidad.y = fuerzaSalto;
                onSuelo = false;
                dobleSalto = true; // Para permitir doble salto
                tiempoBufferSalto = 0f;
            } else if (dobleSalto) {
                velocidad.y = fuerzaSalto;
                dobleSalto = false; // Desactiva una vez hecho
                tiempoBufferSalto = 0f;
                estadoAnim = "";
            }
        }
    }

    @Override
    protected void actualizarAnimacion() {
        String nuevoEstado;

        if (!onSuelo && tiempoEnAire > MIN_TIEMPO_SALTO) {
            nuevoEstado = miraDer ? "saltoDer" : "saltoIzq";
        } else if (velocidad.x !=0) { // Si esta caminando
            nuevoEstado = miraDer ? "caminarDer" : "caminarIzq";
        } else { //Quieta
            nuevoEstado = "idle";
        }
        // Si cambió el estado, reinicia el contador
        if (!nuevoEstado.equals(estadoAnim)) {
            estadoAnim = nuevoEstado;
            timeAnimacion = 0f;
        }
        // Ahora asigna el frame
        switch (estadoAnim) {
            case "saltoDer"   -> currentFrame = animSaltarDer.getKeyFrame(timeAnimacion, false);
            case "saltoIzq"   -> currentFrame = animSaltarIzq.getKeyFrame(timeAnimacion, false);
            case "caminarDer" -> currentFrame = animCaminarDer.getKeyFrame(timeAnimacion, true);
            case "caminarIzq" -> currentFrame = animCaminarIzq.getKeyFrame(timeAnimacion, true);
            default -> { // Si el idle terminó su ciclo, resetea para que no acumule infinito
                if (animIdle.isAnimationFinished(timeAnimacion)) timeAnimacion = 0f;
                currentFrame = animIdle.getKeyFrame(timeAnimacion, true);
            }
        }
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
    }

    @Override
    public void morir(float spawnX, float spawnY) {
        super.morir(spawnX, spawnY);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    @Override
    public void draw(SpriteBatch batch) { super.draw(batch); }

    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }

    @Override
    public void dispose() {
        sheetIdle.dispose();
        sheetCaminarIzq.dispose();
        sheetCaminarDer.dispose();
        sheetSaltoIzq.dispose();
        sheetSaltoDer.dispose();
        sheetInteractIzq.dispose();
        sheetInteractDer.dispose();
    }
}
