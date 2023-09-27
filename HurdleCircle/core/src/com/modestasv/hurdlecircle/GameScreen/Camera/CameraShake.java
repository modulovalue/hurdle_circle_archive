package com.modestasv.hurdlecircle.GameScreen.Camera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.math.MathUtils;

/**
 * sorgt für ein "vibrieren" des Spiels um ein explosions/aufprall Effect zu simulieren
 *
 * ///Aus dem Internet///
 */
public class CameraShake {

    private float SHAKE_DECAY;
    private int SHAKE_DELAY;
    private int SHAKE_INTENSITY;
    private int shakeTime;
    private float shakeAmt;
    private float shakeX;
    private float shakeY;

    public CameraShake() {
        reset();
    }

    public void reset() {
        SHAKE_DECAY = 0.03f;
        SHAKE_DELAY = 3;
        SHAKE_INTENSITY = 40;
        shakeTime = SHAKE_DELAY;
        shakeAmt = 13;
        shakeX = 0f;
        shakeY = 0f;
    }

    public void update(OrthographicCamera camera) {
        if (shakeAmt>0f) {
            shakeTime -= Gdx.graphics.getDeltaTime();
            if (shakeTime <= 0) {
                shakeX = (MathUtils.random(-shakeAmt, shakeAmt));
                shakeY = (MathUtils.random(-shakeAmt, shakeAmt));
                shakeTime = SHAKE_DELAY;
                shakeAmt -= SHAKE_DECAY * SHAKE_INTENSITY;
                if (shakeAmt<0f) {
                    shakeAmt = 0f;
                }
                camera.translate(shakeX,shakeY);
                camera.update();
            }
        }
    }
}
