package com.badlogic.spymouse.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

public class CameraManager {
    private final OrthographicCamera camera;
    private final Matrix4 matrix4;
    private float preDistance = 0;
    
    
    public CameraManager(OrthographicCamera camera) {
        this.camera = camera;
        matrix4 = new Matrix4();
        matrix4.set(camera.projection).mul(camera.view);
    }
    
    public boolean isIn(MyPoint p) {
        return isIn(p.toV3());
    }
    
    public boolean isIn(Vector3 p) {
        Vector3 v3 = p.cpy().mul(matrix4);
        return isInBounds(v3.x) && isInBounds(v3.y) && isInBounds(v3.z);
    }
    
    private boolean isInBounds(float value) {
        return value >= -1 && value <= 1;
    }
    
    public void zoomApply() {
        if (InputListener.getNumDown() == 2) {
            MyPoint pointer1 = InputListener.getPosition(0);
            MyPoint pointer2 = InputListener.getPosition(1);
            if (pointer1 != null && pointer2 != null) {
                float curDistance = pointer1.dst(pointer2);
                
                if (preDistance != 0 && curDistance != 0) {
                    float zoomRatio = Gdx.graphics.getDeltaTime();
                    if (camera.zoom < 0.5F) {
                        camera.zoom += zoomRatio;
                    } else if (camera.zoom > 1.2F) {
                        camera.zoom -= zoomRatio;
                    } else if (preDistance - curDistance > 0) {
                        camera.zoom += zoomRatio;
                    } else if (preDistance - curDistance < 0) {
                        camera.zoom -= zoomRatio;
                    }
                }
                preDistance = curDistance;
            }
        } else {
            preDistance = 0;
        }
    }
}
