package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;

public class Crystal extends Collectable{
    public Crystal(Body body) {
        super(body);

        Texture texture = new Texture(Gdx.files.internal("sprites/crystal/crystal.png"));
        TextureRegion[] sprites = TextureRegion.split(texture, 16,16)[0];
        setAnimation(sprites, 1/12f);

        width = sprites[0].getRegionWidth();
        height = sprites[0].getRegionHeight();
    }
}
