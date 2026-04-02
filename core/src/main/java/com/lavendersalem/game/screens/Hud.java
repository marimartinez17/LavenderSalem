package com.lavendersalem.game.screens;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.lavendersalem.game.utils.Constants;

public class Hud {
    public Stage stage;
    // Specific viewport for the screen, the rest of the game
    // moves and resizes independently
    private Viewport viewport;
    private Integer worldTimer;
    private float timeCount;
    private int collectedCrystals;
    private int level;

    // txt labels
    Label countdownLabel;
    Label timeLabel;
    Label crystalsLabel;
    // value labels
    Label levelLabel;
    Label worldLabel;
    Label collectedLabel;

    public Hud(SpriteBatch sb, int level) {
        this.level = level;
        worldTimer = 300;
        timeCount = 0;
        collectedCrystals = 0;

        viewport = new FitViewport(Constants.VIRTUAL_WIDTH, Constants.VIRTUAL_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        // Table -> Allows to organize labels in a certain position inside our stage
        Table table = new Table();
        table.top(); // Puts in the top of the stage
        table.setFillParent(true); // table is the size of our stage

        timeLabel = new Label("TIME",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        worldLabel = new Label("LEVEL",new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        crystalsLabel = new Label("CRYSTALS",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        countdownLabel = new Label(String.format("%03", worldTimer),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        levelLabel = new Label(String.format("%02",level),new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        collectedLabel = new Label(String.format("%02",collectedCrystals)+" / 3",new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        // expands to the end of the screen
        // with expandX, each component has an equal width
        table.add(timeLabel).expandX().padTop(10);
        table.add(levelLabel).expandX().padTop(10);
        table.add(crystalsLabel).expandX().padTop(10);

        table.row();
        table.add(countdownLabel).expandX();
        table.add(levelLabel).expandX();
        table.add(collectedLabel).expandX();

        stage.addActor(table);
    }
}
