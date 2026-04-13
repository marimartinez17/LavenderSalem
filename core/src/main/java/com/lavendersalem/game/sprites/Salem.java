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

public class Salem extends Player {
    private boolean dobleSalto;

    // Constructor (Crea a Lavender)
    public Salem(PlayScreen screen, float x, float y, float width, float height) {
        super(screen, x, y, width, height);

        // Spritsheets
        sheetDieDer = new Texture(Gdx.files.internal("sprites/salem/salem-idle-sheet.png"));
        sheetIdle = new Texture(Gdx.files.internal("sprites/salem/salem-idle-sheet.png"));
        sheetIdleIzq = new Texture(Gdx.files.internal("sprites/salem/salem-idleleft-sheet.png"));
        sheetIdleDer = new Texture(Gdx.files.internal("sprites/salem/salem-idle-sheet.png"));
        sheetCaminarIzq = new Texture(Gdx.files.internal("sprites/salem/salem-walkleft-sheet.png"));
        sheetCaminarDer = new Texture(Gdx.files.internal("sprites/salem/salem-walkright-sheet.png"));
        sheetSaltoIzq = new Texture(Gdx.files.internal("sprites/salem/salem-jumpleft-sheet.png"));
        sheetSaltoDer = new Texture(Gdx.files.internal("sprites/salem/salem-jumpright-sheet.png"));
        sheetInteractIzq = new Texture(Gdx.files.internal("sprites/salem/salem-interactleft-sheet.png"));
        sheetInteractDer = new Texture(Gdx.files.internal("sprites/salem/salem-interactright-sheet.png"));
        // Crear animaciones en 0 porque hay una sola fila y con 0.15f tiempo de frame
        animDieDer = new Animation<>(0.25f, TextureRegion.split(sheetDieDer, 16, 16)[0]);
        animIdle = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 16)[0]);
        animIdleIzq = new Animation<>(0.25f, TextureRegion.split(sheetIdleIzq, 16, 16)[0]);
        animIdleDer = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 16)[0]);
        animCaminarIzq = new Animation<>(0.2f, TextureRegion.split(sheetCaminarIzq, 16, 16)[0]);
        animCaminarDer = new Animation<>(0.2f, TextureRegion.split(sheetCaminarDer, 16, 16)[0]);
        animSaltarIzq = new Animation<>(0.2f, TextureRegion.split(sheetSaltoIzq, 16, 16)[0]);
        animSaltarDer = new Animation<>(0.2f, TextureRegion.split(sheetSaltoDer, 16, 16)[0]);
        animInteractuarIzq = new Animation<>(0.2f, TextureRegion.split(sheetInteractIzq, 16, 16)[0]);
        animInteractuarDer = new Animation<>(0.2f, TextureRegion.split(sheetInteractDer, 16, 16)[0]);
        // Se config el frame inicial
        currentFrame = TextureRegion.split(sheetIdle, 16, 16)[0][0];

        // Establecer region en donde se muestra la textura
        setRegion(currentFrame);
    }

    @Override
    protected short getCategoryBits(){
        return B2DVars.BIT_SALEM;
    }

    @Override
    protected short getMaskBits(){
        return B2DVars.PLATFORMS | B2DVars.BIT_ENEMY | B2DVars.OBJECTS_CRYSTALS| B2DVars.OBJECTS_INTERACTIVE | B2DVars.BIT_DANGER | B2DVars.BIT_MOVING |  B2DVars.BIT_WATER  | B2DVars.BIT_PORTALS | B2DVars.BIT_INTERACTIVE;
    }

    // Configuramos movimiento
    @Override
    protected void handleInput() {
        if (currentState != Enums.State.DEAD){
            if (Gdx.input.isKeyJustPressed(Input.Keys.W) && onSuelo) {
                b2body.applyLinearImpulse(new Vector2(0, 3.2f), b2body.getWorldCenter(), true);
                // Salem meows when he jumps
                LavenderSalemGame.manager.get("sounds/WAV/Cat_Meow.wav", Sound.class).play();
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && b2body.getLinearVelocity().x <= 2) {
                b2body.applyLinearImpulse(new Vector2(0.07f, 0f), b2body.getWorldCenter(), true);
                miraDer = true;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.A) && b2body.getLinearVelocity().x >= -2) {
                b2body.applyLinearImpulse(new Vector2(-0.07f, 0f), b2body.getWorldCenter(), true);
                miraDer = false;
            }
        }
    }

    @Override
    public void hit(){
        super.hit();
        LavenderSalemGame.manager.get("sounds/WAV/Cat_Meow.wav", Sound.class).play();
    }

    @Override
    public void resetear() {
        super.resetear();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
}
