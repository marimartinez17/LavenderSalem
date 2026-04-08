package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.utils.Constants;


public class MenuPrincipal implements Screen {
    private final LavenderSalemGame game;
    private final OrthographicCamera camera;
    private final FitViewport viewport;
    private final BitmapFont font; // Para texto
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch spriteBatch;
    // Botones del menu (Como rectangulos para dibujar)
    private final Rectangle btnJugar;
    private final Rectangle btnSelecNivel;
    private final Rectangle btnOpciones;
    private final Rectangle btnSalir;
    // Dimensiones de cada boton
    private static final float BOTON_ANCHO = 200f;
    private static final float BOTON_ALTO = 30f;
    private static final float BOTON_X = (Constants.VIRTUAL_WIDTH / 2f) - (BOTON_ANCHO / 2f);
    // Botones apilados
    private static final float Y_JUGAR = 200f;
    private static final float Y_NIVELES = 155f;
    private static final float Y_OPCIONES = 110f;
    private static final float Y_SALIR = 65f;
    // Constructor
    public MenuPrincipal(LavenderSalemGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, camera);
        font = new BitmapFont();
        shapeRenderer = new ShapeRenderer();
        spriteBatch = new SpriteBatch();
        // Config. botones
        btnJugar = new Rectangle(BOTON_X, Y_JUGAR, BOTON_ANCHO, BOTON_ALTO);
        btnSelecNivel = new Rectangle(BOTON_X, Y_NIVELES, BOTON_ANCHO, BOTON_ALTO);
        btnOpciones = new Rectangle(BOTON_X, Y_OPCIONES, BOTON_ANCHO, BOTON_ALTO);
        btnSalir = new Rectangle(BOTON_X, Y_SALIR, BOTON_ANCHO, BOTON_ALTO);
    }
    @Override
    public void show() { }

    @Override
    public void render(float delta) {
        // Limpiar pantalla
        ScreenUtils.clear(Color.DARK_GRAY);
        viewport.apply();
        camera.update();
        // Detectar el clic y convertir coordenadas de pantalla a virtual
        if (Gdx.input.justTouched()) {
            Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            viewport.unproject(click); // Para pasar a coor. virtual

            if (btnJugar.contains(click.x, click.y)) {
                game.setScreen(new PlayScreen(game,1, game.getLvl1()));
                dispose();
                return;
            }
            if (btnSelecNivel.contains(click.x, click.y)) {
                System.out.println("NIVELES");
            }
            if (btnOpciones.contains(click.x, click.y)) {
                System.out.println("OPCIONES");
            }
            if (btnSalir.contains(click.x, click.y)) {
                Gdx.app.exit();
            }
        }
        // Dinujar rectangulos botones
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(btnJugar.x, btnJugar.y, btnJugar.width, btnJugar.height);
        shapeRenderer.rect(btnSelecNivel.x, btnSelecNivel.y, btnSelecNivel.width, btnSelecNivel.height);
        shapeRenderer.rect(btnOpciones.x, btnOpciones.y, btnOpciones.width, btnOpciones.height);
        shapeRenderer.rect(btnSalir.x, btnSalir.y, btnSalir.width, btnSalir.height);

        shapeRenderer.end();
        // Se pasa el batch para cargar font
        spriteBatch.setProjectionMatrix(camera.combined);
        spriteBatch.begin();

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "LAVENDER & SALEM", BOTON_X + 20f, 260f);
        font.draw(spriteBatch, "Jugar", BOTON_X + 20f, Y_JUGAR + 20f);
        font.draw(spriteBatch, "Niveles", BOTON_X + 20f, Y_NIVELES + 20f);
        font.draw(spriteBatch, "Opciones", BOTON_X + 20f, Y_OPCIONES + 20f);
        font.draw(spriteBatch, "Salir", BOTON_X + 20f, Y_SALIR + 20f);

        spriteBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }
}
