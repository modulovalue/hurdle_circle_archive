package com.modestasv.hurdlecircle.GameScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.modestasv.hurdlecircle.Assets;

/**
 * eine Klasse um das Abspielen von Soundeffekten zu vereinfachen.
 */

public class MVSound {
    public Sound sound;
    public Long id;

    public MVSound(String path) {
        this.sound = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public MVSound play(boolean play) {
        if(Assets.getSoundAllow()) {
            if (play) {
                if (id == null) {
                    id = sound.play(1f);
                } else {
                    sound.stop(id);
                    id = sound.play(1f);
                }
            } else {
                if (id != null) {
                    sound.pause(id);
                }
            }
        }
        return this;
    }

    public void setVolume(float volume) {
        if(id != null) {
            sound.setVolume(id, volume);
        }
    }
}
