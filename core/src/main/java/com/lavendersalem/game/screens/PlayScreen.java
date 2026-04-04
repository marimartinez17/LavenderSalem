package com.lavendersalem.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.sprites.Lavender;
import com.lavendersalem.game.sprites.Salem;
import com.lavendersalem.game.tools.LevelCreator;
import com.lavendersalem.game.tools.WorldContactListener;
import com.lavendersalem.game.utils.B2DVars;

public class PlayScreen implements Screen {

    // player sprites
    private Lavender lavender;
    private Salem salem;

    private LavenderSalemGame game;
    private int lvl;
    private Hud hud;

    // Camera and viewport attributes
    private OrthographicCamera gameCam;
    private Viewport gamePort;
    private Vector2 lastMovement;

    // Tiled map attributes
    private TmxMapLoader mapLoader;
    private TiledMap map; // reference to the map itself
    private OrthogonalTiledMapRenderer renderer;

    // Box2D Variables
    private World world; // to create a simulation world
    private Box2DDebugRenderer b2dr; // to visualize the underlying physics simulation

    // Constructor
    public PlayScreen(LavenderSalemGame game, int lvl) {
        this.game = game;
        this.lvl = lvl;

        // To follow the characters through cam world
        gameCam = new OrthographicCamera();

        // mantain aspect ratio
        gamePort = new FitViewport(480 / B2DVars.PPM, 416 / B2DVars.PPM, gameCam);

        // game HUD for crystals/timer/level info
        hud = new Hud(game.batch, lvl);

        // load tiled map
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/nivel1/nivel1.tmx");

        // get width and height of the tilemap for the game camera (provisional)
        int width = (int) map.getProperties().get("width", Integer.class);
        int height = (int) map.getProperties().get("height", Integer.class);
        renderer = new OrthogonalTiledMapRenderer(map, 1/ B2DVars.PPM);

        // establishing game camera position
        gameCam.position.set((width / 2f) / B2DVars.PPM, (height / 2f) / B2DVars.PPM, 0);

        // creating box2D world
        world = new World(new Vector2(0,-9.8f), true);

        // rendering debug lines (to visualize the collisions)
        b2dr = new Box2DDebugRenderer();

        // load level
        new LevelCreator(world, map);

        // loading player sprites
        lavender = new Lavender(world,20,20,16,32);
        salem = new Salem(world, 20,20,16,16);
        lastMovement = new Vector2(lavender.b2body.getPosition().x, lavender.b2body.getPosition().y);


        // establish contact listener
        world.setContactListener(new WorldContactListener());
    }

    public void update(float delta) {
        // how bodies react to collisions
        world.step(1/60f, 6, 2);


        gameCam.zoom = 0.5f;

        // get velocity in the x-axis
        float salemVelX   = salem.b2body.getLinearVelocity().x;
        float lavenderVelX = lavender.b2body.getLinearVelocity().x;

        // setting up camera to follow the last player who moved
        if (salemVelX != 0f){
            lastMovement.set(salem.b2body.getPosition().x, salem.b2body.getPosition().y);
        } else if (lavenderVelX != 0f){
            lastMovement.set(lavender.b2body.getPosition().x,lavender.b2body.getPosition().y);
        }

        // change position of the camera to the last movement and use LERP for linear interpolation
        gameCam.position.x += (lastMovement.x - gameCam.position.x) * B2DVars.CAM_LERP;
        gameCam.position.y += (lastMovement.y - gameCam.position.y) * B2DVars.CAM_LERP;


        // update player sprites
        lavender.update(delta);
        salem.update(delta);

        //update camera
        gameCam.update();

        // tell renderer to only draw what the camera sees
        renderer.setView(gameCam);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);


        // clear game screen with black
        Gdx.gl.glClearColor(0.18f, 0.18f, 0.18f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render game map
        renderer.render();

        // render box2ddebuglines
        b2dr.render(world,gameCam.combined);

        // set our batch to render what the camera sees
        game.batch.setProjectionMatrix(gameCam.combined);
        game.batch.begin();
        lavender.draw(game.batch);
        salem.draw(game.batch);
        game.batch.end();

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
        map.dispose();
        renderer.dispose();
        b2dr.dispose();
        world.dispose();
    }
}
