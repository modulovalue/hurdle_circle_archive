package com.modestasv.hurdlecircle.GameScreen.ScreenObjects.Wall;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * wird genutzt um beide Wände gleichzeitig zu aktualisieren
 */
public class WallManager {

    public Wall leftWall, rightWall;

    public WallManager(Wall leftWall, Wall rightWall) {
        this.leftWall = leftWall;
        this.rightWall = rightWall;
    }

    public void draw(SpriteBatch batch, float spielery) {
        leftWall.draw(batch, spielery);
        rightWall.draw(batch, spielery);
    }

    public void update(boolean trueTouchingDown) {
        rightWall.update(!trueTouchingDown);
        leftWall.update(!trueTouchingDown);
    }

}
