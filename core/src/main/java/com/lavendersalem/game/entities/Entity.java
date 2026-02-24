package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

abstract class Entity { // "Plantilla" para los sprites y entities
    protected Vector2 posicion; // Para posicion y colisiones
    protected Texture texture;
    protected float velocidad;
    protected int tamWidth, tamHeight;

    // Constructor que se ejecuta al crear la entidad
    public Entity(String rutaTexture, float x, float y, float velocidad, int tamWidth, int tamHeight) {
        this.texture = new Texture(Gdx.files.internal(rutaTexture)); // Carga la imagen con la ruta
        this.posicion = new Vector2(x,y); // Posicion en x y
        this.velocidad = velocidad;
        this.tamWidth = tamWidth;
        this.tamHeight = tamHeight;
    }
    // Para movimientos de la entidad (Da la plantilla obligatoria para cada heredero de entity)
    public abstract void update(float delta);
    // Para el dibujo de la entidad
    public void draw(SpriteBatch batch) {
        batch.draw(texture, posicion.x, posicion.y,tamWidth,tamHeight); // "Dibuja" la textura en la posicion
    }
    public void dispose() { // Para liberar memoria
        texture.dispose();
    }
}
