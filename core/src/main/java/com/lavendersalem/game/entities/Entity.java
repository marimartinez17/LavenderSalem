package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    // Todas las entidades posee una posicion y velocidad (x, y)
    protected Vector2 posicion;
    protected Vector2 velocidad;
    // Tamaño para las colisiones
    protected float tamWidth;
    protected float tamHeight;
    // Todos poseen un visual (frame actual)
    protected TextureRegion currentFrame;
    // Constructor
    public Entity(float x, float y, float tamWidth, float tamHeight) {
        this.posicion = new Vector2(x,y);
        this.velocidad = new Vector2(0,0);
        this.tamWidth = tamWidth; this.tamHeight = tamHeight;
    }
    // Todas las entidades deben actualizarse (mov) y dibujarse
    public abstract void update(float delta);
    public abstract void draw(SpriteBatch batch);
    // Hitbox para colisiones (Rectángulo)
    public Rectangle getBounds() {
        return new Rectangle(posicion.x, posicion.y, tamWidth, tamHeight);
    }
    public void resetear(float spawnX, float spawnY) {
        posicion.set(spawnX, spawnY);
        velocidad.set(0,0);
    }
    // SETTERS PARA COLISIONES
    public Vector2 getPosicion() { return posicion; }
    public void setPosicionX(float x) { posicion.x = x; }
    public void setPosicionY(float y) { posicion.y = y; }

    public Vector2 getVelocidad() { return velocidad; }
    public void setVelocidadX(float velox) { velocidad.x = velox; }
    public void setVelocidadY(float veloy) { velocidad.y = veloy; }
}
