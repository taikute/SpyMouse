package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class ZoomAdapter extends InputAdapter {
    OrthographicCamera camera;

    public ZoomAdapter(OrthographicCamera camera) {
        this.camera = camera;
    }

    int numFingerDown = 0;
    float preDistance = 0;
    final Vector2[] fingerPositions = new Vector2[Gdx.input.getMaxPointers()];

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        fingerPositions[pointer] = new Vector2(screenX, screenY);
        numFingerDown++;
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        preDistance = 0;
        numFingerDown--;
        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        fingerPositions[pointer] = new Vector2(screenX, screenY);
        if (numFingerDown == 2) {
            float curDistance = fingerPositions[0].dst(fingerPositions[1]);
            if (preDistance != 0 && curDistance != 0) {
                float zoomFactor = preDistance / curDistance;
                camera.zoom *= zoomFactor;
                if (camera.zoom < 0.5) {
                    camera.zoom /= zoomFactor;
                }
                if (camera.zoom > 1.5) {
                    camera.zoom /= zoomFactor;
                }
            }
            preDistance = curDistance;
        }
        return true;
    }
}
