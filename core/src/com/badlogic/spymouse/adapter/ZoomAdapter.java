package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.spymouse.InputListener;

final class ZoomAdapter extends InputAdapter {
    private final OrthographicCamera camera;
    private float preDistance = 0;
    
    public ZoomAdapter(OrthographicCamera camera) {
        this.camera = camera;
    }
    
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        preDistance = 0;
        return false;
    }
    
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (InputListener.getNumDown() == 2) {
            Vector2 finger1 = InputListener.getPosition(0);
            Vector2 finger2 = InputListener.getPosition(1);
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
