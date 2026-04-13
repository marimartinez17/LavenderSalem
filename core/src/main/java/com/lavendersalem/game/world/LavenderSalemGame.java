package com.lavendersalem.game.world;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.lavendersalem.game.screens.MenuPrincipal;
import com.lavendersalem.game.screens.PlayScreen;

public class LavenderSalemGame extends Game {
    public static LavenderSalemGame game; // Intanciar la clase para el cambio entre screens
    public SpriteBatch batch;
    // Tiled map attributes
    private TmxMapLoader mapLoader;
    private static TiledMap map1;
    private static TiledMap map2;
    private static TiledMap map2v2;
    private static TiledMap map3;

    public static AssetManager manager;
    @Override
    public void create() {
        game = this; // El coordinador de Screens (game) que es esta clase (principal)
        batch = new SpriteBatch(); // LLeva los assets "apilados" a la GPU

        manager = new AssetManager();

        mapLoader = new TmxMapLoader();
        map1 = mapLoader.load("maps/nivel1/nivel1.tmx");
        map2 = mapLoader.load("maps/nivel1/nivel2.tmx");
        map2v2 = mapLoader.load("maps/nivel1/nivel2v2.tmx");
        map3 = mapLoader.load("maps/nivel1/nivel3.tmx");

        // loading music
        manager.load("music/cooties.mp3", Music.class);
        manager.load("music/powder.mp3", Music.class);
        manager.load("music/numbers.mp3", Music.class);
        manager.load("music/garbage.mp3", Music.class);

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

        manager.get("music/cooties.mp3", Music.class).play();
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

    public static TiledMap getMap(int lvl){
        TiledMap map = map1;
        switch (lvl){
            case 0:
                map = map1;
                break;
            case 1:
                map = map2;
                break;
            case 2:
                map =  map3;
                break;
            default:
                map = map1;
                break;
        }
        return map;
    }

    public static Music getMusic(int lvl){
        Music theme;
        switch (lvl){
            case 0:
                theme = manager.get("music/cooties.mp3",Music.class);
                break;
            case 1:
                theme = manager.get("music/powder.mp3",Music.class);
                break;
            case 2:
                theme = manager.get("music/numbers.mp3",Music.class);
                break;
            default:
                theme = LavenderSalemGame.manager.get("music/cooties.mp3",Music.class);
                break;
        }
        return theme;
    }

    public static TiledMap getLvl1(){
        return map1;
    }
    public static TiledMap getLvl2(){
        return map2;
    }
    public static TiledMap getLvlV2(){return map2v2;}
    public static TiledMap getLvl3(){return map3;}
}
