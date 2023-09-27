package com.modestasv.hurdlecircle.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;
import com.modestasv.hurdlecircle.Assets;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * ein GUI wird hier gezeichnet um z.b. das Spiel neuzustarten oder zurück ins Hauptmenü zu gelangen
 */
public class HUDView {

    private Label.LabelStyle textStyle = new Label.LabelStyle();
    private BitmapFont font;
    private Label title;
    private Container scoreContainer;

    public ObjektAbstr pauseBtn, homeBtn, restartBtn;

    public Stage stage;

    private GameModel gameModel;
    public int tempScore = 0;
    public int highestScore = 0;

    private final int btnRadius = 100;
    private final Color fromColorPauseBtn = new Color(1f, 1f, 1f, 1f);
    private final Color toColorPauseBtn = new Color(Color.YELLOW);

    public void dispose() {
        font.dispose();
        stage.dispose();
    }

    private InputListener restartButtonListenerPlaying = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Assets.clickSound.play(true);
            restartBtn.addAction(parallel(scaleTo(2f, 2f, 0.5f, swingOut), moveTo(Assets.getWidth() - 350, Assets.getHeight() - 150, 0.5f, swingOut)));
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            restartBtn.clearActions();
            restartBtn.addAction(sequence(parallel(scaleTo(1f, 1f, 0.1f, swing), moveTo(Assets.getWidth() - 300, Assets.getHeight() - 100, 0.1f, swing)), run(new Runnable() {
                @Override
                public void run() {

                    gameModel.stateMachine.changeState(MainGameState.RESET);
                    gameModel.stateMachine.update();
                }

            })));

        }
    };
    private InputListener pauseButtonListenerPlaying = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

            Assets.clickSound.play(true);
            if (!gameModel.isPaused()) {
                restartBtn.setVisible(true);
                homeBtn.setVisible(true);
                pauseBtn.getActions().clear();
                pauseBtn.addAction(parallel(rotateTo(180), rotateTo(360, 0.4f, swingOut), color(toColorPauseBtn, 0.5f, elastic), scaleTo(2f, 2f, 0.5f, elastic), moveTo(Assets.getWidth() - (btnRadius * 1.5f), Assets.getHeight() - btnRadius * 1.5f, 0.5f, swingOut)));
            }

            return true;
        }
        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            pauseBtn.addAction(sequence(run(new Runnable() {
                @Override
                public void run() {
                    if (gameModel.isPaused()) {
                        restartBtn.setVisible(false);
                        homeBtn.setVisible(false);
                        gameModel.pauseGame(false);
                        pauseBtn.clearActions();
                        pauseBtn.addAction(parallel(rotateTo(-180), rotateTo(-360, 0.4f, swingOut), color(fromColorPauseBtn, 0.2f, linear), scaleTo(1f, 1f, 0.3f, elasticOut), moveTo(Assets.getWidth() - btnRadius, Assets.getHeight() - btnRadius, 0.1f, swing)));

                    } else {
                        gameModel.pauseGame(true);
                    }
                }

            })));
        }
    };
    private InputListener homeButtonListenerPlaying = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Assets.clickSound.play(true);
            homeBtn.addAction(parallel(scaleTo(2f, 2f, 0.5f, swingOut), moveTo(Assets.getWidth() - 150, Assets.getHeight() - 350, 0.5f, swingOut)));
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {

            homeBtn.clearActions();
            homeBtn.addAction(sequence(parallel(scaleTo(1f, 1f, 0.1f, swing), moveTo(Assets.getWidth() - btnRadius, Assets.getHeight() - 300, 0.1f, swing)), run(new Runnable() {
                @Override
                public void run() {
                    gameModel.game.setScreen(gameModel.game.mainMenuScreen);
                }

            })));

        }
    };

    private InputListener restartButtonListenerGameOver = new InputListener() {
        @Override
        public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
            Assets.clickSound.play(true);
            restartBtn.addAction(parallel(scaleTo(4.1f, 4.1f, 0.28f, swingOut)));
            return true;
        }

        @Override
        public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
            restartBtn.clearActions();
            restartBtn.addAction(sequence(parallel(scaleTo(2.1f, 2.1f, 0.1f, swing)), run(new Runnable() {
                @Override
                public void run() {

                    gameModel.stateMachine.changeState(MainGameState.RESET);
                    gameModel.stateMachine.update();
                }

            })));
        }
    };

    public HUDView( GameModel gameModel) {
        stage = new Stage(new FitViewport( Assets.getWidth(), Assets.getHeight()));
        this.gameModel = gameModel;
        initScoreLabel();
        initHomeButton();
        initRestartButton();
        initPauseButton();
    }

    public void update(float delta) {

        tempScore = (int) gameModel.spieler.getY() / 300;
        if(tempScore > highestScore) {
            highestScore = tempScore;
            title.setText(String.valueOf(highestScore));
        }


        stage.act(delta);
        stage.draw();
    }

    public void initScoreLabel() {
        Texture fontTexture = new Texture(Gdx.files.internal("dotty/dotty.png"), true); // true enables mipmaps
        fontTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear); // linear filtering in nearest mipmap image

        font = new BitmapFont(Gdx.files.internal("dotty/dotty.fnt"), new TextureRegion(fontTexture), false);
        textStyle.font = font;

        title = new Label("0",textStyle);
        title.setFontScale(2);
        scoreContainer = new Container(title);
        scoreContainer.align(Align.left);
        scoreContainer.padTop(55);
        scoreContainer.padLeft(20);
        scoreContainer.setPosition(0, Assets.getHeight());
        stage.addActor(scoreContainer);
    }

    public void initHomeButton() {
        homeBtn = new ObjektAbstr(new Vector2(0,0), new Vector2(Assets.getWidth()+500, Assets.getHeight()-300), Assets.HOME_ICON, btnRadius) {
            @Override
            public void setVisible(boolean visible) {
                if(visible) {
                    super.setVisible(true);
                    addAction(sequence(moveTo(Assets.getWidth()-100, Assets.getHeight()-100), scaleTo(0,0), parallel(scaleTo(1,1,0.5f, elastic), moveTo(Assets.getWidth()-100, Assets.getHeight()-300, 0.5f, swingOut)) ));
                } else {
                    super.setVisible(true);
                    addAction(parallel(scaleTo(0, 0, 0.1f, linear), moveTo(Assets.getWidth() - 100, Assets.getHeight() - 100, 0.5f, swingOut)));
                }
            }
        };
        homeBtn.setOrigin(homeBtn.getWidth() / 2, homeBtn.getHeight() / 2);
        homeBtn.setColor(Color.GREEN);
        homeBtn.setTouchable(Touchable.enabled);
        homeBtn.addListener(homeButtonListenerPlaying);
        stage.addActor(homeBtn);
    }

    public void initRestartButton() {
        restartBtn = new ObjektAbstr(new Vector2(0,0), new Vector2(Assets.getWidth()+500, Assets.getHeight()-100), Assets.RESTART_ICON, btnRadius) {

            @Override
            public void setVisible(boolean visible) {
                if(visible) {
                    super.setVisible(true);
                    addAction(sequence(moveTo(Assets.getWidth()-100, Assets.getHeight()-100), scaleTo(0,0), parallel(scaleTo(1,1,0.5f, elastic), moveTo(Assets.getWidth()-300, Assets.getHeight()-100, 0.5f, swingOut)) ));
                } else {
                    super.setVisible(true);
                    addAction(parallel(scaleTo(0, 0, 0.1f, linear), moveTo(Assets.getWidth() - 100, Assets.getHeight() - 100, 0.5f, swingOut)));
                }
            }
        };

        restartBtn.setVisible(false);
        restartBtn.setColor(Color.ORANGE);
        restartBtn.setTouchable(Touchable.enabled);
        restartBtn.addListener(restartButtonListenerPlaying);
        stage.addActor(restartBtn);
    }

    public void initPauseButton() {
        pauseBtn = new ObjektAbstr(new Vector2(0,0), new Vector2(Assets.getWidth()- btnRadius, Assets.getHeight()- btnRadius), Assets.PAUSE_ICON, btnRadius) {};
        pauseBtn.setColor(fromColorPauseBtn);
        pauseBtn.setTouchable(Touchable.enabled);
        pauseBtn.addListener(pauseButtonListenerPlaying);
        stage.addActor(pauseBtn);
    }

    public void initGameOver() {

        pauseBtn.setTouchable(Touchable.disabled);
        pauseBtn.addAction(scaleTo(0, 0, 0.3f, exp10));

        homeBtn.setTouchable(Touchable.disabled);
        homeBtn.addAction(scaleTo(0, 0, 0.3f, exp10));

        restartBtn.addAction(moveTo(Assets.getWidth() / 2 - 50, 1000));
        restartBtn.addAction(scaleTo(0, 0));
        restartBtn.addAction(rotateTo(180));

        restartBtn.addAction(sequence(delay(0.2f), run(new Runnable() {
            @Override
            public void run() {
                restartBtn.removeListener(restartButtonListenerPlaying);
                restartBtn.addListener(restartButtonListenerGameOver);
            }
        }), parallel(alpha(0), fadeIn(1, exp10), scaleTo(2.1f, 2.1f, 1f, swing), rotateBy(-180, 1f, sine), moveTo(Assets.getWidth() / 2 - 50, Assets.getHeight() / 2, 1f, bounceOut))));

    }

}
