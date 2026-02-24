package com.lavendersalem.game.screens;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.entities.Player;

public class GameScreen implements Screen {
    private final LavenderSalemGame game;
    // Camara y viewport
    private OrthographicCamera camera;
    private Viewport viewport;
    // Los player
    private Player lavender;
    private Player salem;

    public GameScreen(LavenderSalemGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(720, 360, camera);
        // Intanciar personajes
        lavender = new Player("sprites/Lavender-Static.png", 100, 100, 200f, 32,64,
            Input.Keys.UP, Input.Keys.DOWN, Input.Keys.LEFT, Input.Keys.RIGHT);
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        // Se llama al update del personaje
        lavender.update(delta);
        // Limpiar la pantalla (color blanco)
        ScreenUtils.clear(247/255.0f,218/255.0f,255/255.0f,1);
        // aplicar viewport (evitar que se distorcione la imagen), actualizar la camara y matriz (batch)
        viewport.apply();
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        // Hacemos el dibujo
        game.batch.begin();
        lavender.draw(game.batch);
        game.batch.end(); // Cerramos el dibujo
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        lavender.dispose(); // Se libera textura del personaje al cerrar la pantalla
    }
}
