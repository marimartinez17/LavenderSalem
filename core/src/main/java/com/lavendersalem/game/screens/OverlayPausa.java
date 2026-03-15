package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.lavendersalem.game.utils.*;

public class OverlayPausa {
    private final OrthographicCamera camara;
    private final FitViewport viewport;
    private final BitmapFont font;
    private final SpriteBatch spriteBatch;
    private final ShapeRenderer shapeRenderer;
    // Overlay (panel)
    private final float ANCHO_PANEL = 200f;
    private final float ALTO_PANEL = 200f;
    private final float PANEL_X = (Constants.VIRTUAL_WIDTH / 2f) - (ANCHO_PANEL / 2f);
    private final float PANEL_Y = (Constants.VIRTUAL_HEIGHT / 2f) - (ALTO_PANEL / 2f);
    // Botones
    private final float PBTN_ANCHO = 125f;
    private final float PBTN_ALTO = 25f;
    private final float PBTN_X = PANEL_X + (ANCHO_PANEL /2f ) - (PBTN_ANCHO / 2f);
    private final float PBTN_CONTINUAR_Y = PANEL_Y + 160f;
    private final float PBTN_RESET_Y = PANEL_Y + 120f;
    private final float PBTN_MENUP_Y = PANEL_Y + 80f;

    private final Rectangle btnContinuar;
    private final Rectangle btnReset;
    private final Rectangle btnMenuPpal;
    // Constructor
    public OverlayPausa (OrthographicCamera camara, FitViewport viewport) {
        this.camara = camara;
        this.viewport = viewport;
        font = new BitmapFont();
        spriteBatch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        btnContinuar = new Rectangle(PBTN_X, PBTN_CONTINUAR_Y, PBTN_ANCHO, PBTN_ALTO);
        btnReset = new Rectangle(PBTN_X, PBTN_RESET_Y, PBTN_ANCHO, PBTN_ALTO);
        btnMenuPpal = new Rectangle(PBTN_X, PBTN_MENUP_Y, PBTN_ANCHO, PBTN_ALTO);
    }

    public void dibujar () {
        shapeRenderer.setProjectionMatrix(camara.combined);

        // Activar blending para la transparencia del fondo (opnGl)
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Fondo oscuro semitransparente
        shapeRenderer.setColor(0f, 0f,0f,0.6f);
        shapeRenderer.rect(0,0,Constants.VIRTUAL_WIDTH,Constants.VIRTUAL_HEIGHT);
        // Panel
        shapeRenderer.setColor(Color.DARK_GRAY);
        shapeRenderer.rect(PANEL_X, PANEL_Y,ANCHO_PANEL, ALTO_PANEL);
        // Cuadros de botones
        shapeRenderer.setColor(Color.PURPLE);
        shapeRenderer.rect(btnContinuar.x, btnContinuar.y, btnContinuar.width, btnContinuar.height);
        shapeRenderer.rect(btnReset.x, btnReset.y, btnReset.width, btnReset.height);
        shapeRenderer.rect(btnMenuPpal.x, btnMenuPpal.y, btnMenuPpal.width, btnMenuPpal.height);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND); // Desactivar el blendig

        spriteBatch.setProjectionMatrix(camara.combined);
        spriteBatch.begin();

        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "PAUSA", PANEL_X + (ANCHO_PANEL / 2f) - 22.5f,
            PANEL_Y + ALTO_PANEL + 10f);
        font.draw(spriteBatch, "Continuar", PBTN_X + 10f, PBTN_CONTINUAR_Y + 18f);
        font.draw(spriteBatch, "Resetear Nivel", PBTN_X + 10f, PBTN_RESET_Y + 18f);
        font.draw(spriteBatch, "Menú Principal", PBTN_X + 10f, PBTN_MENUP_Y + 18f);

        spriteBatch.end();
    }

    public Enums.AccionPausa manejarClicks () {
        if (!Gdx.input.justTouched()) return Enums.AccionPausa.NINGUNA;

        Vector3 click = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        viewport.unproject(click);

        if (btnContinuar.contains(click.x, click.y)) return Enums.AccionPausa.CONTINUAR;
        if (btnReset.contains(click.x, click.y)) return Enums.AccionPausa.RESET;
        if (btnMenuPpal.contains(click.x, click.y)) return Enums.AccionPausa.MENU_PPAL;

        return Enums.AccionPausa.NINGUNA; // Click fuera de los botones
    }

    public void dispose() {
        spriteBatch.dispose();
        shapeRenderer.dispose();
        font.dispose();
    }

}
