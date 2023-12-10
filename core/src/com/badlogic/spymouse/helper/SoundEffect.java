package com.badlogic.spymouse.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Disposable;

public class SoundEffect implements Disposable {
    private final Sound effect;
    private final Preferences preferences;
    
    public SoundEffect(String internalPath) {
        effect = Gdx.audio.newSound(Gdx.files.internal(internalPath));
        preferences = Gdx.app.getPreferences(PrefKeys.PREF_NAME);
    }
    
    public void play() {
        if (preferences.getBoolean(PrefKeys.EFFECT_ON)) {
            effect.play();
        }
    }
    
    @Override
    public void dispose() {
        effect.dispose();
    }
}
