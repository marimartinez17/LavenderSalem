package com.lavendersalem.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavendersalemLS.game.screens.GameScreen;

public class LavenderSalemGame extends Game {
    public static LavenderSalemGame game;// Intanciar la clase para el cambio entre screens
    public SpriteBatch batch;
    @Override
    public void create() {
        game = this; // El coordinador de Screens (game) que es esta clase (principal)
        batch = new SpriteBatch(); // LLeva los assets "apilados" a la GPU
        setScreen(new GameScreen(game));
    }

    @Override
    public void dispose() {
        batch.dispose(); // Para liberar la RAM de basura
    }
}
