package com.badlogic.spymouse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.spymouse.helper.Listener;
import com.badlogic.spymouse.helper.PrefKeys;
import com.badlogic.spymouse.helper.SoundEffect;
import com.badlogic.spymouse.screen.menu.MainMenuScreen;

public final class MyGame extends Game {
    public Preferences preferences;
    public SpriteBatch baseBatch;
    public SpriteBatch combineBatch;
    public BitmapFont font;
    public ShapeRenderer combineShape;
    public ShapeRenderer baseShape;
    public SoundEffect clickEffect;
    public Music soundTrack;
    
    @Override
    public void create() {
        preferences = Gdx.app.getPreferences(PrefKeys.PREF_NAME);
        baseBatch = new SpriteBatch();
        combineBatch = new SpriteBatch();
        combineShape = new ShapeRenderer();
        baseShape = new ShapeRenderer();
        font = new BitmapFont(Gdx.files.internal("testFnt.fnt"));
        clickEffect = new SoundEffect("sound/click.wav");
        soundTrack = Gdx.audio.newMusic(Gdx.files.internal("music/track.mp3"));
        soundTrack.setLooping(true);
        
        if (!preferences.contains(PrefKeys.TRACK_ON)) {
            preferences.putBoolean(PrefKeys.TRACK_ON, true);
        }
        if (!preferences.contains(PrefKeys.EFFECT_ON)) {
            preferences.putBoolean(PrefKeys.EFFECT_ON, true);
        }
        preferences.flush();
        
        Gdx.input.setInputProcessor(Listener.getAdapter());
        setScreen(new MainMenuScreen(this));
    }
    
    @Override
    public void dispose() {
        baseBatch.dispose();
        combineBatch.dispose();
        baseShape.dispose();
        combineShape.dispose();
        font.dispose();
        clickEffect.dispose();
        soundTrack.dispose();
        super.dispose();
    }
}
