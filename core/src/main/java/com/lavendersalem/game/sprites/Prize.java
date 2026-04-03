package com.lavendersalem.game.sprites;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Prize extends InteractiveTileObject{
    public Prize(World world, TiledMap map, Rectangle bounds) {
        super(world,map,bounds);
        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
    }
}
