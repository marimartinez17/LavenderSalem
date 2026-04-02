package com.lavendersalem.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.LavenderSalemGame;
import com.lavendersalem.game.utils.Constants;

public class PlayScreen implements Screen {
    private LavenderSalemGame game;
    private int lvl;
    private Hud hud;

    // Camera and viewport attributes
    private OrthographicCamera gameCam;
    private Viewport gamePort;

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
        gamePort = new FitViewport(480, 416, gameCam);

        // game HUD for crystals/timer/level info
        hud = new Hud(game.batch, lvl);

        mapLoader = new TmxMapLoader();
        map = mapLoader.load("maps/nivel1/nivel1.tmx");

        int width = (int) map.getProperties().get("width", Integer.class);
        int height = (int) map.getProperties().get("height", Integer.class);

        renderer = new OrthogonalTiledMapRenderer(map);
        gameCam.position.set(480/2, 416/2, 0);

        world = new World(new Vector2(0,0), true);
        b2dr = new Box2DDebugRenderer();

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

/*        // Print names of tiled layers
        System.out.println("Map has " + map.getLayers().size() + " layers:");
        for (int i = 0; i < map.getLayers().size(); i++) {
            System.out.println("  [" + i + "] " + map.getLayers().get(i).getName());
        }*/

        for (MapObject object: map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set(rect.getX() + rect.getWidth() / 2, rect.getY() + rect.getHeight() / 2);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2, rect.getHeight() / 2); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            body.createFixture(fdef);
        }
    }

    public void handleInput(float delta){
        if (Gdx.input.isTouched()){
            gameCam.position.y += 100 * delta;
        }
    }

    public void update(float delta) {
        gameCam.update();
        renderer.setView(gameCam);

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);

        // clear game screen with black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // render game map
        renderer.render();

        // render box2ddebuglines
        b2dr.render(world,gameCam.combined);

        // set our batch to render what the camera sees
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
