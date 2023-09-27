package com.modestasv.hurdlecircle.GameScreen.Camera;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

/**
 * Verwaltet die verschiedenen Zustände der Kamera
 *
 * INITIALIZE:
 *      sorgt für einen effekt beim Starten des Spiels
 * ZOOM_OUT:
 *      die kamera wird rausgezoomt
 * ZOOM_NORMAL:
 *      der zoom der kamera wird auf den ausgangswert zurückgesetzt
 * GAMEOVER:
 *      die Kamera Vibriert um eine Expolosion zu simulieren
 *
 */
public enum CameraState implements State<CameraController> {

    INITIALIZE() {
        @Override
        public void update(CameraController en) {
            en.camera.zoom = en.zoomInterpolationInit.update().getValue();
            en.camera.up.set(0, 1, 0);
            en.camera.direction.set(0, 0, 1);
            en.camera.rotate(en.rotateInterpolationInit.update().getValue());
        }
    },

    ZOOM_OUT() {
        @Override
        public void enter(CameraController en) {
            if(en.zoomInterpolationZoomNormal.getValue() != 1) {
                en.zoomInterpolationZoomOut.setValue(en.zoomInterpolationZoomNormal.getValue());
            }
        }

        @Override
        public void update(CameraController en) {
            en.camera.zoom = en.zoomInterpolationZoomOut.update().getValue();
            en.camera.up.set(0, 1, 0);
            en.camera.direction.set(0, 0, 1);
        }
    },

    ZOOM_NORMAL() {

        @Override
        public void enter(CameraController en) {
            en.zoomInterpolationZoomNormal.reset();
            en.zoomInterpolationZoomOut.reset();
        }

        @Override
        public void update(CameraController en) {
            en.camera.zoom = en.zoomInterpolationZoomNormal.update().getValue();
            en.camera.up.set(0, 1, 0);
            en.camera.direction.set(0, 0, 1);
        }

    },

    GAMEOVER() {


        @Override
        public void enter(CameraController entity) {
            super.enter(entity);

        }

        @Override
        public void update(CameraController en) {
          en.shakeCamera();
        }
    };

    @Override
    public void enter(CameraController entity) {
    }

    @Override
    public void update(CameraController entity) {
    }

    @Override
    public void exit(CameraController entity) {
    }

    @Override
    public boolean onMessage(CameraController entity, Telegram telegram) {
        return false;
    }

}
