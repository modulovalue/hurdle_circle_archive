package com.modestasv.hurdlecircle.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.modestasv.hurdlecircle.Game;
import com.modestasv.hurdlecircle.Interfaces.IProxable;

public class DesktopLauncher  {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.height = 800;
        cfg.width = 640;
        cfg.title = "Hurdle Circle";

        new LwjglApplication(new Game(new IProxable() {
            @Override
            public float getProx() {
                return -1;
            }
        }), cfg);
    }

}
