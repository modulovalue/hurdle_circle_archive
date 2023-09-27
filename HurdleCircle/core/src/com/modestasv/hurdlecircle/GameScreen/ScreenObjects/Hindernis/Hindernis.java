package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.modestasv.hurdlecircle.GameScreen.Behaviour.Rotate;
import com.modestasv.hurdlecircle.GameScreen.ScreenObjects.ObjektAbstr;
import com.modestasv.hurdlecircle.Assets;


/**
 * Objekte dieser Klasse sind "Hindernisse" auf dem Feld
 */
public class Hindernis extends ObjektAbstr {

    public StateMachine<Hindernis> stateMachine;
    public float randomNumber;
    public Rotate rotate;
    private ParticleEffect effect = new ParticleEffect();;
    private boolean shouldDrawEffect = false;
    private boolean shouldStartScaleEffect = false;

    public Hindernis(Vector2 pos, String path, float groesse, HindernisState startingState) {
        super(Vector2.Zero, pos, path, groesse);
        rotate = new Rotate(this);
        stateMachine = new DefaultStateMachine<Hindernis>(this, startingState);
        stateMachine.update();
        randomNumber = MathUtils.random(0f,3f);
        if(startingState == HindernisState.QUESTIONMARK) {
            shouldDrawEffect = true;
            setupSparkEffect();
        }
    }

    public void dispose() {
        effect.dispose();
    }

    public void setupSparkEffect() {
        effect.load(Gdx.files.internal("effects/spark.p"), Gdx.files.internal("effects/"));
    }

    public void update(float delta) {
        getBoundingCircle().set(getPos().x, getPos().y, getGroe() / 2);
        rotate.doAct(delta);
    }

    public void refresh(HindernisType hindernisType) {
        shouldDrawEffect = false;
        shouldStartScaleEffect = false;
        setupSparkEffect();
        super.refresh(Vector2.Zero, hindernisType.pos, hindernisType.path, hindernisType.radius);
        rotate = new Rotate(this);
        stateMachine.changeState(hindernisType.state);
        stateMachine.update();
        if(hindernisType.state == HindernisState.QUESTIONMARK) {
            shouldDrawEffect = true;
        } else {
            shouldDrawEffect = false;
        }
    }

    public void drawEffect(SpriteBatch batch) {
        if(shouldDrawEffect && effect != null)  {
            effect.setPosition(getPos().x, getPos().y);
            effect.draw(batch, Gdx.graphics.getDeltaTime());
            if(shouldStartScaleEffect) {
                if(effect.getEmitters().get(0).getScale().getHighMax() > 2500) {
                    shouldDrawEffect = false;
                    return;
                }
                effect.scaleEffect(1.08f);
            }
        }
    }

    public void startScaleEffect() {
        if(!shouldStartScaleEffect) {
            Assets.successSound.play(true).setVolume(0.26f);
        }
        shouldStartScaleEffect = true;
    }


}


