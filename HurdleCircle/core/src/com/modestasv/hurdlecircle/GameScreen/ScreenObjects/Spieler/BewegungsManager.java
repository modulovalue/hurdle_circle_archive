package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Spieler;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/**
 * Ein Manager, der sich um die Bewegung des Spielers kümmert.
 */
public class BewegungsManager {

    public double spielerDrehWinkel = 0;
    public double hindernisDrehWinkel;
    private boolean uhrzeigersinn;
    private float radius;
    private Vector2 vectorRight = new Vector2(1,0);

    public Vector2 normalBewegen(float speed, Vector2 vel, Vector2 oldPos ) {
        return new Vector2( oldPos.x + (vel.cpy().nor().x * Gdx.graphics.getDeltaTime() * speed),
                            oldPos.y + (vel.cpy().nor().y * Gdx.graphics.getDeltaTime() * speed));
    }

    public Vector2 kreisbewegung(Vector2 center) {
        return new Vector2((center.x + radius * MathUtils.cosDeg((float) spielerDrehWinkel))
                , (center.y + radius * MathUtils.sinDeg((float) spielerDrehWinkel))
        );
    }

    public void kreisbewegungUpdate(float speed, float radius, boolean uhrzeigersinn) {
        this.radius = radius;
        this.uhrzeigersinn = uhrzeigersinn;
        hindernisDrehWinkel = (360 / speed) * ((double) Gdx.graphics.getDeltaTime() % speed) / (radius/300);
        spielerDrehWinkel += (uhrzeigersinn) ? - hindernisDrehWinkel : hindernisDrehWinkel;
    }

    public float uhrzeigersinnWinkel() {
        return (uhrzeigersinn) ? -90 : 90;
    }

    public double getSpielerDrehWinkel(Vector2 a, Vector2 b) {
        Vector2 p = b.cpy().sub(a);
        double ret = Math.acos( p.dot(vectorRight) / ( p.len() * vectorRight.len())) * (180/Math.PI);
        return (p.y >= 0) ?  ret : (360 - ret);
    }

}
