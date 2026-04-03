package com.lavendersalem.game.tools;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class WorldContactListener implements ContactListener {
    // comienzo de la colision
    @Override
    public void beginContact(Contact contact) {
        Gdx.app.log("begin contact", "");
    }
    // fin de la colision
    @Override
    public void endContact(Contact contact) {
        Gdx.app.log("end contact", "");
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
