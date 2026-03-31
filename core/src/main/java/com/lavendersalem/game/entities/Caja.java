package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavendersalem.game.utils.Constants;

public class Caja extends Entity{
    private float spawnX, spawnY;
    private boolean onSuelo;

    public Caja(float x, float y) {
        super(x, y, 16f, 16f);
        this.spawnX = x;
        this.spawnY = y;
    }

    @Override
    public void update(float delta) {
        if (!onSuelo) {
            velocidad.y += Constants.GRAVEDAD * delta;
            if (velocidad.y < -960f) velocidad.y = -960f;
        }
    }

    public void movEnX(float delta) {
        posicion.x += velocidad.x * delta;
    }
    public void movEnY (float delta) {
        posicion.y += velocidad.y * delta;
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (currentFrame != null) {
            batch.draw(currentFrame, posicion.x, posicion.y, tamWidth, tamHeight);
        }
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
        onSuelo = false;
    }

    public boolean isOnSuelo() { return onSuelo; }
    public void setOnSuelo(boolean onSuelo) { this.onSuelo = onSuelo; }
}
