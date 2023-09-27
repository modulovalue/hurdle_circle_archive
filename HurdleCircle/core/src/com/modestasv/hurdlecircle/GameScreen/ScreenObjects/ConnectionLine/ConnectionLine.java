package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ConnectionLine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.MVInterpolate;


/**
 * ein Objekt dieser Klasse zeichnet eine VerbindungsLinie zwischen
 * dem "Vector2 hin" und "Vector2 spieler" (in der draw Methode)
 */

public class ConnectionLine {
    private ShapeRenderer sr;
    private boolean blackAndWhite = false;
    private MVInterpolate lengthInterpolator = new MVInterpolate(Interpolation.bounce, 4f, 0, 1);
    private ParticleEffect effect;

    private Vector2 goal;

    public ConnectionLine() {
        sr = new ShapeRenderer();
        this.effect = new ParticleEffect();
        effect.load(Gdx.files.internal("effects/grind.p"), Gdx.files.internal("effects/"));

        float pScale = 3f;

        float scaling = effect.getEmitters().get(0).getScale().getHighMax();
        effect.getEmitters().get(0).getScale().setHigh(scaling * pScale);

        scaling = effect.getEmitters().get(0).getScale().getLowMax();
        effect.getEmitters().get(0).getScale().setLow(scaling * pScale);

        scaling = effect.getEmitters().get(0).getVelocity().getHighMax();
        effect.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);

        scaling = effect.getEmitters().get(0).getVelocity().getLowMax();
        effect.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
    }

    public void setBlackAndWhite(boolean blackAndWhite) {
        this.blackAndWhite = blackAndWhite;
    }

    public void draw(Color color, Vector2 hin, Vector2 spieler, SpriteBatch batch) {
        if(!blackAndWhite) {
            sr.setColor(new Color(color.r, color.g, color.b, 100));
        } else  {
            sr.setColor(new Color(1, 1, 1, 1));
        }
        goal = spieler.cpy().sub(hin.cpy());
        goal.scl(lengthInterpolator.update().getValue());

        batch.end();
        sr.setProjectionMatrix(batch.getProjectionMatrix());
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rectLine(spieler.x, spieler.y, spieler.cpy().sub(goal).x, spieler.cpy().sub(goal).y, 3f);
        sr.end();
        batch.begin();
    }

    public void stopDraw(SpriteBatch batch) {
        effect.draw(batch, Gdx.graphics.getDeltaTime());
        effect.findEmitter("grind").setContinuous(false);
        lengthInterpolator.reset();
    }

    public void startDraw(Color color, OrthographicCamera camera, Vector2 hin, Vector2 spieler, SpriteBatch batch) {
        draw(color, hin, spieler, batch);
        effect.draw(batch, Gdx.graphics.getDeltaTime());
        effect.findEmitter("grind").setContinuous(true);
        effect.setPosition(spieler.cpy().sub(goal).x, spieler.cpy().sub(goal).y);
    }
}
