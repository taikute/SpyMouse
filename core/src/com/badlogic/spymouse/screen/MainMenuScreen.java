package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.SpyMouse;
import com.badlogic.spymouse.actor.Mouse;
import com.badlogic.spymouse.adapter.ZoomAdapter;

public class MainMenuScreen implements Screen {
    SpyMouse game;
    OrthographicCamera camera;
    Viewport extendViewport;

    Mouse mouse;

    Texture icon;
    Rectangle iconPos;

    int WIDTH = Gdx.graphics.getWidth();
    int HEIGHT = Gdx.graphics.getHeight();

    public MainMenuScreen(SpyMouse game) {
        this.game = game;
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        extendViewport = new ExtendViewport(WIDTH, HEIGHT, camera);
        mouse = new Mouse();

        icon = new Texture("Spy_Mouse_icon.png");
        iconPos = new Rectangle();
        iconPos.width = 400;
        iconPos.height = 400;
        iconPos.x = WIDTH / 2F - iconPos.width / 2;
        iconPos.y = HEIGHT / 2F - iconPos.height / 2;

        Gdx.input.setInputProcessor(new ZoomAdapter(camera));
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        extendViewport.apply();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();
        game.batch.draw(new TextureRegion(icon, 0, 0, 72, 72), iconPos.x, iconPos.y, iconPos.width, iconPos.height);
        //game.font.draw(game.batch, "Click icon to continue!", iconPos.x, iconPos.y);
        game.batch.end();

        mouse.draw(game.batch);

        camera.position.x = iconPos.x + iconPos.width / 2;
        camera.position.y = iconPos.y + iconPos.height / 2;
        camera.update();
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
