package com.lavendersalem.game.enemies;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public abstract class Enemy extends Sprite {
    protected World world;
    protected PlayScreen screen;
    protected float x;
    protected float y;
    public Body b2body;
    protected Vector2 velocity;
    protected boolean miraDer;

    public Enemy (PlayScreen screen, float x, float y){
        this.world = screen.getWorld();
        this.screen = screen;
        this.x = x;
        this.y = y;
        miraDer = true;
        setPosition(x / B2DVars.PPM, y / B2DVars.PPM);
        defineEnemy();
        velocity = new Vector2(1,0);
    }

    public abstract void hitOnHead();

    protected abstract void defineEnemy();

    public void reverseVelocity(boolean x, boolean y){
        if (x)velocity.x = -velocity.x;
        if (y)velocity.y = -velocity.y;
        miraDer = velocity.x > 0;
    }
}
