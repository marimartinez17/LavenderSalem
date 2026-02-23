package com.lavendersalem.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.lavendersalem.game.screens.MenuGame;

// Clase principal que hereda de game para gestionar los Screen con el Game Loop (crear,renderizar,pausa)
public class LavenderSalemGame extends Game {
    public SpriteBatch batch; // Se encarga de "apilar" todas las imagenes para enviarlas a la GPU

    @Override
    public void create() {
        // Inicializar el batch al iniciar el juego
        batch = new SpriteBatch();
        // Pasamos las pantallas (Screens) con setScreen al batch
        this.setScreen(new MenuGame(this));
    }
    @Override
    public void render() { // Llama al reder del Screen activo para mostrarlo
        super.render();
    }
    @Override
    public void dispose() { // Para cerrar el juego
        batch.dispose(); // Limpia memoria ram
    }
}
