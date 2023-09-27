package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Wall;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;
import com.modestasv.hurdlecircle.MVInterpolate;
import com.modestasv.hurdlecircle.Assets;

/**
 *  Zeichnet eine Wand die beim berühren des Bildschirms die "INAKTIV_COLOR" Farbe annimmt;, sonst "AKTIV_COLOR"
 */


public class Wall extends ObjektAbstr {

    public static final Color AKTIV_COLOR = Color.RED;
    public static final Color INAKTIV_COLOR = Color.WHITE;
    public MVInterpolate wallColorInterpolator = new MVInterpolate(Interpolation.linear, 7f, 0, 1);
    public boolean aktivOderInaktiv;


    public Wall(Vector2 pos, float groesse) {
        super(Vector2.Zero, pos, Assets.HINDERNISWALL, groesse);
        getObj().setSize(groesse/3.9f,4000);
        getObj().setColor(Wall.AKTIV_COLOR);
        aktivOderInaktiv = true;
    }

    public void draw(SpriteBatch batch, float spielery) {
        if(aktivOderInaktiv) {
             getObj().setColor(Wall.INAKTIV_COLOR.cpy().lerp(Wall.AKTIV_COLOR, 1 - wallColorInterpolator.updateBW().getValue()));
        } else {
             getObj().setColor(Wall.AKTIV_COLOR.cpy().lerp(Wall.INAKTIV_COLOR,wallColorInterpolator.update().getValue()));
        }
        getObj().setPosition(getPos().x - getObj().getWidth() / 2, spielery - 2000);
        super.draw(batch);
    }

    public void update(boolean aktivOrInaktiv) {
        aktivOderInaktiv = aktivOrInaktiv;
    }
}

