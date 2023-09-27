package com.modestasv.hurdlecircle.SplashScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.modestasv.hurdlecircle.Game;
import com.modestasv.hurdlecircle.Assets;

import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

/**
 * Ein SplashScreen.
 * Lädt alle Ressourcen und wechselt ins Hauptmenü bei erfolgreichem Laden
 */
public class SplashScreen implements Screen {
    private Game game;
    private Image splashImage;
    private Texture texture;
    private Stage stage = new Stage();

    public SplashScreen(final Game game) {
        this.game = game;
        Assets.get().clear();
        Assets.get().initAssets();
        texture = new Texture(Gdx.files.internal("data/icon/play.png"), true);
        texture.setFilter(Texture.TextureFilter.MipMapLinearNearest, Texture.TextureFilter.Linear);

        splashImage = new Image(texture);
        splashImage.setSize( Gdx.graphics.getHeight() * (Gdx.graphics.getHeight() / Gdx.graphics.getWidth()) /4, Gdx.graphics.getHeight()/4);
        splashImage.setPosition(
                Gdx.graphics.getWidth()/2,
                Gdx.graphics.getHeight()/2,
                Align.center);
        splashImage.setOrigin(Align.center);
        splashImage.addAction(sequence(alpha(0), scaleTo(0, 0), parallel(scaleTo(1,1,0.5f, new Elastic(4, 3, 2, 1.5f)), fadeIn(0.4f))));
    }

    @Override
    public void show() {
        stage.addActor(splashImage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
        if(Assets.get().update()) {
            if(splashImage.getActions().size == 0) {
                game.setup();
                dispose();
                game.setScreen(game.mainMenuScreen);
            }
        }
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        texture.dispose();
    }

}
