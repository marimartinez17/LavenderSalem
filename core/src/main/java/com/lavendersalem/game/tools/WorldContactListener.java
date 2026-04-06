package com.lavendersalem.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.enemies.Enemy;
import com.lavendersalem.game.screens.Hud;
import com.lavendersalem.game.sprites.Box;
import com.lavendersalem.game.sprites.InteractiveTileObject;
import com.lavendersalem.game.sprites.Player;
import com.lavendersalem.game.utils.B2DVars;
import com.lavendersalem.game.utils.B2DVars.*;
import com.lavendersalem.game.utils.Enums;

public class WorldContactListener implements ContactListener {
    private Array<Body> bodiesToRemove = new Array<Body>();

    // comienzo de la colision
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // combination of bits during a collision
        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if (fixtureA.getUserData()== "foot" || fixtureB.getUserData() == "foot"){

            Fixture foot = fixtureA.getUserData() == "foot" ? fixtureA : fixtureB;
            Fixture object = foot == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && object.getUserData() instanceof InteractiveTileObject){
                ((InteractiveTileObject) object.getUserData()).hit();
            }

            if (object.getUserData() != null && object.getUserData() instanceof Box){
                LavenderSalemGame.manager.get("sounds/WAV/Bump.wav", Sound.class).play();
                ((Player)fixtureA.getUserData()).setState(Enums.State.STANDING);
            }

            if (object.getUserData() == "crystal"){
                bodiesToRemove.add(object.getBody());
                LavenderSalemGame.manager.get("sounds/WAV/Powerup.wav", Sound.class).play();
                Hud.addCrystal();
            }
        }

        switch (cDef){
            case (B2DVars.BIT_ENEMY_HEAD | B2DVars.BIT_LAVENDER):
                // lavender salta sobre el batty
                if (fixtureA.getFilterData().categoryBits == B2DVars.BIT_ENEMY_HEAD){
                    ((Enemy)fixtureA.getUserData()).hitOnHead();
                } else {
                    ((Enemy)fixtureB.getUserData()).hitOnHead();
                }
                LavenderSalemGame.manager.get("sounds/WAV/Hurt.wav", Sound.class).play();
                break;
            case (B2DVars.BIT_ENEMY | B2DVars.OBJECTS_OBSTACLES):
                // batty / enemy choca con su limite y cambia de sentido
                Fixture enemyFixture = fixtureA.getFilterData().categoryBits == B2DVars.BIT_ENEMY ? fixtureA : fixtureB;
                if (enemyFixture.getUserData() instanceof Enemy){
                    ((Enemy)enemyFixture.getUserData()).reverseVelocity(true,false);
                }
                break;
            case(B2DVars.BIT_LAVENDER | B2DVars.BIT_ENEMY):
                // lavender toca enemigo y muere
                Gdx.app.log("LAVENDER","DIE");
                break;
            case(B2DVars.BIT_SALEM | B2DVars.BIT_ENEMY):
                // salem toca enemigo y muere
                Gdx.app.log("SALEM","DIE");
                break;
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
