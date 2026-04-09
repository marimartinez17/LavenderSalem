package com.lavendersalem.game.tools;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.enemies.Batty;
import com.lavendersalem.game.mechanics.Box;
import com.lavendersalem.game.mechanics.MovingPlatform;
import com.lavendersalem.game.sprites.*;
import com.lavendersalem.game.world.LavenderSalemGame;
import com.lavendersalem.game.enemies.Enemy;
import com.lavendersalem.game.screens.Hud;
import com.lavendersalem.game.utils.B2DVars;
import com.lavendersalem.game.utils.Enums;

import static com.badlogic.gdx.utils.JsonSkimmer.JsonToken.TokenType.other;
import static com.badlogic.gdx.utils.JsonValue.ValueType.object;

public class WorldContactListener implements ContactListener {
    private Array<Body> bodiesToRemove = new Array<Body>();

    // comienzo de la colision
    @Override
    public void beginContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        // combination of bits during a collision
        int cDef = fixtureA.getFilterData().categoryBits | fixtureB.getFilterData().categoryBits;

        if ("foot".equals(fixtureA.getUserData()) || "foot".equals(fixtureB.getUserData())) {

            Fixture foot = fixtureA.getUserData() == "foot" ? fixtureA : fixtureB;
            Fixture object = foot == fixtureA ? fixtureB : fixtureA;

            if (object.getUserData() != null && object.getUserData() instanceof Box){
                LavenderSalemGame.manager.get("sounds/WAV/Bump.wav", Sound.class).play();
                ((Player)foot.getBody().getUserData()).setState(Enums.State.STANDING);
            }

            if (object.getUserData() == "crystal"){
                if (!bodiesToRemove.contains(object.getBody(), true)) {
                    bodiesToRemove.add(object.getBody());
                    LavenderSalemGame.manager.get("sounds/WAV/Powerup.wav", Sound.class).play();
                    Hud.addCrystal();
                }
            }

            if (object.getUserData() instanceof MovingPlatform) {
                Player player = (Player) foot.getBody().getUserData();
                MovingPlatform movingPlatform = (MovingPlatform) object.getUserData();
                player.setPlatform(movingPlatform);
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
            case((B2DVars.BIT_LAVENDER | B2DVars.BIT_ENEMY)):
                Fixture lavenderFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_LAVENDER ? fixtureA : fixtureB;
                Fixture enemyFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_ENEMY ? fixtureA : fixtureB;

                if (enemyFix.getUserData() instanceof Batty){
                    if(((Batty) enemyFix.getUserData()).setToDestroy){
                        break;
                    }
                }

                if (lavenderFix.getUserData() instanceof Lavender){
                    ((Lavender)lavenderFix.getUserData()).hit();
                }
                break;
            case(B2DVars.BIT_SALEM | B2DVars.BIT_ENEMY):
                Fixture salemFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_SALEM ? fixtureA : fixtureB;
                ((Salem)salemFix.getUserData()).hit();
                break;
            case(B2DVars.BIT_LAVENDER | B2DVars.BIT_DANGER):
                // lavender toca enemigo y muere
                lavenderFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_LAVENDER ? fixtureA : fixtureB;
                ((Lavender)lavenderFix.getUserData()).hit();
                break;
            case(B2DVars.BIT_SALEM | B2DVars.BIT_DANGER):
                salemFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_SALEM ? fixtureA : fixtureB;
                ((Salem)salemFix.getUserData()).hit();
                break;
            case(B2DVars.BIT_SALEM | B2DVars.BIT_WATER):
                salemFix = fixtureA.getFilterData().categoryBits == B2DVars.BIT_SALEM ? fixtureA : fixtureB;
                ((Salem)salemFix.getUserData()).hit();
                break;
        }
    }
    public Array<Body> getBodiesToRemove() {
        return bodiesToRemove;
    }

    // fin de la colision
    @Override
    public void endContact(Contact contact) {
        Fixture fixtureA = contact.getFixtureA();
        Fixture fixtureB = contact.getFixtureB();

        if ("foot".equals(fixtureA.getUserData()) || "foot".equals(fixtureB.getUserData())) {

            Fixture foot = "foot".equals(fixtureA.getUserData()) ? fixtureA : fixtureB;
            Fixture other = foot == fixtureA ? fixtureB : fixtureA;

            Player player = (Player) foot.getBody().getUserData();

            // dejar plataforma
            if (other.getUserData() instanceof MovingPlatform) {
                player.setPlatform(null);
            }
        }
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
