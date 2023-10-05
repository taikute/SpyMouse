package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public class InputListener extends InputAdapter {
    private int numDown = 0;
    private final Vector2[] positions = new Vector2[Gdx.input.getMaxPointers()];
    private final OrthographicCamera camera;
    private final Viewport viewport;

    public InputListener(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
    }

    public int getNumDown() {
        return numDown;
    }

    public Vector2 getPosition(int pointer) {
        Vector3 v3 = camera.unproject(new Vector3(positions[pointer].x, positions[pointer].y, 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        return new Vector2(v3.x, v3.y);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        positions[pointer] = new Vector2(screenX, screenY);
        numDown++;
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        positions[pointer] = Vector2.Zero;
        numDown--;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        positions[pointer] = new Vector2(screenX, screenY);
        return false;
    }
}
