package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ScoreLine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.modestasv.hurdlecircle.MVInterpolate;
import com.modestasv.hurdlecircle.Assets;

/**
 * Eine simple Linie die verschiedene Punktestände markiert und beim "übertreten" ausgeschaltet wird
 */
public class Line {

    private float height;
    private boolean draw = true;
    private Color color;
    public int posy;
    private ShapeRenderer sr;

    private MVInterpolate colorinterp = new MVInterpolate(Interpolation.linear, 3f, 1,0);

    public Line(float height, int punkteDraw, Color color) {
        this.height = height;
        this.posy = punkteDraw * 300;
        this.color = color.cpy();
        sr = new ShapeRenderer();
    }

    public void draw(float y, OrthographicCamera camera, float leftBound, float rightBound) {

        /* Wenn der Spieler über der Linie ist, dann wird die Linie ausgeschaltet */
        if(y >= posy) {
            turnOff();
        }

        if(!draw) {
            color = new Color(color.r, color.g, color.b, colorinterp.update().getValue());
        }

        if(posy / 300 >= 5) {
            if (camera.frustum.boundsInFrustum(leftBound, posy, 0, rightBound + Math.abs(leftBound) / 2, height / 2, 0f)) {

                Gdx.gl.glEnable(GL20.GL_BLEND);
                Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setProjectionMatrix(camera.combined);
                sr.setColor(color);
                sr.rect(leftBound, posy, rightBound + Math.abs(leftBound), height);
                sr.end();
                Gdx.gl.glDisable(GL20.GL_BLEND);
            }
        }
    }

    public void turnOff() {
        if(draw) {
            Assets.successSound.play(true).setVolume(0.26f);
            draw = false;
        }
    }

    public void turnOn() {
        draw = true;
        colorinterp.reset();
        color = new Color(color.r, color.g, color.b, 1);
    }

    public void setPoints(int points) {
        this.posy = points*300;
    }

}
