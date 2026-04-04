package com.lavendersalem.game.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    private Array<Body> bodiesToRemove = new Array<Body>();

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

        if (fixtureA.getUserData() != null && fixtureA.getUserData().equals("crystal")) {
            // remove crystal
            System.out.println("Remove crystal");
            bodiesToRemove.add(fixtureA.getBody());
        }

        if (fixtureB.getUserData() != null && fixtureB.getUserData().equals("crystal")) {
            // remove crystal
            System.out.println("Remove crystal");
            bodiesToRemove.add(fixtureB.getBody());
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
