package com.lavendersalem.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.utils.B2DVars;

import java.awt.*;

public class Crystal extends InteractiveTileObject {

    //protected Fixture fixture;
    public Crystal(World world, TiledMap map, Rectangle bounds) {
        super(world,map,bounds);

        //fixture.setUserData(this);

    }
}
