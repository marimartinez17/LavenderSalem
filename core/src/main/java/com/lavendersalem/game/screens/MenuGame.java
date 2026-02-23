package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.ScreenUtils;
import com.lavendersalem.game.LavenderSalemGame;

public class MenuGame implements Screen {
    // Referenciamos clase principal
    private final LavenderSalemGame game;
    // Constructor
    public MenuGame(LavenderSalemGame game) {
        this.game = game;
    }
    @Override
    public void show() { // Se ejecuta cuando esta pantalla se vuelve la activa

    }

    @Override
    public void render(float delta) {
        // delta es el tiempo que paso en seg desde el ultimo frame
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
