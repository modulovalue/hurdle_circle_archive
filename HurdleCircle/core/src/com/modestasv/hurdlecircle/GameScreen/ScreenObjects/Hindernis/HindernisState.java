package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.graphics.Color;

/**
 * NORMAL:
 *      das Hindernis wird weiß
 * GREEN:
 *      das Hindernis wird Grün
 * QUESTIONMARK:
 *      das Hindernis wird Gelb
 * RED:
 *      das Hindernis wird Rot
 */

public enum HindernisState implements State<Hindernis> {

    NORMAL() {
        @Override
        public void update(Hindernis en) {
            en.getObj().setColor(new Color(Color.WHITE));
        }
    },

    GREEN() {
        @Override
        public void update(Hindernis en) {
            en.getObj().setColor(new Color(Color.GREEN));
        }
    },

    QUESTIONMARK() {
        @Override
        public void update(Hindernis en) {
            en.getObj().setColor(new Color(Color.YELLOW));

        }
    },

    RED() {
        @Override
        public void update(Hindernis en) {
            en.getObj().setColor(new Color(Color.RED));
        }
    };

    @Override
    public void enter(Hindernis entity) {
    }

    @Override
    public void update(Hindernis entity) {
    }

    @Override
    public void exit(Hindernis entity) {
    }

    @Override
    public boolean onMessage(Hindernis entity, Telegram telegram) {
        return false;
    }

}
