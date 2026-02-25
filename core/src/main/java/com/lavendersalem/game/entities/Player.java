package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavendersalem.game.utils.Constants;

public abstract class Player extends Entity { // Player hereda de Entity porque es para entidades jugables
    //Atributos de Player
    protected boolean onSuelo; // Si el personaje esta en el suelo
    protected boolean vivo; // Si esta vivo o muerto (true/false)
    protected int vidas; // Numero de vidas del personaje
    // Constantes físicas de cada persona (Definidas en Constants)
    protected float moviEnX; // Velocidad en carrera
    protected float fuerzaSalto; // Para el salto de cada personaje (contraresta gravedad)
    // Constructor
    public Player(float x, float y, float tamWidth, float tamHeight) {
        super(x, y, tamWidth, tamHeight);
        this.onSuelo = false; // En false porque al aplicar gravedad queda sobre suelo
        this.vivo = true; // Siempre spawnea vivo
    }
    // Para actualizar el estado y movimiento del player
    @Override
    public void update(float delta) {
        configMov();
        aplicGravedad(delta);
        movimiento(delta);
    }
    protected abstract void configMov(); // Cada player tiene su configuracion de movimientos
    // Para que el player quede sobre el suelo y en caso de salto aplica gravedad
    private void aplicGravedad(float delta) {
        if(!onSuelo){
            velocidad.y += Constants.GRAVEDAD * delta;
        }
    }
    // Para el movimiento como tal
    private void movimiento(float delta) {
        posicion.x += velocidad.x * delta; // La posicion cambia si hay mov (velocidad)
        posicion.y += velocidad.y * delta;
    }
    // Para "dibujar" el sprite
    @Override
    public void draw(SpriteBatch batch) {
        if (currentFrame != null) { // Si hay frames
            batch.draw(currentFrame, posicion.x, posicion.y, tamWidth, tamHeight);
        }
    }
}
