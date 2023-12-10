package com.badlogic.spymouse.screen.menu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Button;
import com.badlogic.spymouse.helper.Device;
import com.badlogic.spymouse.helper.PrefKeys;
import com.badlogic.spymouse.screen.BaseScreen;
import com.badlogic.spymouse.screen.stage.Stage1Screen;

public final class MainMenuScreen extends BaseScreen {
    private final MyGame game;
    
    private final Texture icon = new Texture("image/spy-mouse-icon.png");
    private final Texture settingImage = new Texture("image/setting.png");
    
    private final Button settingButton = new Button(Device.WIDTH - 150, Device.HEIGHT - 150, 100, 100);
    private final Button playButton = new Button(Device.WIDTH / 2F - 150, Device.HEIGHT / 2F - 150, 300, 300);
    
    public MainMenuScreen(MyGame game) {
        this.game = game;
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
        settingButton.draw(game.baseBatch, settingImage);
        
        float recordTime = game.preferences.getFloat(PrefKeys.RECORD, Float.MAX_VALUE);
        game.font.draw(game.baseBatch, "Record: " + Math.round(recordTime) + "s", Device.WIDTH / 2F - 150, Device.HEIGHT - 50);
        
        playButton.draw(game.baseBatch, icon);
        game.font.draw(game.baseBatch, "Touch to play!", Device.WIDTH / 2F - 150, Device.HEIGHT / 2F - 150);
        game.baseBatch.end();
        
        if (playButton.isPress()) {
            game.clickEffect.play();
            game.setScreen(new Stage1Screen(game));
        }
        if (settingButton.isPress()) {
            game.clickEffect.play();
            game.setScreen(new SettingScreen(game));
        }
    }
    
    @Override
    public void dispose() {
        icon.dispose();
        settingImage.dispose();
    }
}
