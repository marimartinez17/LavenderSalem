package com.lavendersalem.game.tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.enemies.Batty;
import com.lavendersalem.game.mechanics.MovingPlatform;
import com.lavendersalem.game.screens.PlayScreen;
import com.lavendersalem.game.collectables.Crystal;
import com.lavendersalem.game.mechanics.Box;
import com.lavendersalem.game.sprites.Lavender;
import com.lavendersalem.game.sprites.Salem;
import com.lavendersalem.game.utils.B2DVars;

public class LevelCreator {
    private Array<Crystal> crystals;
    private Array<Box> boxes;
    private Array<Batty> batties;
    private Array<MovingPlatform> movingPlatforms;
    private Lavender lavender;
    private Salem salem;


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

        // Create fixtures -> platforms
        for (MapObject obj: map.getLayers().get("objects-spawn-lavender").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            float x = (float)obj.getProperties().get("x");
            float y = (float)obj.getProperties().get("y");

            lavender = new Lavender(screen,x,y,16,32);
        }

        // Create fixtures -> platforms
        for (MapObject obj: map.getLayers().get("objects-spawn-salem").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            float x = (float)obj.getProperties().get("x");
            float y = (float)obj.getProperties().get("y");

            salem = new Salem(screen,x,y,16,16);
        }

        crystals = new Array<Crystal>();

        boxes = new Array<Box>();
        MapLayer layer = new MapLayer();

        float mapHeight = ((Number)map.getProperties().get("height", Integer.class)).floatValue() * ((Number)map.getProperties().get("tileheight",Integer.class)).floatValue();


        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();

        for (MapObject obj : map.getLayers().get("objects-crystals").getObjects().getByType(EllipseMapObject.class)) {
            float x = ((float) obj.getProperties().get("x")) / B2DVars.PPM;
            float y = ((float)obj.getProperties().get("y")) / B2DVars.PPM;

            bDef.type = BodyDef.BodyType.StaticBody;
            bDef.position.set(x + (8/ B2DVars.PPM), y + (8/ B2DVars.PPM));

            // create fixture shape
            CircleShape cs = new CircleShape();
            cs.setRadius(8 / B2DVars.PPM);

            // create fixturedef for player collision box
            fDef.shape = cs;
            fDef.isSensor = true;
            fDef.filter.categoryBits = B2DVars.OBJECTS_CRYSTALS;
            fDef.filter.maskBits = B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM;

            body = world.createBody(bDef);
            Crystal c = new Crystal(body);
            body.createFixture(fDef).setUserData("crystal");
            body.setUserData(c);

            //
            crystals.add(c);

            cs.dispose();
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

        //boxes
        boxes = new Array<>();
        for (MapObject object: map.getLayers().get("objects-interactive").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            float x = (float)rect.getX() - rect.getWidth() / 2;
            float y = (float)rect.getY() - rect.getHeight() / 2;

            boxes.add(new Box(screen,x,y,rect.getWidth(),rect.getHeight()));
        }

        for (MapObject object: map.getLayers().get("objects-danger").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.BIT_DANGER;
            fdef.filter.maskBits = B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM | B2DVars.PLATFORMS;
            fdef.isSensor = true;
            body.createFixture(fdef);
        }

        for (MapObject object: map.getLayers().get("objects-portals").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.BIT_PORTALS;
            fdef.filter.maskBits = B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM;
            fdef.isSensor = true;
            body.createFixture(fdef);
        }


        for (MapObject object: map.getLayers().get("objects-water").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.BIT_WATER;
            fdef.filter.maskBits = B2DVars.BIT_LAVENDER | B2DVars.BIT_SALEM | B2DVars.PLATFORMS;
            body.createFixture(fdef);
        }


        // create enemies -> battys
        batties = new Array<Batty>();

        for (MapObject obj : map.getLayers().get("objects-enemies").getObjects().getByType(EllipseMapObject.class)) {
            Ellipse ellipse = ((EllipseMapObject) obj).getEllipse();

            float x = (float)obj.getProperties().get("x");
            float y = (float)obj.getProperties().get("y");

            batties.add(new Batty(screen,x,y));
        }

        // moving platforms
        movingPlatforms = new Array<>();
        for (MapObject obj: map.getLayers().get("objects-moving").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) obj).getRectangle();

            float ax = (rect.getX() + rect.getWidth() / 2)/B2DVars.PPM;
            float ay = (rect.getY() + rect.getHeight() / 2)/B2DVars.PPM;

            float bx = ((Float) obj.getProperties().get("endX") + rect.getWidth() / 2)/B2DVars.PPM;
            float by = (mapHeight - (Float) obj.getProperties().get("endY") - rect.getHeight() / 2)/B2DVars.PPM;

            float velocity = 0.8f;

            movingPlatforms.add(new MovingPlatform(screen,rect.getWidth(),rect.getHeight(), new Vector2(ax,ay),new Vector2(bx,by),velocity));
        }
        shape.dispose();
    }

    public Lavender getLavender() {
        return lavender;
    }

    public Salem getSalem() {
        return salem;
    }

    public Array<Batty> getBatties() {
        return batties;
    }

    public Array<Crystal> getCrystals() { return crystals; }

    public Array<Box> getBoxes() { return boxes; }

    public Array<MovingPlatform> getMovingPlatforms() {
        return movingPlatforms;
    }

    public int getGetTotalCrystals() {
        return crystals.size;
    }
}
