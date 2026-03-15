package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.entities.Lavender;
import com.lavendersalem.game.entities.Salem;
import com.lavendersalem.game.utils.Constants;
import com.lavendersalem.game.utils.Enums;
import com.lavendersalem.game.world.SistemaColisiones;
import com.lavendersalem.game.world.SistemaVidas;

public class GameScreen implements Screen {
    private final LavenderSalemGame game;
    private final SistemaColisiones sistemaColisiones;
    //private final Level nivelActual; // Para alternar entre niveles
    private boolean pausado;
    private final OverlayPausa overlayPausa;
    // Camara ortografica y viewPoint
    private final OrthographicCamera camara;
    private final FitViewport viewport;
    private final Lavender lavender;
    private final Salem salem;
    private final SistemaVidas sistemaVidas;
    private final ShapeRenderer shapeRenderer; // Para probrar con hitbox (rectangulo)
    private final Array<Rectangle> tilesSolidos;
    private final Array<Rectangle> tilesPeligros;

    // Constructor del Game
    public GameScreen(LavenderSalemGame game, int numeroNivel) {
        this.game = game;
        //nivelActual = new Level(numeroNivel);
        // Configurar la camara y viewport
        camara = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camara);
        overlayPausa = new OverlayPausa(camara, viewport);
        lavender = new Lavender(48f,48f);
        salem = new Salem(60f,48f);
        sistemaVidas = new SistemaVidas(lavender, salem);
        shapeRenderer = new ShapeRenderer();
        tilesSolidos = new Array<>();
        tilesPeligros = new Array<>();
        crearTilesPrueba();
        sistemaColisiones = new SistemaColisiones(tilesSolidos, tilesPeligros);
    }
    private void crearTilesPrueba() {
        /* BORDES */
        for (int y = 0; y < (Constants.VIRTUAL_HEIGHT) / 16 ; y++) { // Izq
            tilesSolidos.add(new Rectangle(-16, y * 16, 16, 16));
        }
        for (int y = 0; y < (Constants.VIRTUAL_HEIGHT) / 16 ; y++) { //Der
            tilesSolidos.add(new Rectangle(Constants.VIRTUAL_WIDTH, y * 16, 16, 16));
        }
        // Suelo completo
        for (int x = 0; x < (Constants.VIRTUAL_WIDTH / 16) ; x++) {
            if ((x >= 15 && x < 18) || (x >= 34 && x < 37)) continue; // Para dejar el hueco de los peligros
            tilesSolidos.add(new Rectangle(x * 16, 0, 16, 16));
        }
        // Plataforma
        for (int x = 8; x < 18; x++) {
            tilesSolidos.add(new Rectangle(x * 16, 48, 16, 16));
        }
        // Pared pequeña
        for (int y = 1; y < 2; y++ ) {
            tilesSolidos.add(new Rectangle(96, y * 16, 16, 16));
            tilesSolidos.add(new Rectangle(320, y * 16, 16, 16));
        }
        // Pared alta
        for (int y = 1; y < 4; y++){
            tilesSolidos.add(new Rectangle(480, y * 16, 16, 16));
        }
        // Tiles de agua (peligro)
        for (int x = 15; x < 18; x++) {
            tilesPeligros.add(new Rectangle(x * 16,-17, 16, 32 ));
        }
        for (int x = 34; x < 37; x++) {
            tilesPeligros.add(new Rectangle(x * 16, -17, 16, 32));
        }

    }

    @Override
    public void show() { // Se llama cuando el screen se activa
    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, Constants.DELTA_MAXIMO);
        // Para pantalla de pausa
        if (Gdx.input.isKeyJustPressed(Constants.PAUSE_ESC)
            || Gdx.input.isKeyJustPressed(Constants.PAUSE_P)) {
            pausado = !pausado;
        }
        if (!pausado) {
            sistemaVidas.cederVidas(delta, 48f, 48f);
            if (sistemaVidas.isGameOver()) {
                System.out.println("GAME OVER");
            }
            sistemaColisiones.actualizarPlayer(salem, delta, 60f, 48f);
            // Lavender solo puede moverse si no espera rescate
            if (!sistemaVidas.isEsperaRescate()) {
                sistemaColisiones.actualizarPlayer(lavender, delta, 48f, 48f);
            }
            // Reset
            if (Gdx.input.isKeyJustPressed(Constants.RESET_KEY)) {
                resetNivel();
            }
        }
        // Limpiar pantalla y dejar en fondo blanco
        ScreenUtils.clear(Color.LIGHT_GRAY);
        // aplicar viewport, actualizar camara y mantriz del batch
        viewport.apply();
        camara.update();
        // Para prueba se dibuja el hitbox como rectangulo
        shapeRenderer.setProjectionMatrix(camara.combined); // para que tome la matriz de la camara
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled); // Para "dibujar" el render
        // Para practicas
        shapeRenderer.setColor(Color.BROWN);
        for (Rectangle tile : tilesSolidos) {
            shapeRenderer.rect(tile.x, tile.y, tile.width, tile.height);
        }
        shapeRenderer.setColor(Color.BLUE);
        for (Rectangle tPeli : tilesPeligros) {
            shapeRenderer.rect(tPeli.x, tPeli.y, tPeli.width, tPeli.height);
        }
        shapeRenderer.setColor(Color.PURPLE); // Lavender
        shapeRenderer.rect( // Para definir la posicion de aparicion y tamaño
            lavender.getBounds().x, lavender.getBounds().y,
            lavender.getBounds().width, lavender.getBounds().height
        );
        shapeRenderer.setColor(Color.GRAY); // Salem
        shapeRenderer.rect(
            salem.getBounds().x, salem.getBounds().y,
            salem.getBounds().width, salem.getBounds().height
        );
        // Barra de rescate sobre Lavender
        if (sistemaVidas.isEsperaRescate()) {
            float barraAncho = 64f;
            float barraAlto  = 6f;
            float barraX = lavender.getPosicion().x - (barraAncho / 2f)
                + (lavender.getBounds().width / 2f); // Para que quede centrada
            float barraY = lavender.getPosicion().y + lavender.getBounds().height + 4f; // Sobre lavender
            float proporcion = sistemaVidas.getTiempoEsperaRes() / sistemaVidas.getTiempoMaximo();

            shapeRenderer.setColor(Color.DARK_GRAY);
            shapeRenderer.rect(barraX, barraY, barraAncho, barraAlto);
            shapeRenderer.setColor(Color.CORAL);
            shapeRenderer.rect(barraX, barraY, barraAncho * proporcion, barraAlto);
        }
        shapeRenderer.end(); // Termina el dibujo y manda al GPU
        // Overlay de pausa sobre el juego
        if (pausado) {
            overlayPausa.dibujar();

            Enums.AccionPausa accionPausa = overlayPausa.manejarClicks();
            switch (accionPausa) {
                case CONTINUAR -> pausado = false;
                case RESET -> {
                    resetNivel();
                    pausado = false;
                }
                case MENU_PPAL -> {
                    game.setScreen(new MenuPrincipal(game));
                    dispose();
                }
                case NINGUNA -> {
                }
            }
        }
    }
    private void resetNivel () {
        lavender.resetear(48f, 48f);
        salem.resetear(60f, 48f);
        sistemaVidas.resetear();
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
        shapeRenderer.dispose(); // Libera los hitbox
        overlayPausa.dispose();
    }

}
