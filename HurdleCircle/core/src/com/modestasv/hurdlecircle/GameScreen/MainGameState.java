package com.modestasv.hurdlecircle.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.modestasv.hurdlecircle.GameScreen.Camera.CameraState;
import com.modestasv.hurdlecircle.Assets;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Spieler.SpielerState;

/**
 *  PLAYING:
 *      Alles wird Aktualisiert
 *  PAUSE:
 *      nur die Kamera wird Aktualisiert
 *  RESET:
 *      das Spiel wird zurückgesetzt
 *  GAMEOVER:
 *      der Spieler hat Verloren.
 *      Objekte werden informiert.
 */

public enum MainGameState implements State<GameModel> {

    PLAYING {
        @Override
        public void update(GameModel en) {
            en.gameController.update(Gdx.graphics.getDeltaTime(), false);

            en.gameController.updateInPause(Gdx.graphics.getDeltaTime(), false);
        }
    },

    PAUSE {
        @Override
        public void update(GameModel en) {
            en.gameController.updateInPause(Gdx.graphics.getDeltaTime(), false);
        }
    },

    RESET {
        @Override
        public void update(GameModel en) {
            en.resetMainGameScreen();

        }
    },

    GAMEOVER() {
        @Override
        public void enter(GameModel en) {
            if(Assets.getVibrate()) { Gdx.input.vibrate(15); }

            if(en.gameView.hudView.highestScore > Assets.getHighscore()) {
                Assets.putHighscore(en.gameView.hudView.highestScore);
            }
            en.gameView.hudView.initGameOver();
            en.cameraController.stateMachine.changeState(CameraState.GAMEOVER);
        }

        @Override
        public void update(GameModel en) {
            en.spieler.stateMachine.changeState(SpielerState.GAMEOVER);
            en.gameController.update(Gdx.graphics.getDeltaTime(), true);

            en.gameController.updateInPause(Gdx.graphics.getDeltaTime(), true);
        }
    };

    @Override
    public void enter(GameModel en) {
    }
    @Override
    public void update(GameModel entity) {

    }
    @Override
    public void exit(GameModel entity) {
    }
    @Override
    public boolean onMessage(GameModel entity, Telegram telegram) {
        return false;
    }
}
