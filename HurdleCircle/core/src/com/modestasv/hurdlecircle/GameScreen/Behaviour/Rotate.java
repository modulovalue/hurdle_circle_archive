package com.modestasv.hurdlecircle.GameScreen.Behaviour;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis.HindernisType;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;

/**
 * Ermöglicht das Drehen von "ObjektAbstr" objekten.
 * Ein Objekt wird um einen zufälligen Wert in eine zufällige Richtung gedreht oder mit "drehen" in eine bestimmte
 * Richtung und mit einen übergebenen Winkel gedreht (um das Objekt mit dem Spieler mitzudrehen)
 */
public class Rotate  {

    private float rotateSpeed;
    private boolean uhrzeigersinn;
    protected ObjektAbstr objektAbstr;

    public Rotate(ObjektAbstr objektAbstr) {
        this.objektAbstr = objektAbstr;
        rotateSpeed = MathUtils.random(0, 100);
        uhrzeigersinn = MathUtils.randomBoolean();
    }

    public void doAct(float delta) {
        if(uhrzeigersinn) {
            objektAbstr.getObj().setRotation(objektAbstr.getObj().getRotation() - rotateSpeed * delta );
        } else {
            objektAbstr.getObj().setRotation(objektAbstr.getObj().getRotation() + rotateSpeed * delta);
        }
    }

    public void drehen(boolean uhrzeigersinn, double angle) {
        this.uhrzeigersinn = uhrzeigersinn;
        objektAbstr.getObj().rotate((uhrzeigersinn) ? -(float) angle : (float) angle);
    }

}
