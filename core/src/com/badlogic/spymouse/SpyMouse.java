package com.badlogic.spymouse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.spymouse.adapter.InputManager;
import com.badlogic.spymouse.screen.MainMenuScreen;

public class SpyMouse extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public InputManager inputManager;
    
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("testFnt.fnt"), Gdx.files.internal("testFnt.png"), false);
        inputManager = new InputManager(null, null);
    }
    
    @Override
    public void render() {
        super.render();
    }
    
    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }
}
