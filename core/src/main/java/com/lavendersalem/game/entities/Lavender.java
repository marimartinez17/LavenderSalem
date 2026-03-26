package com.lavendersalem.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.lavendersalem.game.utils.Constants;

import static com.lavendersalem.game.utils.Constants.MIN_TIEMPO_SALTO;

public class Lavender extends Player{
    private boolean esperaRescate = false;
    private float posMuerteX, posMuerteY;
    // Constructor (Crea a Lavender)
    public Lavender(float x, float y) {
        super(x,y,16f,32f, 1); // Posicion (x, y) y tamaño por PPM
        this.moviEnX = Constants.LAV_VELOX;
        this.fuerzaSalto = Constants.LAV_VELOY;
        // Spritsheets
        sheetIdle = new Texture(Gdx.files.internal("sprites/lavender/Lavender-idle-sheet.png"));
        sheetCaminarIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkleft-sheet.png"));
        sheetCaminarDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-walkright-sheet.png"));
        sheetSaltoIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpleft-sheet.png"));
        sheetSaltoDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-jumpright-sheet.png"));
        sheetInteractIzq = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactleft-sheet.png"));
        sheetInteractDer = new Texture(Gdx.files.internal("sprites/lavender/Lavender-interactright-sheet.png"));
        // Crear animaciones en 0 porque hay una sola fila y con 0.15f tiempo de frame
        animIdle = new Animation<>(0.25f, TextureRegion.split(sheetIdle, 16, 32)[0]);
        animCaminarIzq = new Animation<>(0.2f, TextureRegion.split(sheetCaminarIzq, 16, 32)[0]);
        animCaminarDer = new Animation<>(0.2f, TextureRegion.split(sheetCaminarDer, 16, 32)[0]);
        animSaltarIzq = new Animation<>(0.2f, TextureRegion.split(sheetSaltoIzq, 16, 32)[0]);
        animSaltarDer = new Animation<>(0.2f, TextureRegion.split(sheetSaltoDer, 16, 32)[0]);
        animInteractuarIzq = new Animation<>(0.2f, TextureRegion.split(sheetInteractIzq, 16, 32)[0]);
        animInteractuarDer = new Animation<>(0.2f, TextureRegion.split(sheetInteractDer, 16, 32)[0]);
        // Se config el frame inicial
        currentFrame = TextureRegion.split(sheetIdle, 16, 32)[0][0];
    }
    // Configuramos movimiento
    @Override
    protected void configMov() {
        // Si esta esperando no mueve
        if (esperaRescate) {
            velocidad.x = 0;
            return;
        }
        velocidad.x = 0; // Inicializamos la velocidad como tal en el eje X
        if (Gdx.input.isKeyPressed(Constants.LAVE_RIGHT)){
            velocidad.x += moviEnX;
            miraDer = true;
        }
        if (Gdx.input.isKeyPressed(Constants.LAVE_LEFT)){
            velocidad.x -= moviEnX;
            miraDer = false;
        }
        // Si se presiona salto, guardar en buffer
        if (Gdx.input.isKeyPressed(Constants.LAVE_UP)) {
            tiempoBufferSalto = MAX_BUFFER_SALTO;
        }
        // Saltar si hay buffer activo y está en suelo
        if (tiempoBufferSalto > 0 && onSuelo) {
            velocidad.y = fuerzaSalto * 1.05f;
            onSuelo = false;
            tiempoBufferSalto = 0f;
        }
    }

    @Override
    protected void actualizarAnimacion() {
        String nuevoEstado;

        if (esperaRescate) { // Si esta esperando rescate se queda bloqueada en un frame
            nuevoEstado = "esperaRescate";
        } else if  (!onSuelo && tiempoEnAire > MIN_TIEMPO_SALTO) {
            nuevoEstado = miraDer ? "saltoDer" : "saltoIzq";
        } else if (velocidad.x !=0) { // Si esta caminando
            nuevoEstado = miraDer ? "caminarDer" : "caminarIzq";
        } else { //Quieta
            nuevoEstado = "idle";
        }
        // Si cambió el estado, reinicia el contador
        if (!nuevoEstado.equals(estadoAnim)) {
            estadoAnim = nuevoEstado;
            timeAnimacion = 0f;
        }
        // Ahora asigna el frame
        switch (estadoAnim) {
            case "esperaRescate" -> currentFrame = animIdle.getKeyFrame(timeAnimacion, true);
            case "saltoDer"   -> currentFrame = animSaltarDer.getKeyFrame(timeAnimacion, false);
            case "saltoIzq"   -> currentFrame = animSaltarIzq.getKeyFrame(timeAnimacion, false);
            case "caminarDer" -> currentFrame = animCaminarDer.getKeyFrame(timeAnimacion, true);
            case "caminarIzq" -> currentFrame = animCaminarIzq.getKeyFrame(timeAnimacion, true);
            default -> { // Si el idle terminó su ciclo, resetea para que no acumule infinito
                if (animIdle.isAnimationFinished(timeAnimacion)) timeAnimacion = 0f;
                currentFrame = animIdle.getKeyFrame(timeAnimacion, true);
            }
        }
    }
    @Override
    public void resetear(float spawnX, float spawnY) {
        super.resetear(spawnX, spawnY);
        esperaRescate = false;
    }

    @Override
    public void morir(float spawnX, float spawnY) {
        posMuerteX = posicion.x;
        posMuerteY = posicion.y; // Para que quede pegada donde muere
        super.morir(spawnX, spawnY);
    }

    public void morirEspera (float posX, float posY) {
        // Bloquea a lavender en el sitio y activa esperaRescate
        posicion.set(posX, posY);
        velocidad.set(0,0);
        esperaRescate = true;
        vivo = true; // No muere hasta culminar contrareloj
    }

    public void recibirVida (float spawX, float spawY) {
        vidas++;
        esperaRescate = false;
        vivo = true;
        posicion.set(spawX, spawY);
        velocidad.set(0,0);
        setOnSuelo(false); // Para que mantega sobre el suelo
    }

    @Override
    public void update(float delta) {
        super.update(delta); // Aplica el sistema de salto y movimiento
    }
    // Llama al batch de la clase principal
    @Override
    public void draw(SpriteBatch batch) { super.draw(batch); }
    // HitBox para prueba y colisiones
    @Override
    public Rectangle getBounds() {
        return super.getBounds();
    }
    // Elimina basura de la grafica
    @Override
    public void dispose() {
        sheetIdle.dispose();
        sheetCaminarIzq.dispose();
        sheetCaminarDer.dispose();
        sheetSaltoIzq.dispose();
        sheetSaltoDer.dispose();
        sheetInteractIzq.dispose();
        sheetInteractDer.dispose();
    }
    // GETTERS Y SETTERS
    public boolean isEsperaRescate() { return esperaRescate; }

    public float getPosMuerteX() { return posMuerteX; }

    public float getPosMuerteY() { return posMuerteY; }
}
