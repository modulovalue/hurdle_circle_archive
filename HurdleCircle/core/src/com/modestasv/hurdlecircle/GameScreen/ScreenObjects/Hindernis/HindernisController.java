package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.GameModel;
import com.modestasv.hurdlecircle.Assets;

import java.util.ArrayList;

/**
 * Kümmert sich um die Hindernisse.
 * Neue Hindernisse werden erstellt wenn der spieler eine bestimmte Entfernung vom ersten Hindernis,
 * angenommen hat.
 *
 * die Technik "Pooling" wurde hier umgesetzt um ein unnötiges löschen von Hindernis Objekten zu vermeiden und damit
 * unnötigen Garbage Collection Aufrufen zu entgehen.
 */

public class HindernisController {

    public ArrayList<Hindernis> hindernisse = new ArrayList<Hindernis>();

    private RandomHindernisse randomHindernisse;
    private float curScore = 0;

    /* Start Y-Position der Hindernisses*/
    private int currentSpawnY;

    public HindernisController() {
        randomHindernisse = new RandomHindernisse();
        initHindernisse();
        currentSpawnY = 300;
    }

    public void reset() {
        currentSpawnY = 300;
        randomHindernisse = new RandomHindernisse();
        for (Hindernis hin: hindernisse) {
            hin.dispose();
        }
        curScore = 0;
        hindernisse.clear();
        initHindernisse();
    }

    public void draw(SpriteBatch batch, OrthographicCamera camera) {
        for (Hindernis hin: hindernisse) {
            if (camera.frustum.sphereInFrustum(hin.getPos().x, hin.getPos().y, 0, hin.getGroe())) {
                hin.draw(batch);
            }
                hin.drawEffect(batch);
        }
    }

    public void update(float delta, float spielerY, int curScore) {
        this.curScore = curScore;
        if(spielerY - hindernisse.get(0).getY() > 2500) {
            addNeuesHindernisUndRemoveAltes(hindernisse.get(0));
        }
        for (Hindernis hin : hindernisse) {
            hin.update(delta);
        }
    }

    public void initHindernisse() {
        for(int i = 0; i <= 20; i++) {
            switch (i) {
                case 0:
                    hindernisse.add(new Hindernis(new Vector2(200, currentSpawnY), Assets.HINDERNIS_NORMAL1, 30, HindernisState.NORMAL));
                    break;
                case 9:
                    /* ein Hindernis an der X-Position der Startposition des Spielers, damit der Spieler nicht einfach durch das Feld fliegt */
                    hindernisse.add(new Hindernis(new Vector2(0, getNextY()), Assets.HINDERNIS_PLUS, 40, HindernisState.GREEN));
                    break;

                default:
                    int radius = getRandomRadius();
                    currentSpawnY += MathUtils.random(240, 350);
                    hindernisse.add(randomHindernisse.getRandomHin(radius, getRandomX(radius), currentSpawnY));
                    break;
            }
        }
    }

    public int getRandomRadius() {
        return (int) MathUtils.clamp(MathUtils.random(35, (int)45f+(curScore)), 35, 195);
    }

    public int getRandomX(int radius) {
        return MathUtils.random(GameModel.LEFTWALL + radius, GameModel.RIGHTWALL - radius);
    }

    public int getNextY() {
        currentSpawnY += MathUtils.random(240, 350);
        return currentSpawnY;
    }

    private void addNeuesHindernisUndRemoveAltes(Hindernis hindernis) {
        hindernisse.remove(hindernis);
        int radius = getRandomRadius();
        currentSpawnY += MathUtils.random(240, 350);
        hindernis.refresh(randomHindernisse.getRandomHinType(radius, getRandomX(radius), currentSpawnY));
        hindernisse.add(hindernis);
    }
}

