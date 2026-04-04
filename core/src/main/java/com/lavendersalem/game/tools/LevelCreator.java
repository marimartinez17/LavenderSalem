package com.lavendersalem.game.tools;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.lavendersalem.game.sprites.Crystal;
import com.lavendersalem.game.sprites.Crystal2;
import com.lavendersalem.game.utils.B2DVars;

public class LevelCreator {
    private Array<Crystal2> crystals = new Array<>();

    public LevelCreator(World world, TiledMap map) {
// create body and fixture variables
        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;


/*        // Print names of tiled layers
        System.out.println("Map has " + map.getLayers().size() + " layers:");
        for (int i = 0; i < map.getLayers().size(); i++) {
            System.out.println("  [" + i + "] " + map.getLayers().get(i).getName());
        }*/

        // Create fixtures -> platforms
        for (MapObject object: map.getLayers().get("platforms").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2)/ B2DVars.PPM, (rect.getY() + rect.getHeight() / 2)/ B2DVars.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / B2DVars.PPM, rect.getHeight() / 2 / B2DVars.PPM); // divided by two bcs it is located in the center of the boxes
            fdef.shape = shape;
            fdef.filter.categoryBits = B2DVars.PLATFORMS;
            fdef.filter.maskBits = B2DVars.BIT_PLAYER;
            body.createFixture(fdef);
        }

        crystals = new Array<Crystal2>();

        MapLayer layer = new MapLayer();
        layer = map.getLayers().get("objects-crystals");
        BodyDef bDef = new BodyDef();
        FixtureDef fDef = new FixtureDef();

        for (MapObject obj : layer.getObjects()) {
            bDef.type = BodyDef.BodyType.StaticBody;
            float x = (float) obj.getProperties().get("x") / B2DVars.PPM;
            float y = (float) obj.getProperties().get("y") / B2DVars.PPM;

            bDef.position.set(x, y);

            CircleShape cs = new CircleShape();
            cs.setRadius(8 / B2DVars.PPM);
            fDef.shape = cs;
            fDef.isSensor = true;
            fDef.filter.categoryBits = B2DVars.OBJECTS_CRYSTALS;
            fDef.filter.maskBits = B2DVars.BIT_PLAYER;

            body = world.createBody(bDef);

            Crystal2 c = new Crystal2(body);
            body.createFixture(fDef).setUserData("crystal");
            body.setUserData(c);

            crystals.add(c);
        }
    }

    public Array<Crystal2> getCrystals() { return crystals; }
}
