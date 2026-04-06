package com.lavendersalem.game.utils;

public class B2DVars {

    // Tiled layer index
    public static final int BG1 = 0;
    public static final int BG2 = 1;
    public static final int INTERACTIVE_TILES = 2;
    public static final int MIDGROUND = 3;
    public static final int GROUND = 4;
    public static final int DETAILS = 5;
    public static final int OBJECTS_INTERACTIVE = 9;
    public static final int OBJECTS_DANGER = 10;
    public static final int OBJECTS_TRIGGERS= 11;
    public static final int OBJECTS_SPAWNS = 12;
    public static final int OBJECTS_ENEMIES= 14;
    public static final int OBJECTS_PORTALS= 15;
    public static final int OBJECTS_PRIZE = 17;

    // Box2D category and mask bits for collisions
    public static final short BIT_LAVENDER = 1;
    public static final short BIT_PLAYER= 1;
    public static final short BIT_SALEM= 2;
    public static final short PLATFORMS = 4;
    public static final short ONE_WAY = 8;
    public static final short SLOPES = 16;
    public static final short BIT_ENEMY = 32;
    public static final short OBJECTS_OBSTACLES= 64;
    public static final short OBJECTS_CRYSTALS = 128;

    // Misc
    public static final float PPM = 100f;
    public static final float CAM_LERP = 0.1f;
}
