package com.lavendersalem.game.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.lavendersalem.game.entities.Lavender2;
import com.lavendersalem.game.entities.Salem2;
import com.lavendersalem.game.utils.Constants;

public class SistemaVidas {
    private final Lavender2 lavender;
    private final Salem2 salem;

    private boolean esperaRescate = false;
    private float tiempoEsperaRes = 0f;
    private float lavenderMuertaX, lavenderMuertaY;
    private boolean lavVivaFrameAnt = true;

    public SistemaVidas(Lavender2 lavender, Salem2 salem) {
        this.lavender = lavender;
        this.salem = salem;
    }

    public void cederVidas(float delta, float spawnLavX, float spawnLavY) {
        if (esperaRescate) { // Si esta esperando rescate
            tiempoEsperaRes -= delta; // para resta del contrareloj
            // Calculo de distancia por pit
            float distancia = Vector2.dst(lavender.getPosicion().x, lavender.getPosicion().y,
                salem.getPosicion().x, salem.getPosicion().y);
            // Validar en true si pasa
            boolean salemCerca = (distancia <= Constants.DIS_MAX_RESCATE);
            boolean salemPresionaE = (Gdx.input.isKeyJustPressed(Constants.SALEM_ACTION));

            if (salemCerca && salemPresionaE && (salem.getVidas() > 1)) {
                salem.setVidas(salem.getVidas() - 1);
                lavender.recibirVida(spawnLavX, spawnLavY);
                esperaRescate = false; // Para salir de la condicion
            } else if (tiempoEsperaRes <= 0){
                esperaRescate = false; // Si acaba el cronometro sale de la oportunidad
                lavender.setVivo(false);
            }
        } else { // Si esta normal
            // Detectar si Lavender fue tocada por enemigo
            if (lavender.isTocadoEnemigo()) {
                lavender.resetTocadoPorEnemigo();
                lavender.morir(spawnLavX, spawnLavY);
            }
            if (lavVivaFrameAnt && !lavender.isVivo()) { // Para saber en que punto exacto muere
                if (salem.getVidas() > 1) {
                    lavenderMuertaX = lavender.getPosMuerteX();
                    lavenderMuertaY = lavender.getPosMuerteY();
                    // Se activa el rescate
                    esperaRescate = true;
                    tiempoEsperaRes = Constants.TIEMPO_RESCATE;
                    lavender.morirEspera(lavenderMuertaX, lavenderMuertaY);
                }
                // Si salem no tiene vidas extra pasa a Game over
            }
        }
        lavVivaFrameAnt = lavender.isVivo() && !lavender.isEsperaRescate(); // Reinicia valores
    }

    public boolean isGameOver() {
        return (!lavender.isVivo() || !salem.isVivo())
            && (!esperaRescate || salem.getVidas() <= 1);
    }

    public void resetear() {
        esperaRescate = false;
        tiempoEsperaRes = 0f;
        lavenderMuertaX = 0f; lavenderMuertaY = 0f;
        lavVivaFrameAnt = true;
    }
    // GETTER PARA EL GAME
    public boolean isEsperaRescate() { return esperaRescate; }

    public float getTiempoEsperaRes() { return tiempoEsperaRes; }
    public float getTiempoMaximo() { return Constants.TIEMPO_RESCATE; }
    public float getLavenderMuertaX() { return lavenderMuertaX; }
    public float getLavenderMuertaY() { return lavenderMuertaY; }
}
