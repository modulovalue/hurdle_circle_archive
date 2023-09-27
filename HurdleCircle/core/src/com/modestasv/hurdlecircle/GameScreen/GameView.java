package com.modestasv.hurdlecircle.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis.HindernisState;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ScoreLine.Line;
import com.modestasv.hurdlecircle.GrayscaleShader;

/**
 * Alle Objekte werden hier gezeichnet + HUDView
 */

public class GameView {

    public SpriteBatch batch = new SpriteBatch();
    private ParticleEffect effect;
    private GameModel gameModel;
    public HUDView hudView;
    private boolean trueTouchingDown;

    public GameView(final GameModel gameModel)  {
        this.gameModel = gameModel;
        hudView = new HUDView(gameModel);
        effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/explosion.p"), Gdx.files.internal("effects/"));
    }

    public void reset() {
        hudView.dispose();
        hudView = new HUDView(gameModel);
    }

    public void update(boolean trueTouchingDown) {
        this.trueTouchingDown = trueTouchingDown;
    }

    public void draw(float delta) {
        batch.setProjectionMatrix(gameModel.cameraController.camera.combined);
        drawSpielfeld(batch);
        hudView.update(delta);
    }

    public void drawSpielfeld(SpriteBatch batch) {

        for(Line line : gameModel.lines) {
            line.draw(gameModel.spieler.getY(), gameModel.cameraController.camera, gameModel.leftBound, gameModel.rightBound);
        }
        gameModel.highScoreLine.draw(gameModel.spieler.getY(), gameModel.cameraController.camera, gameModel.leftBound, gameModel.rightBound);

        batch.begin();

        if(trueTouchingDown) {
            gameModel.connectionLine.startDraw(
                    gameModel.spieler.getNearestHindernis().getObj().getColor(),
                    gameModel.cameraController.camera,
                    gameModel.spieler.getNearestHindernis().getPos(),
                    gameModel.spieler.getPos(),
                    batch);
        } else {
            gameModel.connectionLine.stopDraw(batch);
        }

        gameModel.orbitCircle.draw(batch);
        gameModel.wallManager.draw(batch, gameModel.spieler.getY());
        gameModel.spieler.draw(batch);

        gameModel.hindernisController.draw(batch, gameModel.cameraController.camera);

        effect.draw(batch, Gdx.graphics.getDeltaTime());
        effect.setPosition(gameModel.spieler.getPos().x, gameModel.spieler.getPos().y);

        if(gameModel.spieler.startedHanging) {
            if(gameModel.spieler.getNearestHindernis().stateMachine.isInState(HindernisState.QUESTIONMARK)) {
                gameModel.spieler.getNearestHindernis().startScaleEffect();
            }
        }
        batch.end();
    }

    public void gameOverEffect(Vector2 pos) {
        effect.setPosition(pos.x, pos.y);
        effect.start();
    }

    public void setBW(boolean b) {
        batch.setShader( (b) ? GrayscaleShader.grayscaleShader : null);
        gameModel.connectionLine.setBlackAndWhite(b);
    }
}

