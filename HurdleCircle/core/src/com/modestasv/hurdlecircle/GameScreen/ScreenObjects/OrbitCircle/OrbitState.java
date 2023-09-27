package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.OrbitCircle;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Color;

/**
 * NORMAL:
 *      der OrbitCircle wird Weiß
 * GREEN::
 *      der OrbitCircle wird Grün
 * QUESTIONMARK:
 *      der OrbitCircle wird Gelb
 * RED:
 *      der OrbitCircle wird Rot
 */

public enum OrbitState implements State<OrbitCircle> {

    NORMAL() {
        @Override
        public void update(OrbitCircle en) {
            en.getObj().setColor(new Color(Color.WHITE));
        }
    },

    GREEN() {
        @Override
        public void update(OrbitCircle  en) {
            en.getObj().setColor(new Color(Color.GREEN));
        }
    },

    QUESTIONMARK() {
        @Override
        public void update(OrbitCircle  en) {
            en.getObj().setColor(new Color(Color.YELLOW));
        }
    },

    RED() {
        @Override
        public void update(OrbitCircle  en) {
            en.getObj().setColor(new Color(Color.RED));
        }
    };

    @Override
    public void enter(OrbitCircle  entity) {
    }

    @Override
    public void update(OrbitCircle  entity) {
    }

    @Override
    public void exit(OrbitCircle  entity) {
    }

    @Override
    public boolean onMessage(OrbitCircle  entity, Telegram telegram) {
        return false;
    }

}
