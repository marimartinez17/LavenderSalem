package com.lavendersalem.game.utils;

import com.badlogic.gdx.Input;

public class Constants {
    // Pantalla
    public static final int VIRTUAL_WIDTH = 720;
    public static final int VIRTUAL_HEIGHT = 360;
    public static final float PPM = 16f; // Pixels por metro
    public static final float DELTA_MAXIMO = 0.05f; // Maximo de fps
    // Teclas juego Lavender
    public static final int LAVE_UP = Input.Keys.UP;
    public static final int LAVE_LEFT = Input.Keys.LEFT;
    public static final int LAVE_RIGHT = Input.Keys.RIGHT;
    public static final int LAVE_ACTION = Input.Keys.MINUS;
    // Teclas de juego Salem
    public static final int SALEM_UP = Input.Keys.W;
    public static final int SALEM_LEFT = Input.Keys.A;
    public static final int SALEM_RIGHT = Input.Keys.D;
    public static final int SALEM_ACTION = Input.Keys.E;
    // Fisicas generales
    public static final float GRAVEDAD = -1024f;
    // Fisicas de Lavender
    public static final float LAV_VELOY = 256f;
    public static final float LAV_VELOX = 120f;
    // Fisicas de Salem
    public static final float SALEM_VELOY = 256f;
    public static final float SALEM_VELOX = 160f;
    // Sistema de Vidas
    public static final float TIEMPO_RESCATE = 5f;
    public static final float DIS_MAX_RESCATE = 64f;
}
