package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.lavendersalem.game.utils.Enums;
import com.lavendersalem.game.world.LavenderSalemGame;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.utils.B2DVars;

public class Lavender extends Player {

    private boolean esperaRescate = false;
    private float posMuerteX, posMuerteY;

    // Constructor (Crea a Lavender)
    public Lavender(PlayScreen screen, float x, float y, float width, float height) {

        super(screen, x, y, width, height);

        // Spritesheets
        sheetIdle = new Texture(Gdx.files.internal("sprites/lavender/Lavender-idle-sheet.png"));
        sheetIdleIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-idleleft-sheet.png"));
        sheetIdleDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-idleright-sheet.png"));
        sheetCaminarIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkleft-sheet.png"));
        sheetCaminarDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkright-sheet.png"));
        sheetSaltoIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpleft-sheet.png"));
        sheetSaltoDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpright-sheet.png"));
        sheetInteractIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactleft-sheet.png"));
        sheetInteractDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactright-sheet.png"));
        sheetDieDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-dieder-sheet.png"));

        // Crear animaciones en 0 porque hay una sola fila y con 0.15f tiempo de frame
        animIdle = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 32)[0]);
        animIdleIzq = new Animation<>(0.25f, TextureRegion.split(sheetIdleIzq, 16, 32)[0]);
        animIdleDer = new Animation<>(0.25f, TextureRegion.split(sheetIdleDer, 16, 32)[0]);
        animCaminarIzq = new Animation<>(0.2f, TextureRegion.split(sheetCaminarIzq, 16, 32)[0]);
        animCaminarDer = new Animation<>(0.2f, TextureRegion.split(sheetCaminarDer, 16, 32)[0]);
        animSaltarIzq = new Animation<>(0.2f, TextureRegion.split(sheetSaltoIzq, 16, 32)[0]);
        animSaltarDer = new Animation<>(0.2f, TextureRegion.split(sheetSaltoDer, 16, 32)[0]);
        animInteractuarIzq = new Animation<>(0.2f, TextureRegion.split(sheetInteractIzq, 16, 32)[0]);
        animInteractuarDer = new Animation<>(0.2f, TextureRegion.split(sheetInteractDer, 16, 32)[0]);

        // Se config el frame inicial
        currentFrame = TextureRegion.split(sheetIdle, 16, 32)[0][0];
        setRegion(currentFrame);
    }

    @Override
    protected short getCategoryBits(){
        return B2DVars.BIT_LAVENDER;
    }

    @Override
    protected short getMaskBits(){
        return B2DVars.PLATFORMS | B2DVars.BIT_ENEMY | B2DVars.BIT_ENEMY_HEAD | B2DVars.OBJECTS_CRYSTALS | B2DVars.OBJECTS_INTERACTIVE | B2DVars.BIT_DANGER | B2DVars.BIT_MOVING | B2DVars.BIT_WATER | B2DVars.BIT_PORTALS | B2DVars.BIT_INTERACTIVE;
    }

    @Override
    public void hit(){
        super.hit();
        LavenderSalemGame.manager.get("sounds/WAV/Low_Health.wav", Sound.class).play();
    }


    // Configuramos movimiento
    @Override
    protected void handleInput() {
        if (currentState != Enums.State.DEAD){
            if ((Gdx.input.isKeyJustPressed(Input.Keys.UP) && onSuelo)){
                b2body.applyLinearImpulse(new Vector2(0,2.6f),b2body.getWorldCenter(),true);
                // play jumping sound
                LavenderSalemGame.manager.get("sounds/WAV/Jump.wav", Sound.class).play();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= 2){
                b2body.applyLinearImpulse(new Vector2(0.07f,0f),b2body.getWorldCenter(),true);
                miraDer = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= -2){
                b2body.applyLinearImpulse(new Vector2(-0.07f,0f),b2body.getWorldCenter(),true);
                miraDer = false;
            }
        }
    }


    @Override
    public TextureRegion getFrame(float delta) {

        TextureRegion frame = super.getFrame(delta);

        if (getState() == Enums.State.DEAD){
            frame = animDieDer.getKeyFrame(stateTimer, false);
        }

        return frame;
    }


    @Override
    public void update(float delta) {
        super.update(delta); // Aplica el sistema de salto y movimiento
    }

    @Override
    public void resetear() {
        esperaRescate = false;
    }



    // Elimina basura de la grafica*/
    @Override
    public void dispose() {
        sheetIdle.dispose();
        sheetCaminarIzq.dispose();
        sheetCaminarDer.dispose();
        sheetSaltoIzq.dispose();
        sheetSaltoDer.dispose();
        sheetInteractIzq.dispose();
        sheetInteractDer.dispose();
    }
    // GETTERS Y SETTERS
    public boolean isEsperaRescate() { return esperaRescate; }

    public float getPosMuerteX() { return posMuerteX; }

    public float getPosMuerteY() { return posMuerteY; }
}
