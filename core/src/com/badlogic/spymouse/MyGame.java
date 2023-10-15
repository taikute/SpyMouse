package com.badlogic.spymouse;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.spymouse.helper.InputListener;
import com.badlogic.spymouse.screen.MainMenuScreen;

public class MyGame extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public ShapeRenderer shape;
    
    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("testFnt.fnt"), false);
        shape = new ShapeRenderer();
        
        // Set listener
        Gdx.input.setInputProcessor(InputListener.getAdapter());
        // Last
        setScreen(new MainMenuScreen(this));
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
