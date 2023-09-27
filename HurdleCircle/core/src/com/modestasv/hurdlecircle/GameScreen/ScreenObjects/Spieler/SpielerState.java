
package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Spieler;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.MathUtils;

/**
 * GAMEOVER:
 *      nichts passiert.
 * HANG:
 *      sollte sich der Spieler in einem Abstand von
 *      radius +- 0.2 zum "nearestHindernis" befinden, so wird er um das
 *      "nearestHindernis" gedreht, ansonsten bewegt er sich normal weiter
 * NOTHANG:
 *      der Spieler bewegt sich normal weiter
 */
public enum SpielerState implements State<Spieler> {

    GAMEOVER() {
        @Override
        public void update(Spieler en) {
        }
    },

    HANG() {
        @Override
        public void update(Spieler en) {

            /* Wenn en.scalarMultip > 0 dann muss mit dem Abstand vom Hindernis zur Vektorprojektion gerechnet werden sonst mit dem Abstand vom Spieler bis zum Hindernis */
            float radius = en.getNearestHindernis().getPos().dst( en.scalarMultip > 0 ? en.proj : en.getPos());

            if( MathUtils.isEqual(en.getPos().dst(en.getNearestHindernis().getPos()), radius, 0.2f)) {
                if (!en.startedHanging) {
                    en.startedHanging = true;
                    en.bewegungsManager.spielerDrehWinkel = (float) en.bewegungsManager.getSpielerDrehWinkel(en.getNearestHindernis().getPos(), en.getPos());
                    en.calcOrbRichtung(en.scalarMultip > 0);
                }
                if(radius < 1000) {
                    en.orbitieren(radius);
                } else {
                    en.bewegen();
                }
            } else {
                en.bewegen();
            }
        }
    },

    NOTHANG() {
        @Override
        public void update(Spieler en) {
            en.bewegen();
        }
    };




    @Override
    public void enter(Spieler entity) {

    }

    @Override
    public void update(Spieler entity) {

    }

    @Override
    public void exit(Spieler entity) {

    }

    @Override
    public boolean onMessage(Spieler entity, Telegram telegram) {
        return false;
    }
}