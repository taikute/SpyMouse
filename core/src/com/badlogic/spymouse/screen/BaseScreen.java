package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Screen;

public abstract class BaseScreen implements Screen {
    @Override
    public void show() {
    
    }
    
    @Override
    public void render(float delta) {
    
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
        dispose();
    }
    
    @Override
    public void dispose() {
    
    }
}
