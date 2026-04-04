package com.lavendersalem.game.tools;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private TextureRegion[] frames;
    private float time;
    private float delay;
    private  int currentFrame;
    private int timesPlayed;

    public MyAnimation(){}

    public MyAnimation(TextureRegion[] frames){
        this(frames, 1/12f);
    }

    public MyAnimation(TextureRegion[] frames, float delay){
        setFrames(frames, delay);
    }

    public void setFrames(TextureRegion[] frames, float delay){
        this.frames = frames;
        this.delay = delay;
        time = 0;
        timesPlayed = 0;
        currentFrame = 0;
    }

    public void update(float delta){
        if (delay <= 0) return; // if there is no delay, dont move the animation
        time += delta;
        while (time >= delay){
            step();
        }
    }

    private void step(){
        time -= delay;
        currentFrame++;
        if (currentFrame == frames.length){
            currentFrame = 0;
            timesPlayed++;
        }
    }

    public TextureRegion getFrame(){
        return frames[currentFrame];
    }

    public int getTimesPlayed(){
        return timesPlayed;
    }
}
