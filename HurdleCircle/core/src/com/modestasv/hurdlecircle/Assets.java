package com.modestasv.hurdlecircle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.modestasv.hurdlecircle.GameScreen.MVSound;


/**
 * Assets kümmert sich um
 *      - den Speicherort aller Ressourcen
 *      - einen AssetManager für den sparsamen Zugriff auf Texturen.
 *        der AssetManager managt die Texturen und verhindert doppelte Instanziierungen der Texturen.
 *      - die Speicherung des Highscores und diversen anderen Einstellungen.
 *      - die Spielsounds.
 */
public class Assets extends AssetManager {

    public static final String TITLE_NAME = "Hurdle Circle";

    private static Assets instance;

    public static final String ORBIT_NORMAL       = "data/orbitCircleNorm.png";
    public static final String SPIELER_NORMAL     = "data/player.png";
    public static final String HINDERNISWALL      = "data/hintergrundtest.png";
    public static final String HINDERNIS_NORMAL1  = "data/hindernis1.png";
    public static final String HINDERNIS_NORMAL2  = "data/hindernis2.png";
    public static final String HINDERNIS_BLOCK    = "data/hindernisBlock.png";
    public static final String HINDERNIS_PLUS     = "data/hindernisPlus.png";
    public static final String HINDERNIS_QUESTION = "data/hindernisQuestion.png";

    public static final String HOME_ICON          = "data/icon/home.png";
    public static final String SETTINGS_ICON      = "data/icon/cog.png";
    public static final String BACK_ICON          = "data/icon/back.png";
    public static final String PLAY_ICON          = "data/icon/play.png";
    public static final String PAUSE_ICON         = "data/icon/pause.png";
    public static final String RESTART_ICON         = "data/icon/restart.png";

    public Assets() {
        initAssets();
    }

    public static Assets get() {
        if(instance == null) {
            instance = new Assets();
            return instance;
        } else {
            return instance;
        }
    }

    public void initAssets() {
        TextureLoader.TextureParameter param = new TextureLoader.TextureParameter();
        param.minFilter = Texture.TextureFilter.MipMapLinearNearest;
        param.magFilter = Texture.TextureFilter.Nearest;
        param.genMipMaps = true;

        load(ORBIT_NORMAL, Texture.class, param);
        load(SPIELER_NORMAL, Texture.class, param);
        load(HINDERNISWALL, Texture.class, param);
        load(HINDERNIS_NORMAL1, Texture.class, param);
        load(HINDERNIS_NORMAL2, Texture.class, param);
        load(HINDERNIS_BLOCK, Texture.class, param);
        load(HINDERNIS_PLUS, Texture.class, param);
        load(HINDERNIS_QUESTION, Texture.class, param);
        load(HOME_ICON, Texture.class, param);
        load(SETTINGS_ICON, Texture.class, param);
        load(BACK_ICON, Texture.class, param);
        load(PLAY_ICON, Texture.class, param);
        load(PAUSE_ICON, Texture.class, param);
        load(RESTART_ICON, Texture.class, param);

        mainMusic.setLooping(true);

    }

    public Texture getTexture(String res){
        if(isLoaded(res)) {
            return get( res, Texture.class);
        } else {
            Gdx.app.error("Error", "ERROR LOADING RES");
            Gdx.app.exit();
            return null;
        }
    }

    private static final String PREF_NAME = "StoragePreferencesHurdleCircle";
    private static final String PREFNAME_PROXIMITY = "ProximityAllow";
    private static final String PREFNAME_HIGHSCORE = "Highscore";
    private static final String PREFNAME_VIBRATEALLOW = "Vibrate";
    private static final String PREFNAME_SOUNDALLOW = "SoundAllow";

    private static Preferences prefs = Gdx.app.getPreferences(PREF_NAME);

    public static boolean getProximityBool() {
        return prefs.getBoolean(PREFNAME_PROXIMITY, true);
    }
    public static void putProxBool( boolean bool) {
        prefs.putBoolean(PREFNAME_PROXIMITY, bool);
        prefs.flush();
    }

    public static int getHighscore() {
        return prefs.getInteger(PREFNAME_HIGHSCORE, 0);
    }
    public static void putHighscore(int highscore) {
        prefs.putInteger(PREFNAME_HIGHSCORE, highscore);
        prefs.flush();
    }

    public static boolean getVibrate() {
        return prefs.getBoolean(PREFNAME_VIBRATEALLOW, true);
    }
    public static void putVibrate(boolean vibrate) {
        prefs.putBoolean(PREFNAME_VIBRATEALLOW, vibrate);
        prefs.flush();
    }

    public static boolean getSoundAllow() { return prefs.getBoolean(PREFNAME_SOUNDALLOW, true); }
    public static void putSoundAllow(boolean sound) {
        prefs.putBoolean(PREFNAME_SOUNDALLOW, sound);
        prefs.flush();
    }


    private static final String MUSIC_SOUND = "sound/ostttl.ogg";
    private static Music mainMusic = Gdx.audio.newMusic(Gdx.files.internal(MUSIC_SOUND));

    public static void playMainMusic() {
        if(getSoundAllow()) {
            mainMusic.play();
        }
    }

    public static void pauseMainMusic() {
        mainMusic.pause();
    }

    public static float LEISE = 0.4f;
    public static float NORMAL= 0.8f;

    public static void setMainMusicVolume(float volume) {
       mainMusic.setVolume(volume);
    }

    private static final String SUCCESS_SOUND = "sound/success.ogg";
    public static final MVSound successSound = new MVSound(SUCCESS_SOUND);

    private static final String CLICK_SOUND = "sound/click.ogg";
    public static final MVSound clickSound = new MVSound(CLICK_SOUND);


    public static final int GAME_WORLD_HEIGHT = 800;

    public static int getHeight() {
        return  GAME_WORLD_HEIGHT;
    }

    public static int getWidth() {
        return getHeight() * Gdx.graphics.getWidth() / Gdx.graphics.getHeight();
    }

}
