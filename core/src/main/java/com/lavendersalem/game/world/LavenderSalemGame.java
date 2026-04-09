package com.lavendersalem.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.lavendersalem.game.screens.MenuPrincipal;
import com.lavendersalem.game.screens.PlayScreen;

public class LavenderSalemGame extends Game {
    public static LavenderSalemGame game; // Intanciar la clase para el cambio entre screens
    public SpriteBatch batch;
    // Tiled map attributes
    private TmxMapLoader mapLoader;
    private static TiledMap map1;

    public static AssetManager manager;
    @Override
    public void create() {
        game = this; // El coordinador de Screens (game) que es esta clase (principal)
        batch = new SpriteBatch(); // LLeva los assets "apilados" a la GPU

        manager = new AssetManager();

        mapLoader = new TmxMapLoader();
        map1 = mapLoader.load("maps/nivel1/nivel1.tmx");

        // loading music
        manager.load("music/cooties.mp3", Music.class);
        manager.load("music/powder.mp3", Music.class);

        //loading sfx sounds (.WAV files)
        manager.load("sounds/WAV/Bottle_Break.wav", Sound.class);
        manager.load("sounds/WAV/Bump.wav", Sound.class);
        manager.load("sounds/WAV/Cancel.wav", Sound.class);
        manager.load("sounds/WAV/Cat_Meow.wav", Sound.class);
        manager.load("sounds/WAV/Click.wav", Sound.class);
        manager.load("sounds/WAV/Confirm.wav", Sound.class);
        manager.load("sounds/WAV/Crunch.wav", Sound.class);
        manager.load("sounds/WAV/Digital_Alarm.wav", Sound.class);
        manager.load("sounds/WAV/Dog_Bark.wav", Sound.class);
        manager.load("sounds/WAV/Door_Slow_Open.wav", Sound.class);
        manager.load("sounds/WAV/Drink.wav", Sound.class);
        manager.load("sounds/WAV/Evil_Laugh.wav", Sound.class);
        manager.load("sounds/WAV/Explosion.wav", Sound.class);
        manager.load("sounds/WAV/Gun.wav", Sound.class);
        manager.load("sounds/WAV/Hurt.wav", Sound.class);
        manager.load("sounds/WAV/Jump.wav", Sound.class);
        manager.load("sounds/WAV/Laser_Gun.wav", Sound.class);
        manager.load("sounds/WAV/Low_Health.wav", Sound.class);
        manager.load("sounds/WAV/Menu_In.wav", Sound.class);
        manager.load("sounds/WAV/Menu_Out.wav", Sound.class);
        manager.load("sounds/WAV/Monster_Scream.wav", Sound.class);
        manager.load("sounds/WAV/Notso_Confirm.wav", Sound.class);
        manager.load("sounds/WAV/Pause.wav", Sound.class);
        manager.load("sounds/WAV/Phone_Ring.wav", Sound.class);
        manager.load("sounds/WAV/Powerdown.wav", Sound.class);
        manager.load("sounds/WAV/Powerup.wav", Sound.class);
        manager.load("sounds/WAV/Siren.wav", Sound.class);
        manager.load("sounds/WAV/Steps.wav", Sound.class);
        manager.load("sounds/WAV/Sword_Slash.wav", Sound.class);
        manager.load("sounds/WAV/Thunder.wav", Sound.class);
        manager.load("sounds/WAV/Trampoline.wav", Sound.class);
        manager.load("sounds/WAV/Water_Splash.wav", Sound.class);
        manager.finishLoading();

        setScreen(new MenuPrincipal(game));
    }

    @Override
    public void render() {
        super.render();
    }


    @Override
    public void dispose() {
        super.dispose();
        manager.dispose();
        batch.dispose();
    }

    public static TiledMap getLvl1(){
        return map1;
    }
}
