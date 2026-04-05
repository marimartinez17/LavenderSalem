package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public class Prize extends InteractiveTileObject{
    public Prize(PlayScreen screen, Rectangle bounds, short bit, String userData) {
        super(screen,bounds,bit, userData);
        bit =  B2DVars.OBJECTS_PRIZE;
        fixture.setUserData(this);
    }

    @Override
    public void hit(){
        Gdx.app.log("Prize","Collision");
    }
}
