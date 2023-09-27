package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.SpyMouse;

public class MainMenuScreen implements Screen {
    SpyMouse game;
    OrthographicCamera camera;
    Texture icon;
    Rectangle iconPos;
    int WIDTH = Gdx.graphics.getWidth();
    int HEIGHT = Gdx.graphics.getHeight();

    public MainMenuScreen(SpyMouse game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.zoom = 2;
        camera.update();

        icon = new Texture("Spy_Mouse_icon.png");
        iconPos = new Rectangle();
        iconPos.x = WIDTH / 2F - 200;
        iconPos.y = HEIGHT / 2F - 200;
        iconPos.width = 400;
        iconPos.height = 400;

        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                System.out.println(pointer);
                if (pointer == 1) {
                    camera.zoom += 0.1F;
                    System.out.println("x1 = " + screenX + "|y1 = " + screenY);
                }
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);

        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(icon, iconPos.x, iconPos.y, iconPos.width, iconPos.height);
        game.font.draw(game.batch, "Click icon to continue!", iconPos.x, iconPos.y);
        game.batch.end();

        camera.update();
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
