package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.utils.Constants;

public class Salem extends Player{
    private boolean dobleSalto; // Para establecer el doble salto
    // Constructor para crear a Salem
    public Salem(float x, float y) {
        super(x,y,16f,16f);
        this.vidas = 3;
        this.moviEnX = Constants.SALEM_VELOX;
        this.fuerzaSalto = Constants.SALEM_VELOY;
        this.dobleSalto = false; // No puede hacer doble salto hasta intentar el primero
    }
    @Override
    protected void configMov() {
        velocidad.x = 0;
        if (Gdx.input.isKeyPressed(Constants.SALEM_RIGHT)){
            velocidad.x += moviEnX;
        }
        if (Gdx.input.isKeyPressed(Constants.SALEM_LEFT)){
            velocidad.x -= moviEnX;
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
            }
        }
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
}
