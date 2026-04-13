package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lavendersalem.game.world.LavenderSalemGame;
import com.lavendersalem.game.utils.Constants;

public class MenuPrincipal implements Screen {
    private final LavenderSalemGame game;
    private final Stage stage;
    private Texture texFondoPantalla, texLogoGame, texPanelFondo, texJugarUp, texJugarHover,
        texNivelesUp, texNivelesHover, texCreditosUp, texCreditosHover,
        texSalirUp, texSalirHover;

    public MenuPrincipal (LavenderSalemGame game) {
        this.game = game;
        // Se crea el escenario con fitVieport
        this.stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage); // El stage recibe los cliks

        cargarTexturas();
        cargarInterfaz();
    }

    private void cargarTexturas() {
        texFondoPantalla = new Texture("ui/menus/Fondo_menuppal.png");
        texFondoPantalla.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        texLogoGame = new Texture("ui/menus/Logo_lavendersalem.png");
        texLogoGame.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        texPanelFondo = new Texture("ui/menus/Panel_fondo_menu.png");
        texPanelFondo.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        texJugarUp = new Texture("ui/menus/Boton_jugar.png");
        texJugarHover = new Texture("ui/menus/Boton_jugar_hover.png");

        texNivelesUp = new Texture("ui/menus/Boton_niveles.png");
        texNivelesHover = new Texture("ui/menus/Boton_niveles_hover.png");

        texCreditosUp = new Texture("ui/menus/Boton_creditos.png");
        texCreditosHover = new Texture("ui/menus/Boton_creditos_over.png");

        texSalirUp = new Texture("ui/menus/Boton_salir.png");
        texSalirHover = new Texture("ui/menus/Boton_salir_hover.png");
    }

    private void cargarInterfaz() {
        // Fondo pantalla
        Image fondoPantalla = new Image(texFondoPantalla);
        fondoPantalla.setFillParent(true);
        stage.addActor(fondoPantalla);

        Table contenedorPrincipal = new Table(); // Para alinear los assets
        contenedorPrincipal.setFillParent(true);
        contenedorPrincipal.left().top(); // Centra arriba a la izquierda

        Image logo = new Image(texLogoGame);
        contenedorPrincipal.add(logo).width(210f).height(110f).padTop(10f).padBottom(10).
            padLeft(40).left().row(); // Espacio del logo
        // Para el menu como tal
        Table menuTable = new Table();
        TextureRegionDrawable fondo = new TextureRegionDrawable(new TextureRegion(texPanelFondo));
        menuTable.setBackground(fondo);
        // Tamaños
        float panelAncho = 210f;
        float panelAlto = 215f;
        float btnAncho = 140f;
        float btnAlto = 30f;
        /* BOTONES */
        menuTable.add().height(10).row(); // Espacio vacio
        // Boton jugar
        ImageButton btnJugar = crearBoton(texJugarUp, texJugarHover);
        menuTable.add(btnJugar).width(btnAncho).height(btnAlto).pad(3).row();
        // Boton niveles
        ImageButton btnNiveles = crearBoton(texNivelesUp, texNivelesHover);
        menuTable.add(btnNiveles).width(btnAncho).height(btnAlto).pad(3).row();
        // boton Creditos
        ImageButton btnOpciones = crearBoton(texCreditosUp, texCreditosHover);
        menuTable.add(btnOpciones).width(btnAncho).height(btnAlto).pad(3).row();
        // Boton Salir
        ImageButton btnSalir = crearBoton(texSalirUp, texSalirHover);
        menuTable.add(btnSalir).width(btnAncho).height(btnAlto).pad(3).padBottom(10).row(); // Con pad para que no se pegue al borde inferior
        // Añadir el panel del menu a la tabla principal con separación del borde
        contenedorPrincipal.add(menuTable).width(panelAncho).height(panelAlto).padLeft(50).left();
        // Añadimos la tabla al escenario para que se dibuje
        stage.addActor(contenedorPrincipal);
        // Configuarcion de clicks
        configurarListeners(btnJugar, btnNiveles, btnOpciones, btnSalir);
    }

    private ImageButton crearBoton(Texture up, Texture over) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(up));
        style.over = new TextureRegionDrawable(new TextureRegion(over));
        return new ImageButton(style);
    }
    private void configurarListeners(ImageButton btnJugar, ImageButton btnNiveles, ImageButton btnOpciones,
                                     ImageButton btnSalir) {
        /* MANEJO DE CLICKS */
        btnJugar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new PlayScreen(game, 1, LavenderSalemGame.getLvl1(), LavenderSalemGame.manager.get("music/cooties.mp3", Music.class)));
                dispose();
            }
        });

        btnNiveles.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuNiveles(game));
            }
        });

        btnOpciones.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new ScreenCreditos(game));
            }
        });

        btnSalir.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0,0,0,1);
        stage.getViewport().apply();
        // Fondo
        stage.act(delta); // Actualiza la lógica del menú
        stage.draw(); // Dibuja los assets
    }

    @Override
    public void resize(int width, int height) {
        // Con scene2d el resize se hace directamente al Viewport del Stage
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        stage.dispose();
        texFondoPantalla.dispose();
        texPanelFondo.dispose();
        texJugarUp.dispose(); texJugarHover.dispose();
        texNivelesUp.dispose(); texNivelesHover.dispose();
        texCreditosUp.dispose(); texCreditosHover.dispose();
        texSalirUp.dispose(); texSalirHover.dispose();
    }
}
