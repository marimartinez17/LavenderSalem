package com.lavendersalem.game.utils;

import com.badlogic.gdx.Input;

public class Constants {
    // Pantalla
    public static final int VIRTUAL_WIDTH = 720;
    public static final int VIRTUAL_HEIGHT = 360;
    public static final float PPM = 16f; // Pixels por metro
    // Teclas juego Lavender
    public static final int LAVE_UP = Input.Keys.UP;
    public static final int LAVE_LEFT = Input.Keys.LEFT;
    public static final int LAVE_RIGHT = Input.Keys.RIGHT;
    public static final int LAVE_ACTION = Input.Keys.MINUS;
    // Teclas de juego Salem
    public static final int SALEM_UP = Input.Keys.W;
    public static final int SALEM_LEFT = Input.Keys.A;
    public static final int SALEM_RIGHT = Input.Keys.D;
    // Fisicas generales
    public static final float GRAVEDAD = -15f;
    // Fisicas de Lavender
    public static final float LAV_VELOY = 31f;
    public static final float LAV_VELOX = 60f;
    // Fisicas de Salem
    public static final float SALEM_VELOY = 10.25f;
    public static final float SALEM_VELOX = 7f;
}
