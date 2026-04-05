package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.screens.Hud;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public class Crystal0 extends InteractiveTileObject {

    //protected Fixture fixture;
    public Crystal0(PlayScreen screen, Rectangle bounds, short bit, String userData) {
        super(screen,bounds,bit,userData);
        bit = B2DVars.OBJECTS_CRYSTALS;
        fixture.setUserData(this);
    }
    @Override
    public void hit(){
        Gdx.app.log("Crystal","Collision");
        Hud.addCrystal();
    }

}
