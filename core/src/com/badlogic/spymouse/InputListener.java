package com.badlogic.spymouse;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class InputListener {
    private static final Vector2[] positions = new Vector2[Gdx.input.getMaxPointers()];
    private static int numDown = 0;
    private static int curPointer;
    
    // Add to multiplexer first!
    public static InputAdapter getListener() {
        return new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                positions[pointer] = new Vector2(screenX, screenY);
                numDown++;
                curPointer = pointer;
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
        };
    }
    
    public static int getNumDown() {
        return numDown;
    }
    
    public static int getCurPointer() {
        return curPointer;
    }
    
    public static Vector2 getPosition() {
        return getPosition(curPointer);
    }
    
    public static Vector2 getPosition(OrthographicCamera camera) {
        return getPosition(curPointer, camera);
    }
    
    public static Vector2 getPosition(OrthographicCamera camera, Viewport viewport) {
        return getPosition(curPointer, camera, viewport);
    }
    
    public static Vector2 getPosition(int pointer) {
        return positions[pointer];
    }
    
    public static Vector2 getPosition(int pointer, OrthographicCamera camera) {
        Vector3 v3 = camera.unproject(new Vector3(positions[pointer].x, positions[pointer].y, 0));
        return new Vector2(v3.x, v3.y);
    }
    
    public static Vector2 getPosition(int pointer, OrthographicCamera camera, Viewport viewport) {
        Vector3 v3 = camera.unproject(new Vector3(positions[pointer].x, positions[pointer].y, 0), viewport.getScreenX(), viewport.getScreenY(), viewport.getScreenWidth(), viewport.getScreenHeight());
        return new Vector2(v3.x, v3.y);
    }
}
