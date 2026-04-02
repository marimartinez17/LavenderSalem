package com.lavendersalem.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.utils.Constants;

public class PlayScreen implements Screen {
    private LavenderSalemGame game;
    private int lvl;
    private Hud hud;

    private OrthographicCamera gameCam;
    private Viewport gamePort;

    private TmxMapLoader mapLoader;
    private TiledMap map; // reference to the map itself
    private OrthogonalTiledMapRenderer renderer;

    public PlayScreen(LavenderSalemGame game, int lvl) {
        this.game = game;
        this.lvl = lvl;

        // To follow the characters through cam world
        gameCam = new OrthographicCamera();

        // mantain aspect ratio
        gamePort = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, gameCam);

        // game HUD for crystals/timer/level info
        hud = new Hud(game.batch, lvl);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("mapas/nivel1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(gamePort.getScreenWidth()/2, gamePort.getScreenHeight()/2, 0);
    }

    public void update(float delta) {
        delta = Math.min(delta, Constants.DELTA_MAXIMO);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();


    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
