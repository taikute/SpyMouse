package com.badlogic.spymouse.screen.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Button;
import com.badlogic.spymouse.helper.Device;
import com.badlogic.spymouse.helper.PrefKeys;
import com.badlogic.spymouse.screen.BaseScreen;

public class SettingScreen extends BaseScreen {
    private final MyGame game;
    private final Texture trackOnImage = new Texture(Gdx.files.internal("image/track-on.png"));
    private final Texture trackOffImage = new Texture(Gdx.files.internal("image/track-off.png"));
    private final Texture effectOnImage = new Texture(Gdx.files.internal("image/effect-on.png"));
    private final Texture effectOffImage = new Texture(Gdx.files.internal("image/effect-off.png"));
    private final Texture backImage = new Texture(Gdx.files.internal("image/back.png"));
    
    private final Button backButton = new Button(20, Device.HEIGHT - 170, 150F * backImage.getWidth() / backImage.getHeight(), 150);
    private final Button trackSwapButton;
    private final Button effectSwapButton;
    
    public SettingScreen(MyGame game) {
        this.game = game;
        float btnSize = 300;
        float padding = btnSize / 6;
        float trackX = Device.WIDTH / 2F - btnSize - padding;
        float trackY = Device.HEIGHT / 2F - btnSize / 2;
        trackSwapButton = new Button(trackX, trackY, btnSize, btnSize);
        float effectX = trackX + btnSize + padding * 2;
        effectSwapButton = new Button(effectX, trackY, btnSize, btnSize);
    }
    
    @Override
    public void show() {
        if (game.preferences.getBoolean(PrefKeys.TRACK_ON)) {
            game.soundTrack.play();
        }
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        
        game.baseBatch.begin();
        backButton.draw(game.baseBatch, backImage);
        if (game.preferences.getBoolean(PrefKeys.TRACK_ON)) {
            game.soundTrack.play();
            trackSwapButton.draw(game.baseBatch, trackOnImage);
        } else {
            game.soundTrack.pause();
            trackSwapButton.draw(game.baseBatch, trackOffImage);
        }
        if (game.preferences.getBoolean(PrefKeys.EFFECT_ON)) {
            effectSwapButton.draw(game.baseBatch, effectOnImage);
        } else {
            effectSwapButton.draw(game.baseBatch, effectOffImage);
        }
        game.baseBatch.end();
        
        if (backButton.isPress()) {
            game.setScreen(new MainMenuScreen(game));
        }
        if (trackSwapButton.isPress()) {
            game.clickEffect.play();
            game.preferences.putBoolean(PrefKeys.TRACK_ON, !game.preferences.getBoolean(PrefKeys.TRACK_ON));
            game.preferences.flush();
        }
        if (effectSwapButton.isPress()) {
            game.clickEffect.play();
            game.preferences.putBoolean(PrefKeys.EFFECT_ON, !game.preferences.getBoolean(PrefKeys.EFFECT_ON));
            game.preferences.flush();
        }
    }
    
    @Override
    public void dispose() {
        backImage.dispose();
        trackOnImage.dispose();
        trackOffImage.dispose();
        effectOnImage.dispose();
        effectOffImage.dispose();
    }
}
