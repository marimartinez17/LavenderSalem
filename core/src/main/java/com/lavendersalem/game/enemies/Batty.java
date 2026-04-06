package com.lavendersalem.game.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
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
    protected boolean miraDer;
    protected String estadoAnim = "";
    protected float timeAnimacion = 0f;
    protected TextureRegion currentFrame;

    // Estado del sprite para mostrar animacion
    protected Enums.State currentState;
    protected Enums.State previousState;
    protected float stateTimer;


    public Batty(PlayScreen screen, float x, float y) {
        super(screen, x, y);
        b2body.setUserData("batty");
        miraDer = true;
        stateTimer = 0;

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

        setBounds(
            b2body.getPosition().x - (32f / B2DVars.PPM / 2),
            b2body.getPosition().y - (32f / B2DVars.PPM / 2),
            32f / B2DVars.PPM,
            32f / B2DVars.PPM
        );
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
        b2body = world.createBody(bdef);

        // create fixturedef for player collision box
        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(12 / B2DVars.PPM);
        fdef.filter.categoryBits = B2DVars.BIT_ENEMY;
        fdef.filter.maskBits = B2DVars.PLATFORMS | B2DVars.OBJECTS_OBSTACLES | B2DVars.BIT_SALEM | B2DVars.BIT_LAVENDER;
        fdef.shape = shape;
        b2body.createFixture(fdef);
    }

    public void update(float delta) {
        stateTimer  += delta;

        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(miraDer ? animMoveDer.getKeyFrame(stateTimer, true) : animMoveIzq.getKeyFrame(stateTimer, true));

        //Gdx.app.log("Batty", "pos: " + b2body.getPosition() + " w: " + getWidth() + " h: " + getHeight());


    }
}
