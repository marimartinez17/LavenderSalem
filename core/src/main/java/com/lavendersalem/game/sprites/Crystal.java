package com.lavendersalem.game.sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.lavendersalem.game.utils.B2DVars;

import java.awt.*;

public class Crystal extends InteractiveTileObject {

    public Crystal(World world, TiledMap map, Rectangle bounds) {
        super(world,map,bounds);

    }
}
