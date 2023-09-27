package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Hindernis;

import com.badlogic.gdx.math.Vector2;

/**
 * Ermöglicht die Speicherung von verschiedener Hindernisattributen in einem Objekt
 */

public class HindernisType {

    public String path;
    public HindernisState state;
    public float probability;
    public Vector2 pos;
    public float radius;

    public HindernisType(String path, HindernisState state, float probability) {
        this.path = path;
        this.state = state;
        this.probability = probability;
        this.pos = Vector2.Zero;
        this.radius = 0;
    }

    public HindernisType(String path, HindernisState state, Vector2 pos, float radius) {
        this.path = path;
        this.state = state;
        this.probability = 0;
        this.pos = pos;
        this.radius = radius;
    }
}
