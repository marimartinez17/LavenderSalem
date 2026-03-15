package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.utils.Constants;

public class Lavender extends Player{
    private boolean esperaRescate = false;
    // Constructor (Crea a Lavender)
    public Lavender(float x, float y) {
        super(x,y,16f,32f, 1); // Posicion (x,y) y tamaño por PPM
        this.moviEnX = Constants.LAV_VELOX;
        this.fuerzaSalto = Constants.LAV_VELOY;
    }
    // Configuramos movimiento
    @Override
    protected void configMov() {
        // Si esta esperando no mueve
        if (esperaRescate) {
            velocidad.x = 0;
            return;
        }
        velocidad.x = 0; // Inicializamos la velocidad como tal en el eje X
        if (Gdx.input.isKeyPressed(Constants.LAVE_RIGHT)){
            velocidad.x += moviEnX;
        }
        if (Gdx.input.isKeyPressed(Constants.LAVE_LEFT)){
            velocidad.x -= moviEnX;
        }
        // Si se presiona salto, guardar en buffer
        if (Gdx.input.isKeyPressed(Constants.LAVE_UP)) {
            tiempoBufferSalto = MAX_BUFFER_SALTO;
        }
        // Saltar si hay buffer activo y está en suelo
        if (tiempoBufferSalto > 0 && onSuelo) {
            velocidad.y = fuerzaSalto * 1.05f;
            onSuelo = false;
            tiempoBufferSalto = 0f;
        }
    }
    public void morirEspera (float posX, float posY) {
        // Bloquea a lavender en el sitio y activa esperaRescate
        posicion.set(posX, posY);
        velocidad.set(0,0);
        esperaRescate = true;
        vivo = true; // No muere hasta culminar contrareloj
    }
    public void recibirVida (float spawX, float spawY) {
        vidas++;
        esperaRescate = false;
        vivo = true;
        posicion.set(spawX, spawY);
        velocidad.set(0,0);
        setOnSuelo(false); // Para que mantega sobre el suelo
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
        esperaRescate = false;
    }

    @Override
    public void update(float delta) {
        super.update(delta); // Aplica el sistema de salto y movimiento
    }
    // Llama al batch de la clase principal
    @Override
    public void draw(SpriteBatch batch) { super.draw(batch); }
    // HitBox para prueba y colisiones
    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }

    // GETTERS Y SETTERS
    public boolean isEsperaRescate() { return esperaRescate; }
}
