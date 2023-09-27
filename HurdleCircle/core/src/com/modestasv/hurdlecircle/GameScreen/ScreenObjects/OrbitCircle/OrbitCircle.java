package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.OrbitCircle;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis.HindernisState;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;
import com.modestasv.hurdlecircle.MVInterpolate;
import com.modestasv.hurdlecircle.Assets;

/**
 * Diese Klasse steht für den Kreis welcher angezeigt wird wenn ein Spieler anfängt sich an einem Hindernis zu drehen.
 */
public class OrbitCircle extends ObjektAbstr {

    public StateMachine<OrbitCircle> stateMachine;
    private MVInterpolate interpolate = new MVInterpolate(Interpolation.linear, 4.5f, 0, 1);

    public OrbitCircle(float groesse) {
        super(Vector2.Zero,Vector2.Zero, Assets.ORBIT_NORMAL, groesse);
        stateMachine = new DefaultStateMachine<OrbitCircle>(this, OrbitState.NORMAL);
        interpolate.setDone();
    }

    public void update(boolean show, Vector2 pos, float groesse, State state) {
        stateMachine.update();
        getObj().setSize(getGroe() + getGroe() / 70, getGroe() + getGroe() / 70);
        getObj().setPosition(
                getPos().x - getObj().getWidth() / 2,
                getPos().y - getObj().getHeight() / 2
        );
        changeState((HindernisState) state);
        if(show) {
            setGroe(groesse);
            setPos(pos);
            getObj().setAlpha(interpolate.update().getValue());
        } else {
            getObj().setAlpha(interpolate.updateBW().getValue());
        }
    }

    public void changeState(HindernisState state) {
        switch (state) {
            case NORMAL:
                stateMachine.changeState(OrbitState.NORMAL);
                break;
            case GREEN:
                stateMachine.changeState(OrbitState.GREEN);
                break;
            case QUESTIONMARK:
                stateMachine.changeState(OrbitState.QUESTIONMARK);
                break;
            case RED:
                stateMachine.changeState(OrbitState.RED);
                break;
        }
    }
}

