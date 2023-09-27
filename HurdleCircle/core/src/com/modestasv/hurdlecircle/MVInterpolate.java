package com.modestasv.hurdlecircle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;

/**
 * erlaubt eine Interpolation von "start" bis "end" in der zeit "speed".
 *
 * LibGDX stellt die Klasse "Interpolation" zur verfügung, welche verschiedene Funtionen für eine Interpolation bietet.
 * Hiermit wird das Nutzen dieser Klasse erleichtert
 *
 */
public class MVInterpolate {
    private Interpolation interpolation;
    private float value;
    private float speed;
    private float start;
    private float end;

    public MVInterpolate(Interpolation interpolation, float speed, float start, float end) {
        this.interpolation = interpolation;
        this.value = 0;
        this.speed = speed;
        this.start = start;
        this.end = end;
    }

    /* besitzt sich selbst als Rückgabewert und ermöglicht Chaining ....update().getValue() */
    public MVInterpolate update() {
        value = MathUtils.clamp(value + speed * Gdx.graphics.getDeltaTime(), 0, 1);
        return this;
    }

    public MVInterpolate updateBW() {
        value = MathUtils.clamp(value - speed * Gdx.graphics.getDeltaTime(), 0, 1);
        return this;
    }

    public void reset() {
        value = 0;
    }

    public void setDone() {
        value = 1;
    }

    public boolean isDone() {
        return (value == 1);
    }

    public float getValue() {
        return interpolation.apply(start, end, value);
    }

    public void setValue(float value) {
        this.value = value;
    }
}
