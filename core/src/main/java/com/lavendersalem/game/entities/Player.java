package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavendersalem.game.utils.Constants;

public abstract class Player extends Entity { // Player hereda de Entity porque es para entidades jugables
    //Atributos de Player
    protected boolean onSuelo; // Si el personaje esta en el suelo
    protected boolean vivo;
    protected int vidasInicial;
    protected int vidas; // Numero de vidas del personaje
    // Constantes físicas de cada persona (Definidas en Constants)
    protected float moviEnX; // Velocidad en carrera
    protected float fuerzaSalto; // Para el salto de cada personaje (contraresta gravedad)
    // Para optimizar el salto en mov
    protected float tiempoBufferSalto = 0f;
    protected static final float MAX_BUFFER_SALTO = 0.1f; // 100ms de ventana
    // Para dibujado (sprites)
    protected float timeAnimacion = 0f;
    // Constructor
    public Player(float x, float y, float tamWidth, float tamHeight, int vidasInicial) {
        super(x, y, tamWidth, tamHeight);
        this.onSuelo = false; // En false porque al aplicar gravedad queda sobre suelo
        this.vivo = true; // Siempre spawnea vivo
        this.vidasInicial = vidasInicial;
        this.vidas = vidasInicial;
    }
    // Para actualizar el estado y movimiento del player
    @Override
    public void update(float delta) {
        timeAnimacion += delta; // Acumula tiempo en cada frame
        if (tiempoBufferSalto > 0) tiempoBufferSalto -= delta; // Reducir buffer cada frame
        configMov();
        aplicGravedad(delta);
        actualizarAnimacion();
    }
    protected abstract void configMov(); // Cada player tiene su configuracion de Teclas
    protected abstract void actualizarAnimacion(); // Cada palyer maneja sus frames
    // Para que el player quede sobre el suelo y en caso de salto aplica gravedad
    private void aplicGravedad(float delta) {
        if(!onSuelo){
            velocidad.y += Constants.GRAVEDAD * delta;
            // Tope de velocidad de caida para evitar traspaso de tiles
            if (velocidad.y < -960f) velocidad.y = -960f;
        }
    }
    // Para el movimiento como tal (En X y en Y)
    public void movEnX(float delta) {
        posicion.x += velocidad.x * delta; // La posicion cambia si hay mov (velocidad)
    }
    public void movEnY(float delta) {
        posicion.y += velocidad.y * delta;
    }
    // Para "dibujar" el sprite
    @Override
    public void draw(SpriteBatch batch) {
        if (currentFrame != null) { // Si hay frames
            batch.draw(currentFrame, posicion.x, posicion.y, tamWidth, tamHeight);
        }
    }
    /* PARA CUANDO MUERE O RESPAWNEA */
    public void morir(float spawnX, float spawnY) {
        vidas--;
        posicion.set(spawnX, spawnY);
        velocidad.set(0, 0);
        setOnSuelo(false);
        if (vidas <= 0) vivo = false;
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
        vidas = vidasInicial;
        vivo = true;
        onSuelo = false;
    }

    // GETTERS Y SETTER PARA EL GAMESCREEN
    public boolean isOnSuelo() { return onSuelo; }
    public void setOnSuelo(boolean onSuelo) { this.onSuelo = onSuelo; }

    public boolean isVivo() { return vivo; }
    public void setVivo(boolean vivo) { this.vivo = vivo; }

    public int getVidas() { return vidas; }
    public void setVidas(int vidas) { this.vidas = vidas; }
}
