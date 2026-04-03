package com.lavendersalem.game.utils;

public class Enums {
    public enum AccionPausa{
        NINGUNA, // No hace nada
        CONTINUAR,
        RESET,
        MENU_PPAL
    }

    public enum EstadoMurcielago{
        PATRULLA,
        PERSIGUE,
        PAUSA,
        REGRESA
    }

    public enum State {
        JUMPING,
        STANDING,
        RUNNING,
        FALLING,
    }

}
