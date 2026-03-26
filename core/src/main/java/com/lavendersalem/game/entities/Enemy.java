package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Enemy extends Entity{
    protected boolean activo = true;
    protected final float spawnEneX, spawnEneY; // Como final porque no cambia

    public Enemy(float x, float y, float tamWidth, float tamHeight) {
        super(x, y, tamWidth, tamHeight);
        this.spawnEneX = x;
        this.spawnEneY = y;
    }

    @Override
    public abstract void update(float delta);

    @Override
    public void draw(SpriteBatch batch) {
        if (currentFrame != null) {
            batch.draw(currentFrame, posicion.x, posicion.y, tamWidth, tamHeight);
        }
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnEneX, spawnEneY);
        activo = true;
    }
    //GETTERS Y SETTERS
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
