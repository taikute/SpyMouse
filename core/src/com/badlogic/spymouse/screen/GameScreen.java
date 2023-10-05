package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.Device;
import com.badlogic.spymouse.SpyMouse;
import com.badlogic.spymouse.mouse.Mouse;

public class GameScreen implements Screen {
    SpyMouse game;
    OrthographicCamera camera;
    Viewport extendViewport;

    Mouse mouse = new Mouse(300,300);

    public GameScreen(SpyMouse game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        extendViewport = new ExtendViewport(Device.WIDTH, Device.HEIGHT, camera);
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

    }

    @Override
    public void dispose() {

    }
}
