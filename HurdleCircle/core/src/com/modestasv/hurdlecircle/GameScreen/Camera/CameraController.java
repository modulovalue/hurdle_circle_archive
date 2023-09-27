package com.modestasv.hurdlecircle.GameScreen.Camera;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.modestasv.hurdlecircle.MVInterpolate;
import com.modestasv.hurdlecircle.Assets;


/**
 * Verwaltet die Camera ihre die verschiedenen Effekte die im laufe des Spiels auftreten können.
 */
public class CameraController {


    public StateMachine<CameraController> stateMachine = new DefaultStateMachine<CameraController>(this, CameraState.INITIALIZE);
    public OrthographicCamera camera;

    public MVInterpolate zoomInterpolationZoomOut = new MVInterpolate(new Interpolation.Exp(4,3), 2f, 1f, 1.7f);
    public MVInterpolate zoomInterpolationZoomNormal = new MVInterpolate(new Interpolation.Exp(4,3), 1f,  1.7f, 1f);
    public MVInterpolate zoomInterpolationInit = new MVInterpolate(new Interpolation.Exp(4,3), 0.7f, 4f, 1f);
    public MVInterpolate rotateInterpolationInit = new MVInterpolate(new Interpolation.SwingOut(MathUtils.random(1, 3)), 0.7f, MathUtils.random(-60, 60), 0f);

    private CameraShake cameraShake = new CameraShake();

    public CameraController() {
        camera = new OrthographicCamera(Assets.getWidth(), Assets.getHeight());
        zoomInterpolationZoomNormal.setDone();
    }

    public void reset() {
        rotateInterpolationInit.reset();
        zoomInterpolationInit.reset();
        cameraShake = new CameraShake();
        stateMachine.changeState(CameraState.INITIALIZE);
    }

    public void update(float delta, float spielerx, float spielery, boolean gameOver) {

        if(!gameOver) {
            camera.position.x = spielerx / 1.1f;
            camera.position.y = 200 + spielery;
            camera.update();
        }

        stateMachine.update();
    }

    public void resize(float width, float height) {
        camera.setToOrtho(false, Assets.getWidth(), Assets.getHeight());
    }

    public void shakeCamera() {
        cameraShake.update(camera);
    }

    public void zoomOut() {
        stateMachine.changeState(CameraState.ZOOM_OUT);
    }

    public void zoomNormal() {
        if(stateMachine.isInState(CameraState.ZOOM_OUT)) {
            stateMachine.changeState(CameraState.ZOOM_NORMAL);
        }
    }
}

