package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;

public class Player extends Entity{
    // Flechas o controles de movimiento (Atributos)
    private int up, down, left, right;
    // Constructor
    public Player(String ruta, float x, float y, float velocidad, int tamAncho, int tamAlto,
                  int teclaUp, int teclaDown, int teclaLeft, int teclaRight){
        super(ruta, x, y, velocidad, tamAncho, tamAlto); // Para usar constructor de Entity para cargar texture
        // Guardar las teclas de movimiento
        this.up = teclaUp;
        this.down = teclaDown;
        this.left = teclaLeft;
        this.right = teclaRight;
    }
    @Override
    public void update(float delta) { // Se ejecuta a 60fps
        if (Gdx.input.isKeyPressed(up)){ // Retorna true si la tecla es presionada
            posicion.y += velocidad * delta; // +y para subir
            // Con velocidad + delta es para que la texture se mueva 200 uni*seg sin importar la potencia de la PC
        }
        if (Gdx.input.isKeyPressed(down)){
            posicion.y -= velocidad * delta;
        }
        if (Gdx.input.isKeyPressed(left)){
            posicion.x -= velocidad * delta; // -x para izq
        }
        if (Gdx.input.isKeyPressed(right)){
            posicion.x += velocidad * delta;
        }
        // Para delimitar la ventana
        if (posicion.x < 0) posicion.x = 0; // Limite izquierdo
        if (posicion.x > 720 - tamWidth) posicion.x = 720 - tamWidth; // Limite Derecho
        if (posicion.y < 0) posicion.y = 0; // Limite inferior
        if (posicion.y > 360 - tamHeight) posicion.y = 360 - tamHeight; // Limite superior

    }
}
