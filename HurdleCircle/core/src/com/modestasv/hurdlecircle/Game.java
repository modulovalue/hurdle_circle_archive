package com.modestasv.hurdlecircle;

import com.modestasv.hurdlecircle.GameScreen.GameModel;
import com.modestasv.hurdlecircle.Interfaces.IProxable;
import com.modestasv.hurdlecircle.MainMenuScreen.MainMenuScreen;
import com.modestasv.hurdlecircle.SplashScreen.SplashScreen;

/**
 * Wird von den Jeweiligen Platformen (Android, PC, ... ) erstellt und startet damit das Spiel
 */
public class Game extends com.badlogic.gdx.Game {

    public IProxable proxable;
    public MainMenuScreen mainMenuScreen;
    public GameModel gameModel;
    public SplashScreen splashScreen;

    public Game(IProxable proxable) {
        this.proxable = proxable;
    }

    @Override
    public void create() {
        splashScreen = new SplashScreen(this);
        setScreen(splashScreen);
    }

    public void setup() {
        gameModel = new GameModel(this);
        mainMenuScreen = new MainMenuScreen(this);
    }

    public void showGame() {
        Assets.playMainMusic();
        setScreen(gameModel);
    }

    public float getProxiSensorValue() {
        if(proxable.getProx() == -1) {
            return -1;
        } else if(Assets.getProximityBool()) {
            return proxable.getProx();
        } return 0;
    }

}
