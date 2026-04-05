package com.lavendersalem.game.collectables;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.lavendersalem.game.tools.MyAnimation;
import com.lavendersalem.game.utils.B2DVars;

public class Collectable {
    protected Body body;
    protected MyAnimation animation;
    protected float width;
    protected float height;

    public Collectable(Body body){
        this.body = body;
        animation = new MyAnimation();
    }

    public void setAnimation(TextureRegion[] frames, float delay){
        animation.setFrames(frames, delay);
        width = frames[0].getRegionWidth();
        height = frames[0].getRegionHeight();
    }

    public void update(float delta){
        animation.update(delta);
    }

    public void render(SpriteBatch sb){
        sb.draw(animation.getFrame(),
            body.getPosition().x - width / B2DVars.PPM /2,
            body.getPosition().y - height / B2DVars.PPM /2,
            width / B2DVars.PPM,
            height / B2DVars.PPM);
    }

    public Body getBody() {
        return body;
    }

    public Vector2 getPosition(){
        return new Vector2(body.getPosition());
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
