package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Spieler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis.Hindernis;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;

/**
 * der Spieler.
 * Kann sich an Hindernisse hängen und sich um sie Drehen.
 * Die Drehung erfolgt in einer Kreisbewegung um das "nearestHindernis" Objekt.
 *
 *  ---- Beschreibung der Drehbewegung -----
 *
 * um den Spieler um ein Hindernis zu drehen wird eine Kreisbewegung um das "nearestHindernis" Objekt umgesetzt.
 *
 * dazu benötigt man:
 *          - ein Radius
 *          - den Mittelpunkt des Kreises um das sich der Spieler drehen soll -> Position von "nearestHindernis"
 *          - die alte Position -> Position des Spielers
 *
 * es gibt 2 Fälle für den Radius:
 *
 * 1 Fall:
 *      der Spieler entfernt sich von "nearestHindernis"
 * 2 Fall:
 *      Spieler nähert sich "nearestHindernis"
 *
 * der Radius im ersten Fall entspricht dem Abstand vom Hindernis zum Spieler
 *
 * um den Radius des zweiten Falles zu erhalten, benütigt man
 * den Abstand vom Hindernis zur Vektorprojektion vom Hindernis auf den Richtungsvektor des Spielers
 *
 *  Vektor a entspricht Position des Hindernisses
 *  Vektor b entspricht Richtungsvektor des Spielers
 *
 *  Man benötigt zuerst ein Skalar s.
 *
 *  s =  a*b / |b|^2
 *
 * dieser Skalar, multipliziert mit b, ergibt die gesuchte Vektorprojektion.
 * der Abstand dieser, zum Hindernis ergibt den gesuchten Radius.
 *
 *
 */

public class Spieler extends ObjektAbstr {

    public StateMachine<Spieler> stateMachine;
    private Hindernis nearestHindernis;
    public boolean uhrzeigersinn;
    public Vector2 proj;
    public float scalarMultip;
    public boolean startedHanging = false;
    public BewegungsManager bewegungsManager = new BewegungsManager();
    public static final float GESCHWINDIGKEIT = 800f;
    public static final float DREH_GESCHWINDIGKEIT = 2.5f;
    public float drehGeschwindigkeit = Spieler.DREH_GESCHWINDIGKEIT;

    public Spieler(Vector2 vel, Vector2 pos, String path, float groesse) {
        super(vel, pos, path, groesse);
        stateMachine = new DefaultStateMachine<Spieler> (this, SpielerState.NOTHANG);
    }

    public void update(float delta, boolean trueTouchingDown, boolean gameOver) {
        proj = getProj( nearestHindernis );
        if(trueTouchingDown) {
            if (!stateMachine.isInState(SpielerState.GAMEOVER)) {
                stateMachine.changeState(SpielerState.HANG);
            }
        } else {
            if (!stateMachine.isInState(SpielerState.GAMEOVER)) {
                stateMachine.changeState(SpielerState.NOTHANG);
            }
        }
        stateMachine.update();
        getObj().setPosition(getX() - getObj().getWidth() / 2, getPos().y - getObj().getHeight() / 2);
        getBoundingCircle().set(getPos().x,getPos().y,getGroe()/2-15);
    }


    public void orbitieren(float radius) {

        bewegungsManager.kreisbewegungUpdate(drehGeschwindigkeit, radius, uhrzeigersinn);

        /* Hindernis wird gedreht */
        getNearestHindernis().rotate.drehen(uhrzeigersinn, bewegungsManager.hindernisDrehWinkel);

        /* Richtung der Bewegung des  Spielers wird geändert */
        getVel().setAngle((float) bewegungsManager.spielerDrehWinkel + bewegungsManager.uhrzeigersinnWinkel());

        /* Spieler wird gehdreht */
        getObj().setRotation((float) bewegungsManager.spielerDrehWinkel);

        setPos(bewegungsManager.kreisbewegung(getNearestHindernis().getPos()));
    }

    public void bewegen() {
        startedHanging = false;
        setPos(bewegungsManager.normalBewegen(Spieler.GESCHWINDIGKEIT, getVel(), getPos()));
    }


    // Hier wird die Richtung der Umdrehung ermittelt.
    // nach vielen verzweifelten Versuchen und langen erfolgslosen Google Sessions kam ich zu diesem Ergebnis
    public void calcOrbRichtung(boolean radiusOrAbstand) {
        if(proj.y > getPos().y) {
            uhrzeigersinn = (radiusOrAbstand) ? proj.x < getNearestHindernis().getPos().x : proj.x > getNearestHindernis().getPos().x;
        } else if(proj.y < getPos().y) {
            uhrzeigersinn = (radiusOrAbstand) ? proj.x > getNearestHindernis().getPos().x : proj.x < getNearestHindernis().getPos().x;
        }
    }

    /* gibt eine Vektorprojektion zurück */
    public Vector2 getProj(Hindernis hindernis) {
        return vectorProj(getDir(), hindernis.getPos(), getPos());
    }

    /* Vektorprojection von bb auf aa wird zurückgegeben (angepasst mit origin) */
    private Vector2 vectorProj ( Vector2 aa, Vector2 bb, Vector2 origin) {
        Vector2 a = aa.cpy().sub(origin);
        scalarMultip  = getSca(aa,bb,origin);
        return a.scl(scalarMultip).add(origin);
    }

    /* Skalar für die Vektorprojection von bb auf aa wird zurückgegeben (angepasst mit origin) */
    public float getSca(Vector2 aa, Vector2 bb, Vector2 origin) {
        Vector2 a = aa.cpy().sub(origin);
        Vector2 b = bb.cpy().sub(origin);
        return  (float) (a.dot(b) / Math.pow(a.len(),2));
    }

    public void setNearestHindernis(Hindernis nearestHindernis) {
        this.nearestHindernis = nearestHindernis;
    }

    public Hindernis getNearestHindernis() {
        return nearestHindernis;
    }

}