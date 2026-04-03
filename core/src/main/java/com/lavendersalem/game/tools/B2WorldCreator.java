package com.lavendersalem.game.tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.lavendersalem.game.sprites.Crystal;
import com.lavendersalem.game.sprites.InteractiveTileObject;
import com.lavendersalem.game.utils.B2DVars;

public class B2WorldCreator {
    public B2WorldCreator(World world, TiledMap map) {
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
            body.createFixture(fdef);
        }

        // Create fixtures -> crystals/gems :)
        for (MapObject object: map.getLayers().get("objects-crystals").getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            new Crystal(world,map,rect);
        }
        //lavender = new Lavender(200f,222f);
    }
}
