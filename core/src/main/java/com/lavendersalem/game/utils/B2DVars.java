package com.lavendersalem.game.utils;

public class B2DVars {
    // background layer
    public static final short BG1 = 0;
    public static final short BG2 = 1;

    // ground/graphic layers
    public static final short INTERACTIVE_TILES = 2;
    public static final short MIDGROUND = 3;
    public static final short GROUND = 4;
    public static final short DETAILS = 5;

    // collision object layers
    public static final short PLATFORMS = 6;
    public static final short ONE_WAY = 7;
    public static final short SLOPES = 8;

    // object layers
    public static final short OBJECTS_INTERACTIVE = 9;
    public static final short OBJECTS_DANGER = 10;
    public static final short OBJECTS_TRIGGERS = 11;
    public static final short OBJECTS_SPAWNS = 12;
    public static final short OBJECTS_ENEMIES = 13;
    public static final short OBJECTS_PORTALS = 14;
    public static final short OBJECTS_CRYSTALS= 15;

    //player/sprite constants for box2D
    public static final short BIT_PLAYER = 16;
    public static final float PPM = 100f;
}
