package com.lavendersalem.game.world;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapLoader;

public class Level {
    private final TiledMap mapaNivel;

    public Level(int numeroNivel) {
        String ruta = "mapas/nivel" + numeroNivel + ".tmx";
        mapaNivel = new TiledMapLoader().load(ruta);
    }
}
