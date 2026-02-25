package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Entity {
    // Todas las entidades posee una posicion y velocidad (x,y)
    protected Vector2 posicion;
    protected Vector2 velocidad;
    // Tamaño para las coliciones
    protected float tamWidth;
    protected float tamHeight;
    // Todos poseen un visual (sprites)
    protected Texture spriteSheet;
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
    // Hitbox para colisiones (Rectangulo)
    public Rectangle getBounds() {
        return new Rectangle(posicion.x, posicion.y, tamWidth, tamHeight);
    }
}
