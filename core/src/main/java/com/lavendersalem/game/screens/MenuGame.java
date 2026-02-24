package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;

public class MenuGame implements Screen {
    // Referenciamos clase principal
    private final LavenderSalemGame game;
    // Definir la camara y el ajuste de pantalla con viewport
    private OrthographicCamera camera;
    private Viewport viewport;
    // Tamaño de la pantalla "estandar"
    public static final float VIRTUAL_WIDTH = 640;
    public static final float VIRTUAL_HEIGHT = 360;
    // Constructor
    public MenuGame(LavenderSalemGame game) {
        this.game = game;
        // Inicializar camara y viewport
        camera = new OrthographicCamera();
        // Con fitviewport para ajustar a medida estandar y camara que debe controlar
        viewport = new FitViewport(VIRTUAL_WIDTH, VIRTUAL_HEIGHT, camera);
        // Se centra la camara en el medio del viewport
        camera.position.set(VIRTUAL_WIDTH /2, VIRTUAL_HEIGHT /2, 0);
    }
    @Override
    public void show() { // Se ejecuta cuando esta pantalla se vuelve la activa

    }

    @Override
    public void render(float delta) {// delta es el tiempo que paso en seg desde el ultimo frame
        camera.update(); // Se actualiza la camara (calcula matrices internas)
        // Para que el SpriteBatch use la matriz de proyeccion de la camara
        game.batch.setProjectionMatrix(camera.combined);
        // Colocamos el buffer de color limpio (negro)
        ScreenUtils.clear(0.05f,0.05f,0.1f,1);
        // Inicia el dibujo (imagenes)
        game.batch.begin();

        game.batch.end(); // Termina dibujo
        // Si el usuario toca la pantalla o tecla
        if (Gdx.input.justTouched()){
            // Se pasa a la pantalla de juego
        }

    }

    @Override
    public void resize(int width, int height) {// Si el usuario redimensiona la pantalla
        // Para cuando se ajuste tamaño de pantalla, se le pasa al viewport con true para centrar la camara auto.
        viewport.update(width, height,true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}

    @Override
    public void hide() { // Para cuando se cambia de pantalla

    }

    @Override
    public void dispose() { // Liberar recursos pesados de la pantalla

    }
}
