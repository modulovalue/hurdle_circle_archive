package com.modestasv.hurdlecircle.MainMenuScreen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.modestasv.hurdlecircle.Game;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;
import com.modestasv.hurdlecircle.Assets;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.math.Interpolation.*;

/**
 * das Hauptmenü
 */

public class MainMenuScreen implements Screen {

    private Game game;
    private Stage stage;

    private ObjektAbstr settingsBtn, playButton, backButton;

    private Table titleTable = new Table();
    private Table settingsTable = new Table();
    private Label.LabelStyle textStyle = new Label.LabelStyle();
    private Label highScore;

    private Button proxSensorBtn, vibrBtn, soundBtn;
    private Label proxLbl, vibrLbl, soundLbl;


    public MainMenuScreen(final Game game) {
        this.game = game;
        stage = new Stage(new FitViewport(Assets.getWidth(), Assets.getHeight()));
        Texture fontTexture = new Texture(Gdx.files.internal("dotty/dotty.png"), true);
        fontTexture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);
        BitmapFont font = new BitmapFont(Gdx.files.internal("dotty/dotty.fnt"), new TextureRegion(fontTexture), false);
        textStyle.font = font;
        setupSettingsButton();
        setupPlayButton();
        setupBackButton();
        setupTitleTable();
        setupSettingsTable();
        showSettings(false);
        Gdx.input.setInputProcessor(stage);
    }

    private void setupSettingsTable() {
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));

        settingsTable.padTop(Assets.getHeight()*0.2f);
        settingsTable.align(Align.top);
        settingsTable.setFillParent(true);
        settingsTable.setVisible(false);

        proxSensorBtn = new Button(skin);
        proxSensorBtn.padLeft(20).padRight(20);
        proxLbl = new Label("",textStyle);
        proxLbl.setFontScale(0.5f);
        changeProximityBtnText(proxLbl, proxSensorBtn, Assets.getProximityBool());
        proxSensorBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.clickSound.play(true);
                changeProximityBtnText(proxLbl, proxSensorBtn, !Assets.getProximityBool());
            }
        });
        proxSensorBtn.add(proxLbl);
        settingsTable.add(proxSensorBtn).padBottom(50).row();

        vibrBtn = new Button(skin);
        vibrBtn.padLeft(20).padRight(20);
        vibrLbl = new Label("",textStyle);
        vibrLbl.setFontScale(0.5f);
        changeVibrBtnText(vibrLbl, vibrBtn, Assets.getVibrate());
        vibrBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                Assets.clickSound.play(true);
                changeVibrBtnText(vibrLbl, vibrBtn, !Assets.getVibrate());
            }
        });
        vibrBtn.add(vibrLbl);
        settingsTable.add(vibrBtn).padBottom(50).row();

        soundBtn = new Button(skin);
        soundBtn.padLeft(20).padRight(20);
        soundLbl = new Label("",textStyle);
        soundLbl.setFontScale(0.5f);
        changeSoundBtnText(soundLbl, soundBtn, Assets.getSoundAllow());
        soundBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y){
                changeSoundBtnText(soundLbl, soundBtn, !Assets.getSoundAllow());
            }
        });
        soundBtn.add(soundLbl);
        settingsTable.add(soundBtn).padBottom(50).row();
        highScore = new Label("",textStyle);
        highScore.setFontScale(0.6f);

        Container scoreContainer = new Container(highScore);
        settingsTable.add(scoreContainer).padBottom(0).size(Assets.getWidth() * 0.8f, Assets.getHeight() * 0.1f).row();
        stage.addActor(settingsTable);
    }

    private void changeProximityBtnText(Label label, Button button, boolean bool) {
        if(game.getProxiSensorValue() == -1f) {
            button.setColor(Color.RED);
            label.setText("Proximity Sensor: N/A");
            Assets.putProxBool(false);
        } else {
            Assets.putProxBool(bool);
            if(Assets.getProximityBool()) {
                label.setText("Proximity Sensor: On");
            } else {
                label.setText("Proximity Sensor: Off");
            }
        }
    }

    private void changeVibrBtnText(Label label, Button button, boolean bool) {
        if(!Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator)) {
            button.setColor(Color.RED);
            label.setText("Vibration: N/A");
            Assets.putVibrate(false);
        } else {
            Assets.putVibrate(bool);
            if(Assets.getVibrate()) {
                label.setText("Vibration: On");
            } else {
                label.setText("Vibration: Off");
            }
        }
    }

    private void changeSoundBtnText(Label label, Button button, boolean bool) {
        if(bool) {
            Assets.putSoundAllow(bool);
            label.setText("Sound: On");
        } else {
            Assets.putSoundAllow(bool);
            label.setText("Sound: Off");
        }
    }

    private void setupTitleTable() {
        Label title = new Label(Assets.TITLE_NAME,textStyle);
        title.setAlignment(Align.center);
        Container titleContainer = new Container(title);
        title.setAlignment(Align.center);
        titleTable.align(Align.top);
        titleTable.add(titleContainer).padBottom(10).size(Assets.getWidth() * 0.8f, Assets.getHeight() * 0.1f).row();
        titleTable.setFillParent(true);
        stage.addActor(titleTable);
    }

    private void setupPlayButton() {
        playButton = new ObjektAbstr(new Vector2(0,0), new Vector2(Assets.getWidth()-80,-60), Assets.PLAY_ICON, 140 ) {};
        playButton.setColor(Color.YELLOW);
        playButton.setTouchable(Touchable.enabled);
        playButton.addListener(new InputListener() {

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.clickSound.play(true);
                playButton.getActions().clear();
                playButton.addAction(parallel(scaleTo(2f, 2f, 0.5f, swingOut), moveTo(Assets.getWidth() - 200, 60, 0.5f, swingOut)));
                return true;
            }
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                playButton.getActions().clear();
                playButton.addAction(sequence(parallel(scaleTo(1f, 1f, 0.2f, swing), moveTo(Assets.getWidth() - 80, -60, 0.2f, swing)), run(new Runnable() {
                    @Override
                    public void run() {
                        game.showGame();
                        showSettings(false);
                        game.mainMenuScreen.dispose();
                    }

                })));
            }
        });
        stage.addActor(playButton);
    }

    public void setupSettingsButton() {
        settingsBtn = new ObjektAbstr(new Vector2(0,0), new Vector2(-60,-60), Assets.SETTINGS_ICON, 140 ) {};
        settingsBtn.setColor(Color.GREEN);
        settingsBtn.addAction(forever(rotateBy(2)));
        settingsBtn.setTouchable(Touchable.enabled);
        settingsBtn.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Assets.clickSound.play(true);
                settingsBtn.addAction(parallel(scaleTo(2f, 2f, 0.5f, swingOut), moveTo(60, 60, 0.5f, swingOut)));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                settingsBtn.getActions().clear();
                settingsBtn.addAction(parallel(scaleTo(1f, 1f, 0.2f, swing), moveTo(-60, -60, 0.2f, swing), run(new Runnable() {
                    @Override
                    public void run() {
                        showSettings(true);
                    }

                })));
                settingsBtn.addAction(forever(rotateBy(2)));
            }
        });
        stage.addActor(settingsBtn);

    }

    public void showTitleTable(boolean show) {
        if(!show) {
            titleTable.addAction(moveTo(0, Assets.getHeight() * -0.05f, 0.4f, Interpolation.swingOut));
        } else {
            titleTable.addAction(moveTo(0, Assets.getHeight() * -0.4f, 0.5f, Interpolation.swingOut));
        }
    }

    public void showSettingsTable(boolean show) {
        if(show) {
            settingsTable.clearActions();
            settingsTable.setVisible(true);
            settingsTable.addAction(parallel(Actions.alpha(0), fadeIn(1)));
            settingsTable.setPosition(0, 0);
            backButton.setVisible(true);
        } else {
            settingsTable.clearActions();
            settingsTable.addAction(parallel(Actions.alpha(1), sequence( parallel( fadeOut(0.3f), moveTo(0, -Assets.getHeight()*2, 0.5f)), run(new Runnable() {
                @Override
                public void run() {
                    settingsTable.setVisible(false);
                }
            }))));
            backButton.setVisible(false);
        }
    }

    private void setupBackButton() {
        backButton = new ObjektAbstr(new Vector2(0,0), new Vector2(Assets.getWidth()/2-70, -200), Assets.BACK_ICON, 140 ) {
            public Vector2 origPos = new Vector2(getX(),getY());
            @Override
            public void setVisible(boolean visible) {
                if(visible) {
                    addAction(moveTo(origPos.x, Assets.getHeight() * -0.01f, 0.5f, swingOut));
                } else {
                    addAction(moveTo(origPos.x, origPos.y, 0.5f, swingIn));
                }
            }
        };

        backButton.setColor(Color.LIGHT_GRAY);
        backButton.setTouchable(Touchable.enabled);
        backButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {

                Assets.clickSound.play(true);
                backButton.getActions().clear();
                backButton.addAction(parallel(scaleTo(1.5f, 1.5f, 0.5f, swingOut)));
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backButton.getActions().clear();
                backButton.addAction(sequence(parallel(scaleTo(1f, 1f, 0.2f, swing), run(new Runnable() {
                    @Override
                    public void run() {
                        backButton.setVisible(false);
                        showSettings(false);
                    }
                }))));
            }
        });
        stage.addActor(backButton);
    }

    public void showSettings(boolean settings) {
        if(settings) {
            showTitleTable(false);
            showSettingsTable(true);
        } else {
            showTitleTable(true);
            showSettingsTable(false);
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor( 80/255f, 80/255f, 80/255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void show() {
        Assets.pauseMainMusic();
        setHighscore();
        Gdx.input.setInputProcessor(stage);
    }

    private void setHighscore() {
        highScore.setText("Highscore: " + String.valueOf(Assets.getHighscore()));
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
    }
}


