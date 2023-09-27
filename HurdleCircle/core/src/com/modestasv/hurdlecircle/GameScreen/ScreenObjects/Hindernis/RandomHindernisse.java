package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.Assets;

import java.util.ArrayList;

/**
 * Ermöglicht eine zufällige Generation von Hindernissen mit einer vorgegebenen Wahrscheinlichkeit
 *
 *
 *
 * in "hindernisMoeglichkeiten" werden die verschiedenen Möglichkeiten für Hindernisse gespeichert
 *
 * in der cumulativeSum ArrayList werden die Wahrscheinlichkeiten als aufeinanderfolgende Summe gespeichert.
 *
 * maxRand gibt die Summe aller Wahrscheinlichkeiten an.
 *
 * x = zufällige Zahl zwischen 0 und maxRand
 *
 * y = Größter cumulativeSum Wert für den Gilt  wert <= x
 * 
 * der index von y in der cumulativeSum ArrayList entspricht dem index des neuen zufälligen Hindernisses im hindernisMoeglichkeiten Array
 *
 *
 */
public class RandomHindernisse {

    /* Verschiedene Hindernismöglichkeiten */
    private HindernisType[] hindernisMoeglichkeiten = new HindernisType[5];

    /* Wahrscheinlichkeiten mit der jeweils nächten Wahrscheinlichkeit zusammenaddiert */
    private ArrayList<Float> cumulativeSum = new ArrayList<Float>();

    private float maxRand = 0;

    public RandomHindernisse() {
        initHindernisKombinationen();
        prepareRandomHindernisse();
    }

    /* Verschiedene Kombinationen werden initialisiert mit ihren Wahrscheinlichkeiten als letzter Parameter */
    private void initHindernisKombinationen() {
        hindernisMoeglichkeiten[0] = new HindernisType(Assets.HINDERNIS_PLUS, HindernisState.GREEN, 5f);
        hindernisMoeglichkeiten[1] = new HindernisType(Assets.HINDERNIS_BLOCK, HindernisState.RED, 5f);
        hindernisMoeglichkeiten[2] = new HindernisType(Assets.HINDERNIS_NORMAL1, HindernisState.NORMAL, 30f);
        hindernisMoeglichkeiten[3] = new HindernisType(Assets.HINDERNIS_NORMAL2, HindernisState.NORMAL, 30f);
        hindernisMoeglichkeiten[4] = new HindernisType(Assets.HINDERNIS_QUESTION, HindernisState.QUESTIONMARK, 1.5f);
    }

    /* ArrayLists werden gefüllt */
    private void prepareRandomHindernisse() {
        for (HindernisType hindernisType : hindernisMoeglichkeiten) {
            maxRand += hindernisType.probability;
            if(cumulativeSum.isEmpty()) {
                cumulativeSum.add(hindernisType.probability);
            } else {
                if(hindernisType.probability == 0) {
                    cumulativeSum.add(0f);
                } else {
                    cumulativeSum.add(hindernisType.probability+cumulativeSum.get(cumulativeSum.size()-1));
                }
            }
        }
    }

    public Hindernis getRandomHin(float radius, float x, float y) {
        int index = 0;
        float randomCur = MathUtils.random(0f, maxRand);
        for (Float zahl : cumulativeSum) {
            if ( randomCur <= zahl) {
                index = cumulativeSum.indexOf(zahl);
                break;
            }
        }
        return new Hindernis( new Vector2(x, y), hindernisMoeglichkeiten[index].path, radius, hindernisMoeglichkeiten[index].state);
    }

    public HindernisType getRandomHinType(float radius, float x, float y) {

        int index = 0;
        for (Float zahl : cumulativeSum) {
            if ( MathUtils.random(0f, maxRand) <= zahl) {
                index = cumulativeSum.indexOf(zahl);
                break;
            }
        }
        return new HindernisType( hindernisMoeglichkeiten[index].path, hindernisMoeglichkeiten[index].state, new Vector2(x, y), radius);
    }
}
