package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.utils.B2DVars;

import java.awt.*;

public class Crystal extends InteractiveTileObject {

    //protected Fixture fixture;
    public Crystal(World world, TiledMap map, Rectangle bounds, short bit, String userData) {
        super(world,map,bounds,bit,userData);
        bit = B2DVars.OBJECTS_CRYSTALS;
        fixture.setUserData(this);
    }
    @Override
    public void hit(){
        Gdx.app.log("Crystal","Collision");
    }

}
