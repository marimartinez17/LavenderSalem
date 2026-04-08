package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public class Box extends Sprite {
    public World world;
    public Body b2body;
    private float x;
    private float y;
    private float width;
    private float height;

    Texture texture;

    public Box(PlayScreen screen, float x, float y, float width, float height) {
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        texture = new Texture(Gdx.files.internal("props/brown_wall_stand_00.png"));
        setTexture(texture);

        defineBox();

        setBounds(0,0,width / B2DVars.PPM,height / B2DVars.PPM);
    }

    protected void defineBox(){
        // create body from bodydef
        BodyDef bdef = new BodyDef();

        // create box shape for prop collision box
        bdef.position.set(x / B2DVars.PPM,y/B2DVars.PPM );
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // create fixturedef for prop collision box
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2)/B2DVars.PPM, (height/2)/B2DVars.PPM);
        fdef.shape = shape;
        fdef.restitution = 0.5f;
        fdef.filter.categoryBits = B2DVars.OBJECTS_INTERACTIVE;
        fdef.filter.maskBits = B2DVars.PLATFORMS | B2DVars.BIT_SALEM | B2DVars.BIT_LAVENDER;
        b2body.createFixture(fdef);
        b2body.setUserData(this);

        // create box shape for prop foot
        shape.setAsBox(((width) / 2 /B2DVars.PPM), ((height) /2/B2DVars.PPM));
        fdef.isSensor = true;

        // collision filtering
        fdef.filter.categoryBits = B2DVars.OBJECTS_INTERACTIVE;
        fdef.filter.maskBits = B2DVars.PLATFORMS | B2DVars.BIT_SALEM | B2DVars.BIT_LAVENDER;
        b2body.createFixture(fdef).setUserData("foot");
    }

    public void update(float delta){
        setPosition((b2body.getPosition().x - getWidth() / 2  ), (b2body.getPosition().y - getHeight() / 2  - 0.01f));
    }

    public void dispose(){

    }
}
