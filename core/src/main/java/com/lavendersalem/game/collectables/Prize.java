package com.lavendersalem.game.collectables;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.sprites.InteractiveTileObject;
import com.lavendersalem.game.utils.B2DVars;

public class Prize extends InteractiveTileObject {
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
