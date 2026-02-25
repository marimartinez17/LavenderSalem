package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.utils.Constants;

public class Lavender extends Player{
    // Constructor (Crea a Lavender)
    public Lavender(float x, float y) {
        super(x,y,16f,32f); // Posicion (x,y) y tamaño por PPM
        this.vidas = 1;
        this.moviEnX = Constants.LAV_VELOX;
        this.fuerzaSalto = Constants.LAV_VELOY;
    }
    // Configuramos movimiento
    @Override
    protected void configMov() {
        velocidad.x = 0; // Inicializamos la velocidad como tal en el eje X
        // Cuando se mueva a la izquierda o derecha
        if (Gdx.input.isKeyPressed(Constants.LAVE_RIGHT)){
            velocidad.x += moviEnX;
        }
        if (Gdx.input.isKeyPressed(Constants.LAVE_LEFT)){
            velocidad.x += -moviEnX;
        }
        // Cuando salta aplicamos gravedad
        if (Gdx.input.isKeyPressed(Constants.LAVE_UP) && onSuelo){ // Si se presiona salta y el sprite esta en el suelo
            velocidad.y = fuerzaSalto; // Se hace el salto y se aplica gravedad
            onSuelo = false;
        }
    }

    @Override
    public void update(float delta) {
        super.update(delta); // Aplica el sistema de salto y movimiento
        /* ACA VAN LAS COLISIONES, INTERACCIONES Y DEMAS */
        // Suelo de prueba basico
        if (posicion.y <= 0){
            posicion.y = 0;
            velocidad.y = 0;
            onSuelo = true;
        }
    }
    // Llama al batch de la clase principal
    @Override
    public void draw(SpriteBatch batch) { super.draw(batch); }
    // HitBox para prueba y colisiones
    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }
}
