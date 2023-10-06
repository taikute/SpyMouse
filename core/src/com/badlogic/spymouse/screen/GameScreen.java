package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.Device;
import com.badlogic.spymouse.SpyMouse;
import com.badlogic.spymouse.mouse.Mouse;

final class GameScreen implements Screen {
    SpyMouse game;
    OrthographicCamera camera;
    Viewport extendViewport;
    
    int WIDTH = Device.WIDTH;
    int HEIGHT = Device.HEIGHT;
    
    Mouse mouse = new Mouse(0, 0, camera, extendViewport);
    
    public GameScreen(SpyMouse game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        extendViewport = new ExtendViewport(WIDTH, HEIGHT, camera);
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        extendViewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.font.draw(game.batch, "Game Screen!", 300, 300);
        game.batch.end();
        extendViewport.update(WIDTH--, HEIGHT--);
    }
    
    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
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
