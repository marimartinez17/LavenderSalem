package com.lavendersalem.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;
import com.lavendersalem.game.utils.Enums;

public class Batty extends Enemy{
    // Para sprites
    protected Texture sheetAttackDer;
    protected Texture sheetAttackIzq;
    protected Texture sheetMoveIzq;
    protected Texture sheetMoveDer;
    protected Texture sheetDamageDer;
    protected Texture sheetDamageIzq;
    protected Texture sheetDieDer;
    protected Texture sheetDieIzq;

    // Para cada frame de animaciones
    protected Animation<TextureRegion> animAttackDer;
    protected Animation<TextureRegion> animAttackIzq;
    protected Animation<TextureRegion> animMoveIzq;
    protected Animation<TextureRegion> animMoveDer;
    protected Animation<TextureRegion> animDamageDer;
    protected Animation<TextureRegion> animDamageIzq;
    protected Animation<TextureRegion> animDieIzq;
    protected Animation<TextureRegion> animDieDer;

    protected String estadoAnim = "";
    protected float timeAnimacion = 0f;
    protected TextureRegion currentFrame;

    // Estado del sprite para mostrar animacion
    protected Enums.State currentState;
    protected Enums.State previousState;
    protected float stateTimer;
    private float deathTimer;

    // to destroy the body
    public boolean setToDestroy;
    public boolean destroyed;


    public Batty(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        stateTimer = 0;
        deathTimer = 0;
        setToDestroy = false;
        destroyed = false;

        // load textures
        sheetAttackDer = new Texture(Gdx.files.internal("sprites/bat/BatAttack-right.png"));
        sheetAttackIzq = new Texture(Gdx.files.internal("sprites/bat/BatAttack.png"));
        sheetMoveDer = new Texture(Gdx.files.internal("sprites/bat/BatMovement-right.png"));
        sheetMoveIzq = new Texture(Gdx.files.internal("sprites/bat/BatMovement.png"));
        sheetDamageDer = new Texture(Gdx.files.internal("sprites/bat/BatdamagedColor-right.png"));
        sheetDamageIzq = new Texture(Gdx.files.internal("sprites/bat/BatdamagedColor.png"));
        sheetDieDer = new Texture(Gdx.files.internal("sprites/bat/BatDeath-right.png"));
        sheetDieIzq = new Texture(Gdx.files.internal("sprites/bat/BatDeath.png"));

        // load animations and divide spritesheets
        animAttackDer = new Animation<>(0.25f, TextureRegion.split(sheetAttackDer, 32, 32)[0]);
        animAttackIzq = new Animation<>(0.25f, TextureRegion.split(sheetAttackIzq, 32, 32)[0]);
        animMoveDer = new Animation<>(0.2f, TextureRegion.split(sheetMoveDer, 32, 32)[0]);
        animMoveIzq = new Animation<>(0.2f, TextureRegion.split(sheetMoveIzq, 32, 32)[0]);
        animDamageDer = new Animation<>(0.2f, TextureRegion.split(sheetDamageDer, 32, 32)[0]);
        animDamageIzq = new Animation<>(0.2f, TextureRegion.split(sheetDamageIzq, 32, 32)[0]);
        animDieDer = new Animation<>(0.2f, TextureRegion.split(sheetDieDer, 32, 32)[0]);
        animDieIzq = new Animation<>(0.2f, TextureRegion.split(sheetDieIzq, 32, 32)[0]);


        // Se config el frame inicial
        currentFrame = TextureRegion.split(sheetMoveDer, 32, 32)[0][0];
        setRegion(currentFrame);

        setBounds(0, 0, 32f / B2DVars.PPM, 32f / B2DVars.PPM);
    }

    @Override
    protected void defineEnemy() {
        // create body from bodydef
        BodyDef bdef = new BodyDef();

        float width = 32f;
        float height = 32f;

        // create box shape for player collision box
        bdef.position.set(x / B2DVars.PPM,y/B2DVars.PPM );
        bdef.type = BodyDef.BodyType.DynamicBody;
        bdef.gravityScale = 0f;
        b2body = world.createBody(bdef);
        b2body.setUserData(this);

        // create fixturedef for player collision box
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / B2DVars.PPM);
        fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
        fdef.filter.maskBits = B2DVars.PLATFORMS | B2DVars.OBJECTS_OBSTACLES | B2DVars.BIT_SALEM | B2DVars.BIT_LAVENDER;
        fdef.shape = shape;
        b2body.createFixture(fdef).setUserData(this);

        // create head fixture for collisions
        PolygonShape head = new PolygonShape();
        Vector2 vertice[] = new Vector2[4];
        vertice[0] = new Vector2(-8,14).scl(1/B2DVars.PPM);
        vertice[1] = new Vector2(8,14).scl(1/B2DVars.PPM);
        vertice[2] = new Vector2(-9,9).scl(1/B2DVars.PPM);
        vertice[3] = new Vector2(9,9).scl(1/B2DVars.PPM);
        head.set(vertice);

        fdef.restitution = 0.5f;
        fdef.density = 0f;
        fdef.isSensor = true;
        fdef.filter.categoryBits = B2DVars.BIT_ENEMY_HEAD;
        fdef.shape = head;
        b2body.createFixture(fdef).setUserData(this); // this -> to access this data from the collision hanfaler
    }

    public void update(float delta) {
        stateTimer  += delta;
        float deathDuration = animDieDer.getAnimationDuration();

        // destroy batty
        if (setToDestroy && !destroyed) {
            deathTimer += delta;
            b2body.setLinearVelocity(0, 0);
            setPosition(
                b2body.getPosition().x - getWidth() / 2,b2body.getPosition().y - getHeight() / 2
            );

            // loads death animation
            setRegion(miraDer ? animDieDer.getKeyFrame(stateTimer) : animDieIzq.getKeyFrame(stateTimer));

            // Plays full animation, then destroy body
            if (animDieIzq.isAnimationFinished(deathTimer) || animDieDer.isAnimationFinished(deathTimer)){
                world.destroyBody(b2body);
                destroyed = true;
            }

        } else if(!destroyed){
            b2body.setLinearVelocity(velocity);
            // for alive batties
            setPosition(b2body.getPosition().x - (32f / 2 / B2DVars.PPM), b2body.getPosition().y - (32f / 2 / B2DVars.PPM));
            setRegion(miraDer ? animMoveDer.getKeyFrame(stateTimer, true) : animMoveIzq.getKeyFrame(stateTimer, true));
        }
    }

    @Override
    public void hitOnHead() {
        setToDestroy = true;

        // gets rid of collisions in the body that is going to be destroyed
        for (Fixture fixture : b2body.getFixtureList()){
            fixture.setSensor(true);
        }
    }
}
