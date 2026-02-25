package com.lavendersalem.game.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.entities.Lavender;
import com.lavendersalem.game.utils.Constants;

public class GameScreen implements Screen {
    private final LavenderSalemGame game;
    // Camara ortografica y viewPoint
    private OrthographicCamera camara;
    private Viewport viewport;
    private final Lavender lavender;
    private ShapeRenderer shapeRenderer; // Para probrar con hitbox (rectangulo)
    // Constructor del Game
    public GameScreen(LavenderSalemGame game) {
        this.game = game;
        // Configurar la camara y viewport
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camara);
        lavender = new Lavender(50f,0f);
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    public void show() { // Se llama cuando el screen se activa
    }

    @Override
    public void render(float delta) {
        lavender.update(delta);
        // Limpiar pantalla y dejar en fondo blanco
        ScreenUtils.clear(Color.LIGHT_GRAY);
        // aplicar viewport, actualizar camara y mantriz del batch
        viewport.apply();
        camara.update();
        // Para prueba se dibuja el hitbox como rectangulo
        shapeRenderer.setProjectionMatrix(camara.combined); // para que tome la matriz de la camara
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Para "dibujar" el render
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect( // Para definir la posicion de aparicion y tamaño
            lavender.getBounds().x, lavender.getBounds().y,
            lavender.getBounds().width, lavender.getBounds().height
        );
        shapeRenderer.end(); // Termina el dibujo y manda al GPU
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true); // Para que si se redimenciona centre el viewport
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {

    }
}
