package com.lavendersalem.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
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

    // para animación
    protected boolean miraDer;
    protected boolean miraFront;

    // Para sprites
    protected Texture sheetIdle;
    protected Texture sheetCaminarIzq;
    protected Texture sheetCaminarDer;
    protected Texture sheetSaltoDer;
    protected Texture sheetSaltoIzq;
    protected Texture sheetInteractDer;
    protected Texture sheetInteractIzq;
    // Para cada frame de animaciones
    protected Animation<TextureRegion> animIdle;
    protected Animation<TextureRegion> animCaminarDer;
    protected Animation<TextureRegion> animCaminarIzq;
    protected Animation<TextureRegion> animSaltarDer;
    protected Animation<TextureRegion> animSaltarIzq;
    protected Animation<TextureRegion> animInteractuarDer;
    protected Animation<TextureRegion> animInteractuarIzq;
    protected String estadoAnim = "";
    protected float timeAnimacion = 0f;
    protected float tiempoEnAire = 0f;
    protected TextureRegion currentFrame;

    // Estado del sprite para mostrar animacion
    protected State currentState;
    protected State previousState;
    protected float stateTimer;

    public Player(World world, float x, float y, float width, float height) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        currentState = State.STANDING;
        previousState = State.STANDING;
        stateTimer = 0f;
        miraDer = true;

        definePlayer();

        setBounds(0,0,width / B2DVars.PPM,height / B2DVars.PPM);
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(x / B2DVars.PPM,y/B2DVars.PPM );
        bdef.type = BodyDef.BodyType.DynamicBody;

        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox((width / 2)/B2DVars.PPM, (height/2)/B2DVars.PPM);
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    protected abstract void handleInput(); // Cada player tiene su configuracion de movimientos

    public void update(float delta) {
        setPosition((b2body.getPosition().x - getWidth() / 2  ), (b2body.getPosition().y - getHeight() / 2  - 0.01f));

        handleInput();
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
            case JUMPING:
                region = miraDer ? animSaltarDer.getKeyFrame(stateTimer) : animSaltarIzq.getKeyFrame(stateTimer);
                break;
            case RUNNING:
                region = miraDer ? animCaminarDer.getKeyFrame(stateTimer,true) : animCaminarIzq.getKeyFrame(stateTimer,true);
                break;
            case FALLING:
                region = miraDer ? animSaltarDer.getKeyFrame(3) : animSaltarIzq.getKeyFrame(3);
            case STANDING:
            default:
                region = animIdle.getKeyFrame(stateTimer,true);
                break;
        }

        // si el estado cambia, transicionar y reiniciar el timer
        stateTimer = (currentState == previousState)  ? stateTimer + delta : 0f;

        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0) {
            return State.JUMPING;
        } else if (b2body.getLinearVelocity().y < 0) {
            return State.FALLING;
        } else if (b2body.getLinearVelocity().x != 0) {
            return State.RUNNING;
        } else {
            return State.STANDING;
        }
    }

    public void setState(State state) {
        this.currentState = state;
    }

    // Elimina basura de la grafica
    public abstract void dispose();
}
