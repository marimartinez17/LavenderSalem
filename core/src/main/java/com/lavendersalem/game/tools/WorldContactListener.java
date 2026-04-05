package com.lavendersalem.game.tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.screens.Hud;
import com.lavendersalem.game.sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    private Array<Body> bodiesToRemove = new Array<Body>();

    // comienzo de la colision
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData()== "foot" || fixtureB.getUserData() == "foot"){

            Fixture foot = fixtureA.getUserData() == "foot" ? fixtureA : fixtureB;
            Fixture object = foot == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).hit();
            }

            if (object.getUserData() == "crystal"){
                bodiesToRemove.add(object.getBody());
                LavenderSalemGame.manager.get("sounds/WAV/Powerup.wav", Sound.class).play();
                Hud.addCrystal();
            }
        }
    }
    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    // fin de la colision
    @Override
    public void endContact(Contact contact) {

    }

    // cambiar caracteristicas de la colision
    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    // resultados de la colision (ej: ángulos)
    @Override
    public void postSolve(Contact contact, ContactImpulse impulse){

    }
}
