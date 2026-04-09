package com.lavendersalem.game.mechanics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public class MovingPlatform extends Sprite {
    public World world;
    public PlayScreen screen;
    public Body b2body;
    private float height;
    private float width;
    private Vector2 positionA = new Vector2();
    private Vector2 positionB = new Vector2();
    private Vector2 destination = new Vector2();
    private float velocity;
    private boolean goB;

    public MovingPlatform(PlayScreen screen, float width, float height, Vector2 positionA, Vector2 positionB, float velocity) {
        super(new Texture(Gdx.files.internal("props/platform1.png")));
        this.screen = screen;
        this.world = screen.getWorld();
        this.width = width;
        this.height = height;
        this.positionA.set(positionA);
        this.positionB.set(positionB);
        this.velocity = velocity;
        this.goB = true;
        definePlatform();

        setBounds(0, 0, width / B2DVars.PPM, height / B2DVars.PPM);
        b2body.setTransform(positionA, 0);

        b2body.setActive(true); // SET TO FALSE LATER
    }

    protected void definePlatform(){
        BodyDef bdef = new BodyDef();

        bdef.type = BodyDef.BodyType.KinematicBody;
        bdef.position.set(positionA);
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / B2DVars.PPM, height / 2 / B2DVars.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = B2DVars.BIT_MOVING;
        fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM;
        b2body.createFixture(fdef).setUserData(this);

        shape.dispose();
    }

    public void update(float delta){
        destination.set(goB ? positionB :  positionA);

        float distancia = b2body.getPosition().dst(destination);

        if (distancia < velocity * delta){
            goB = !goB;
            b2body.setTransform(destination, 0);
            b2body.setLinearVelocity(0,0);
        } else {
            Vector2 direction = new Vector2(destination).sub(b2body.getPosition()).nor();
            Vector2 vel = direction.scl(velocity);
            b2body.setLinearVelocity(vel);
        }

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        System.out.println("Platform pos: " + b2body.getPosition());
        System.out.println("pos A: " + positionA.x + ",: " + positionA.y);
        System.out.println("pos B: " + positionB.x + ",: " + positionB.y);
    }

    public void dispose(){
        getTexture().dispose();
    }
}
