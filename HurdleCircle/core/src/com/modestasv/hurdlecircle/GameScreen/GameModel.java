package com.modestasv.hurdlecircle.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.Game;
import com.modestasv.hurdlecircle.GameScreen.Camera.CameraController;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ConnectionLine.ConnectionLine;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis.HindernisController;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.OrbitCircle.OrbitCircle;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ScoreLine.Line;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Spieler.Spieler;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Wall.Wall;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Wall.WallManager;
import com.modestasv.hurdlecircle.Assets;

import java.util.ArrayList;

/**
 * hält Referenzen zu allen Spielobjekten und sorgt für die verschiedenen Zustände des Spiels
 */
public class GameModel implements Screen {

    public final static int LEFTWALL = -285;
    public final static int RIGHTWALL = 285;
    public Game game;
    public GameController gameController;
    public GameView gameView;
    public Spieler spieler;
    public HindernisController hindernisController;
    public CameraController cameraController;
    public ConnectionLine connectionLine;
    public WallManager wallManager;
    public ArrayList<Line> lines = new ArrayList<Line>();
    public Line highScoreLine;

    public OrbitCircle orbitCircle;

    public float leftBound;
    public float rightBound;

    public StateMachine<GameModel> stateMachine = new DefaultStateMachine<GameModel>(this, MainGameState.PLAYING);

    public GameModel(Game game) {
        this.game = game;

        highScoreLine = new Line(20, Assets.getHighscore(), Color.YELLOW);
        lines.add(new Line(5, 25, Color.GREEN));
        lines.add(new Line(5, 50, Color.GREEN));
        lines.add(new Line(5, 100, Color.GREEN));
        lines.add(new Line(5, 200, Color.GREEN));
        lines.add(new Line(5, 500, Color.GREEN));
        lines.add(new Line(5, 1000, Color.GREEN));
        lines.add(new Line(5, 5000, Color.GREEN));
        lines.add(new Line(5, 10000, Color.GREEN));

        this.gameView = new GameView(this);
        this.connectionLine = new ConnectionLine();
        this.cameraController = new CameraController();
        this.orbitCircle = new OrbitCircle(40);
        this.hindernisController = new HindernisController();

        resetMainGameScreen();

        this.leftBound = LEFTWALL + spieler.getObj().getWidth()/2;
        this.rightBound = RIGHTWALL - spieler.getObj().getWidth()/2;
        this.wallManager = new WallManager(new Wall(new Vector2(leftBound,0), 50), new Wall(new Vector2(rightBound,0), 50));
        this.gameController = new GameController(this);
    }

    public void resetMainGameScreen() {

        this.spieler = new Spieler(new Vector2(0,5), new Vector2(0, -1700f), Assets.SPIELER_NORMAL, 50);
        this.hindernisController.reset();
        for (Line value : lines) {
            value.turnOn();
        }
        highScoreLine.setPoints(Assets.getHighscore());
        highScoreLine.turnOn();
        cameraController = new CameraController();
        gameView.reset();
        stateMachine.changeState(MainGameState.PLAYING);
        System.gc();
        pauseGame(false);
    }

    public void pauseGame(boolean pause) {
        if(!pause ) {
            show();
            gameView.setBW(false);
            Assets.setMainMusicVolume(Assets.NORMAL);
            stateMachine.changeState(MainGameState.PLAYING);
        } else {
            gameView.setBW(true);
            Assets.setMainMusicVolume(Assets.LEISE);
            stateMachine.changeState(MainGameState.PAUSE);
        }
    }

    public boolean isPaused() {
        return stateMachine.isInState(MainGameState.PAUSE);
    }

    public void gameOver() {
        if(!stateMachine.isInState(MainGameState.GAMEOVER)) {
            gameView.gameOverEffect(spieler.getPos());
            Assets.setMainMusicVolume(Assets.LEISE);
            stateMachine.changeState(MainGameState.GAMEOVER);
        }
    }

    public void show() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(gameView.hudView.stage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameView.draw(delta);
        stateMachine.update();
    }

    @Override
    public void dispose() {
    }

    @Override
    public void resize(int width, int height) {
        cameraController.resize(width,height);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }
}

