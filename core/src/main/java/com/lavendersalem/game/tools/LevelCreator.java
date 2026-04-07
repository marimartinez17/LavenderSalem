package com.lavendersalem.game.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.CircleMapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.enemies.Batty;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.collectables.Crystal;
import com.lavendersalem.game.utils.B2DVars;

import static java.lang.System.getProperties;

public class LevelCreator {
    private Array<Crystal> crystals = new Array<>();
    private Array<Batty> batties;


    public LevelCreator(PlayScreen screen) {
        World world = screen.getWorld();
        TiledMap map = screen.getMap();
// create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


       // Print names of tiled layers
        System.out.println("Map has " + map.getLayers().size() + " layers:");
        for (int i = 0; i < map.getLayers().size(); i++) {
            System.out.println("  [" + i + "] " + map.getLayers().get(i).getName());
        }

        // Create fixtures -> platforms
        for (MapObject object: map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.PLATFORMS;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER | B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM | B2DVars.BIT_ENEMY;
            body.createFixture(fdef);
        }

        crystals = new Array<Crystal>();

        MapLayer layer = new MapLayer();

        float mapHeight = ((Number)map.getProperties().get("height", Integer.class)).floatValue() * ((Number)map.getProperties().get("tileheight",Integer.class)).floatValue();


        layer = map.getLayers().get("objects-crystals");
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();

        for (MapObject obj : layer.getObjects()) {
            bDef.type = BodyDef.BodyType.StaticBody;
            float x = ((float) obj.getProperties().get("x") + 32f/2) / B2DVars.PPM;
            float y = (mapHeight - (float)obj.getProperties().get("y") + 32f/2) / B2DVars.PPM;

            bDef.position.set(x, y);

            CircleShape cs = new CircleShape();
            cs.setRadius(8 / B2DVars.PPM);
            fDef.shape = cs;
            fDef.isSensor = true;
            fDef.filter.categoryBits = B2DVars.OBJECTS_CRYSTALS;
            fDef.filter.maskBits = B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM;

            body = world.createBody(bDef);

            Crystal c = new Crystal(body);
            body.createFixture(fDef).setUserData("crystal");
            body.setUserData(c);

            crystals.add(c);
        }

        // create invisible bareer for enemies

        for (MapObject object: map.getLayers().get("objects-obstacles").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.OBJECTS_OBSTACLES;
            fdef.filter.maskBits = B2DVars.BIT_ENEMY | B2DVars.PLATFORMS;
            body.createFixture(fdef);
        }

        // create enemies -> battys
        batties = new Array<Batty>();

        for (MapObject obj : map.getLayers().get("objects-enemies").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) obj).getEllipse();

            float x = (float)obj.getProperties().get("x");
            float y = (float)obj.getProperties().get("y");

            Gdx.app.log("Batty spawn", "x=" + x + " y=" + y);
            batties.add(new Batty(screen,x,y));
        }

    }

    public Array<Batty> getBatties() {
        return batties;
    }

    public Array<Crystal> getCrystals() { return crystals; }
}
