package com.lavendersalem.game.sprites;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.mechanics.Box;
import com.lavendersalem.game.mechanics.MovingPlatform;
import com.lavendersalem.game.world.LavenderSalemGame;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;
import com.lavendersalem.game.utils.Enums.*;

public abstract class Player extends Sprite {
    // atributos del jugador
    public World world;
    public Body b2body;
    private float x;
    private float y;
    private float width;
    private float height;

    // Dirección de la animación
    protected boolean miraDer;

    // manejo de coleccionables (cristales)
    private int numCrystals;
    private int totalCrystals;

    private MovingPlatform currentPlatform;

    //Atributos de Player
    protected boolean onSuelo; // Si el personaje esta en el suelo

    // Para sprites
    protected Texture sheetIdle;
    protected Texture sheetIdleDer;
    protected Texture sheetIdleIzq;
    protected Texture sheetCaminarIzq;
    protected Texture sheetCaminarDer;
    protected Texture sheetSaltoDer;
    protected Texture sheetSaltoIzq;
    protected Texture sheetInteractDer;
    protected Texture sheetInteractIzq;
    protected Texture sheetDieDer;
    // Para cada frame de animaciones
    protected Animation<TextureRegion> animDieDer;
    protected Animation<TextureRegion> animIdle;
    protected Animation<TextureRegion> animIdleDer;
    protected Animation<TextureRegion> animIdleIzq;
    protected Animation<TextureRegion> animCaminarDer;
    protected Animation<TextureRegion> animCaminarIzq;
    protected Animation<TextureRegion> animSaltarDer;
    protected Animation<TextureRegion> animSaltarIzq;
    protected Animation<TextureRegion> animInteractuarDer;
    protected Animation<TextureRegion> animInteractuarIzq;
    protected String estadoAnim = "";
    protected float timeAnimacion = 0f;
    protected TextureRegion currentFrame;

    // Estado del sprite para mostrar animacion
    protected State currentState;
    protected State previousState;
    protected float stateTimer;

    protected FixtureDef fdef;

    //vida
    protected boolean isDead;

    public Player(PlayScreen screen, float x, float y, float width, float height) {
        this.world = screen.getWorld();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        onSuelo = true;
        miraDer = true;
        isDead = false;

        definePlayer();

        setBounds(0,0,width / B2DVars.PPM,height / B2DVars.PPM);
    }

    protected abstract short getCategoryBits();
    protected abstract short getMaskBits();

    public void definePlayer() {
        // create body from bodydef
        BodyDef bdef = new BodyDef();

        // create box shape for player collision box
        bdef.position.set(x / B2DVars.PPM,y/B2DVars.PPM );
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        // create fixturedef for player collision box
        fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2)/B2DVars.PPM, (height/2)/B2DVars.PPM);
        fdef.shape = shape;
        fdef.filter.categoryBits = getCategoryBits();
        fdef.filter.maskBits = getMaskBits();
        b2body.createFixture(fdef).setUserData(this);

        // create box shape for player foot
        shape.setAsBox(((width - 2) / 2 /B2DVars.PPM), ((height + 0.01f) /2/B2DVars.PPM));
        fdef.isSensor = true;
        // collision filtering
        fdef.filter.categoryBits = getCategoryBits();
        fdef.filter.maskBits = getMaskBits();
        b2body.createFixture(fdef).setUserData("foot");

        b2body.setUserData(this);

    }


    protected abstract void handleInput(); // Cada player tiene su configuracion de movimientos

    public void hit(){
        LavenderSalemGame.manager.get("music/powder.mp3", Music.class).stop();
        isDead = true;
        Filter filter = new Filter();
        filter.maskBits = B2DVars.BIT_NOTHING;
        for (Fixture fixture : b2body.getFixtureList()) {
            fixture.setFilterData(filter);
        }
        b2body.applyLinearImpulse(new Vector2(0,4f),b2body.getLocalCenter(),true);
    }

    public void update(float delta) {
        handleInput();

        setPosition((b2body.getPosition().x - getWidth() / 2  ), (b2body.getPosition().y - getHeight() / 2  - 0.01f));

        if (currentPlatform != null) {
            Vector2 platformVel = currentPlatform.b2body.getLinearVelocity();
            Vector2 playerVel = new Vector2(b2body.getLinearVelocity());

            float newVelY;

            if (platformVel.y < 0){
                newVelY = platformVel.y;
            } else {
                newVelY = (float) (playerVel.y + platformVel.y);
            }
            b2body.setLinearVelocity(playerVel.x+platformVel.x,newVelY);
        }

        setRegion(getFrame(delta));
    }

    public TextureRegion getFrame(float delta) {
        currentState = getState();
        TextureRegion region;

        if (b2body.getLinearVelocity().x > 0){
            miraDer = true;
        } else if (b2body.getLinearVelocity().x < 0){
            miraDer = false;
        }

        // Seleccionar animación del sprite según estado
        switch (currentState) {
            case DEAD:
                isDead = true;
                region = animDieDer.getKeyFrame(stateTimer);
                break;
            case JUMPING:
                region = miraDer ? animSaltarDer.getKeyFrame(stateTimer) : animSaltarIzq.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = miraDer ? animCaminarDer.getKeyFrame(stateTimer,true) : animCaminarIzq.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
            case STANDING:
            default:
                region = miraDer ? animIdleDer.getKeyFrame(stateTimer,true) : animIdleIzq.getKeyFrame(stateTimer,true);
                break;
        }

        // si el estado cambia, transicionar y reiniciar el timer
        stateTimer = (currentState == previousState)  ? stateTimer + delta : 0f;

        previousState = currentState;

        return region;
    }

    public State getState() {
        if ((b2body.getLinearVelocity().y > 0)) {
            onSuelo = false;
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            onSuelo = false;
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            onSuelo = true;
            return State.RUNNING;
        } else if (isDead){
            return State.DEAD;
        } else {
            onSuelo = true;
            return State.STANDING;
        }
    }

    public void setPlatform(MovingPlatform p){
        this.currentPlatform = p;
    }

    public MovingPlatform getCurrentPlatform() {
        return currentPlatform;
    }

    public void setState(State state) {
        this.currentState = state;
    }

    public void setOnSuelo(Boolean onSuelo) {
        this.onSuelo = onSuelo;
    }

    public void resetear() {
        // falta set position
        onSuelo = false;
        estadoAnim = "";
        timeAnimacion = 0f;
    }

    // Elimina basura de la grafica
    public abstract void dispose();
}
