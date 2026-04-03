package com.lavendersalem.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.lavendersalem.game.utils.B2DVars;
import com.lavendersalem.game.utils.Enums;

public class Lavender extends Player {
    private boolean esperaRescate = false;
    private float posMuerteX, posMuerteY;
    // Constructor (Crea a Lavender)
    public Lavender(World world, float x, float y, float width, float height) {
        super(world, x, y, width, height);
        // Spritsheets
        sheetIdle = new Texture(Gdx.files.internal("sprites/lavender/Lavender-idle-sheet.png"));
        sheetCaminarIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkleft-sheet.png"));
        sheetCaminarDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkright-sheet.png"));
        sheetSaltoIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpleft-sheet.png"));
        sheetSaltoDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpright-sheet.png"));
        sheetInteractIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactleft-sheet.png"));
        sheetInteractDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactright-sheet.png"));
        // Crear animaciones en 0 porque hay una sola fila y con 0.15f tiempo de frame
        animIdle = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 32)[0]);
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
    // Configuramos movimiento
    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            b2body.applyLinearImpulse(new Vector2(0,2f),b2body.getWorldCenter(),true);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && b2body.getLinearVelocity().x <= 2){
            b2body.applyLinearImpulse(new Vector2(0.08f,0f),b2body.getWorldCenter(),true);
            miraDer = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && b2body.getLinearVelocity().x >= -2){
            b2body.applyLinearImpulse(new Vector2(-0.08f,0f),b2body.getWorldCenter(),true);
            miraDer = false;
        }


        // Si esta esperando no mueve
        if (esperaRescate) {
            b2body.applyLinearImpulse(new Vector2(0,0),b2body.getWorldCenter(),true);
            return;
        }

    }

    @Override
    public void update(float delta) {
        super.update(delta); // Aplica el sistema de salto y movimiento
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
