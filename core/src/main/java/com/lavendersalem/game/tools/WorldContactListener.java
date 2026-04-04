package com.lavendersalem.game.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    // comienzo de la colision
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData()== "foot" || fixtureB.getUserData() == "foot"){

            Fixture head = fixtureA.getUserData() == "foot" ? fixtureA : fixtureB;
            Fixture object = head == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass())){
                ((InteractiveTileObject) object.getUserData()).hit();
            }
        }

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
