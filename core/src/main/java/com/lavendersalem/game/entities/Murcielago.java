package com.lavendersalem.game.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.lavendersalem.game.utils.Constants;
import com.lavendersalem.game.utils.Enums;

public class Murcielago extends Enemy{

    private final float limiteIzq, limiteDer;
    private Enums.EstadoMurcielago estadoActual = Enums.EstadoMurcielago.PATRULLA;
    private boolean moviDer = true;
    private float tiempoPausa = 0; // Contador para pausa
    // Se le pasa a lavender y salem para las distancias
    private final Lavender lavender;
    private final Salem salem;

    public Murcielago(float x, float y, Lavender lavender, Salem salem) {
        super(x, y, 16f, 16f);
        // Calculo
        this.limiteIzq = x - (Constants.MURCI_RANGO_PATRU / 2);
        this.limiteDer = x + (Constants.MURCI_RANGO_PATRU / 2);
        this.lavender = lavender;
        this.salem = salem;
    }

    @Override
    public void update(float delta) {
        if (!activo) return;
        // Detectar si hay un objetivo cerca
        Player objetivo = objetivoCerca();

        if (objetivo != null) { // Si hay jugador cerca
            estadoActual = Enums.EstadoMurcielago.PERSIGUE;
        } else if (estadoActual == Enums.EstadoMurcielago.PERSIGUE) { // Si estaba persiguiendo y sale de rango
            estadoActual = Enums.EstadoMurcielago.REGRESA;
        }

        switch (estadoActual) {
            case PATRULLA -> patrullar(delta);
            case PERSIGUE -> perseguir(delta, objetivo);
            case PAUSA -> pausa(delta);
            case REGRESA -> regresar(delta);
        }
    }

    private Player objetivoCerca() {
        // Calcular la distancia con pitagoras
        float distLav = Vector2.dst(posicion.x, posicion.y, lavender.getPosicion().x, lavender.getPosicion().y);
        float distSal = Vector2.dst(posicion.x, posicion.y, salem.getPosicion().x, salem.getPosicion().y);

        boolean lavCerca = (distLav <= Constants.MURCI_RANGO_DETEC);
        boolean salCerca = (distSal <= Constants.MURCI_RANGO_DETEC);

        if (lavCerca && salCerca) { // Si ambos estan en el rango del murcielgo
            return (distLav < distSal) ? lavender : salem;
        } else if (lavCerca) {
            return lavender;
        } else if (salCerca) {
            return salem;
        }
        return null; // Si ninguno esta cerca
    }

    private void patrullar(float delta) {
        // Mover en la direccion correcta
        float velo = (moviDer ? Constants.MURCI_VELO : -Constants.MURCI_VELO);
        posicion.x += velo * delta;

        if (posicion.x >= limiteDer) { // Verificar si llega al limite
            posicion.x = limiteDer; // Para que no sobrepase el limite
            estadoActual = Enums.EstadoMurcielago.PAUSA;
            moviDer = false;
            tiempoPausa = Constants.MURCI_PAUSA;
        } else if (posicion.x <= limiteIzq) {
            posicion.x = limiteIzq;
            estadoActual = Enums.EstadoMurcielago.PAUSA;
            moviDer = true;
            tiempoPausa = Constants.MURCI_PAUSA;
        }
    }
    private void perseguir(float delta, Player objetivo) {
        // Diferencia de dis entre murcielago y objetivo por pitagoras
        float difX = (objetivo.getPosicion().x - posicion.x);
        float difY = (objetivo.getPosicion().y - posicion.y);

        float difDistancia = (float) Math.sqrt(difX * difX + difY * difY);

        if (difDistancia > 0) {
            posicion.x += (difX / difDistancia) * Constants.MURCI_VELO * delta;
        }
    }
    private void pausa(float delta) {
        tiempoPausa -= delta;

        if (tiempoPausa <= 0f) {
            estadoActual = Enums.EstadoMurcielago.PATRULLA;
        }
    }
    private void regresar(float delta) {
        if (posicion.x > limiteDer) {
            posicion.x -= Constants.MURCI_VELO * delta;
            if (posicion.x <= limiteDer) {
                posicion.x = limiteDer;
                estadoActual = Enums.EstadoMurcielago.PAUSA;
                moviDer = false;
                tiempoPausa = Constants.MURCI_PAUSA;
            }
        } else if (posicion.x < limiteIzq) {
            posicion.x += Constants.MURCI_VELO * delta;
            if (posicion.x >= limiteIzq) {
                posicion.x = limiteIzq;
                estadoActual = Enums.EstadoMurcielago.PAUSA;
                moviDer = true;
                tiempoPausa = Constants.MURCI_PAUSA;
            }
        } else {
            estadoActual = Enums.EstadoMurcielago.PATRULLA;
        }
    }

    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
        estadoActual = Enums.EstadoMurcielago.PATRULLA;
        moviDer = true;
        tiempoPausa = 0f;
    }

    @Override
    public void draw(SpriteBatch batch) {
        super.draw(batch);
    }
}
