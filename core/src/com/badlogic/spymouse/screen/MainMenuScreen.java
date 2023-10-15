package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Device;
import com.badlogic.spymouse.helper.MyButton;

public final class MainMenuScreen implements Screen {
    private final MyGame game;
    
    public MainMenuScreen(MyGame game) {
        this.game = game;
    }
    
    private final Texture icon = new Texture("Spy_Mouse_icon.png");
    
    @Override
    public void show() {
        System.out.println("On MainMenuScreen");
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        
        MyButton button = new MyButton(Device.WIDTH / 2F - 150, Device.HEIGHT / 2F - 150, 300, 300);
        game.batch.begin();
        button.draw(game.batch, icon);
        game.font.draw(game.batch, "Touch to play!", Device.WIDTH / 2F - 150,Device.HEIGHT / 2F - 150);
        game.batch.end();
        
        if (button.isPress()) {
            game.setScreen(new GameScreen(game));
        }
    }
    
    @Override
    public void resize(int width, int height) {
    
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void hide() {
    
    }
    
    @Override
    public void dispose() {
    }
}
