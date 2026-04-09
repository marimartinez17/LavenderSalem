package com.lavendersalem.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lavendersalem.game.world.LavenderSalemGame;
import com.lavendersalem.game.screens.*;
import com.lavendersalem.game.utils.Constants;

public class OverlayPausa {
    private final LavenderSalemGame game;
    private final Stage stage;
    private boolean visible = false;
    private boolean continuar = false;
    private boolean resetearNivel = false;

    private Texture texContinuarUp, texContinuarOver, texReintentarUp, texReintentarOver,
        texOpcionesUp, texOpcionesOver, texMenuUp, texMenuOver;
    private Texture texPanelMenu, texPergaminoTitulo;
    private Texture texFondoOscuro;

    public OverlayPausa(LavenderSalemGame game) {
        this.game = game;
        this.stage = new Stage(new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT));

        cargarTexturas();
        cargarInterfaz();
    }

    private void cargarTexturas() {
        // Para el fondo
        Pixmap pixmap = new Pixmap(1,1,Pixmap.Format.RGBA4444);
        pixmap.setColor(0,0,0,0.6f); // Opaca al 60%
        pixmap.fill();
        texFondoOscuro = new Texture(pixmap);
        // Panel
        texPanelMenu = new Texture("ui/menus/Panel_fondo_menu.png");
        texPergaminoTitulo = new Texture("ui/menus/Pausa_titulo.png");
        // Botones
        texContinuarUp = new Texture("ui/menus/Boton_continuar.png");
        texContinuarOver = new Texture("ui/menus/Boton_continuar_over.png");
        texReintentarUp = new Texture("ui/menus/Boton_reintentar.png");
        texReintentarOver = new Texture("ui/menus/Boton_reintentar_over.png");
        texOpcionesUp = new Texture("ui/menus/Boton_opciones.png");
        texOpcionesOver = new Texture("ui/menus/Boton_opciones_hover.png");
        texMenuUp = new Texture("ui/menus/Boton_menu.png");
        texMenuOver = new Texture("ui/menus/Boton_menu_over.png");
    }

    private void cargarInterfaz() {
        Table contenedorPpal = new Table();
        contenedorPpal.setFillParent(true);

        Image fondo = new Image(texFondoOscuro);
        fondo.setFillParent(true);
        stage.addActor(fondo); // Para que quede detras del menu

        // Menu centrado
        Table panelMenu = new Table();

        Image tituloPaussa = new Image(texPergaminoTitulo);
        panelMenu.add(tituloPaussa).width(180).height(60).padBottom(-10).row();
        // Para organizar el menu con stack
        Stack stackMenu = new Stack();
        Image marcoMenu = new Image(texPanelMenu);
        // Para los botones que quedan sobre el panel por stack
        Table botones = new Table();

        // Botones
        Button btnContinuar = crearBoton(texContinuarUp, texContinuarOver);
        Button btnReintentar = crearBoton(texReintentarUp, texReintentarOver);
        Button btnOpciones = crearBoton(texOpcionesUp, texOpcionesOver);
        Button btnMenu = crearBoton(texMenuUp, texMenuOver);

        configurarListener(btnContinuar, btnReintentar, btnOpciones, btnMenu);
        // Configuración de layout de botones
        float AnchoBoton = 160f;
        float AltoBoton = 40f;

        botones.add(btnContinuar).width(AnchoBoton).height(AltoBoton).padBottom(6).row();
        botones.add(btnReintentar).width(AnchoBoton).height(AltoBoton).padBottom(6).row();
        botones.add(btnOpciones).width(AnchoBoton).height(AltoBoton).padBottom(6).row();
        botones.add(btnMenu).width(AnchoBoton).height(AltoBoton);

        stackMenu.add(marcoMenu);    // Atrás
        stackMenu.add(botones); // Adelante

        // Añadimos el stack al contenedor del menú
        panelMenu.add(stackMenu).width(220).height(230);

        contenedorPpal.add(panelMenu);
        stage.addActor(contenedorPpal);
    }

    private ImageButton crearBoton(Texture up, Texture over) {
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.up = new TextureRegionDrawable(new TextureRegion(up));
        style.over = new TextureRegionDrawable(new TextureRegion(over));
        return new ImageButton(style);
    }

    private void configurarListener(Button btnContinuar, Button btnReintentar, Button btnOpcines, Button btnMenu) {
        btnContinuar.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                continuar = true;
            }
        });
        btnReintentar.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                resetearNivel = true;
                visible = false;
            }
        });
        btnOpcines.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("OPCIONES");
            }
        });
        btnMenu.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MenuPrincipal(game));
            }
        });

    }

    public void show() {
        visible = true;
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float delta) {
        if (visible) {
            stage.act(delta);
            stage.draw();
        }
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    public void dispose() {
        stage.dispose();
        texFondoOscuro.dispose();
        texPergaminoTitulo.dispose();
        texPanelMenu.dispose();
        texContinuarUp.dispose();   texContinuarOver.dispose();
        texReintentarUp.dispose();  texReintentarOver.dispose();
        texOpcionesUp.dispose();    texOpcionesOver.dispose();
        texMenuUp.dispose();        texMenuOver.dispose();
    }

    public void setVisible(boolean visible) { this.visible = visible; }

    public boolean isContinuar() { return continuar; }
    public void setContinuar(boolean continuar) { this.continuar = continuar; }

    public boolean isResetearNivel() { return resetearNivel; }
    public void setResetearNivel(boolean resetearNivel) { this.resetearNivel = resetearNivel; }
}
