package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

final class ZoomAdapter extends InputAdapter {
    private final OrthographicCamera camera;
    private final InputListener listener;
    private float preDistance = 0;

    public ZoomAdapter(OrthographicCamera camera, InputListener listener) {
        this.camera = camera;
        this.listener = listener;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        preDistance = 0;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (listener.getNumDown() == 2) {
            Vector2 finger1 = listener.getPosition(0);
            Vector2 finger2 = listener.getPosition(1);
            float curDistance = finger1.dst(finger2);

            if (preDistance != 0 && curDistance != 0) {
                float zoomRatio = Gdx.graphics.getDeltaTime();
                if (camera.zoom < 0.5) {
                    camera.zoom += zoomRatio;
                } else if (camera.zoom > 1.5) {
                    camera.zoom -= zoomRatio;
                } else if (preDistance - curDistance > 0) {
                    camera.zoom += zoomRatio;
                } else {
                    camera.zoom -= zoomRatio;
                }
            }
            preDistance = curDistance;
        }
        return false;
    }
}
