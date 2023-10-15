package com.badlogic.spymouse.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public final class InputListener {
    private static final MyPoint[] positions = new MyPoint[Gdx.input.getMaxPointers()];
    private static int numDown = 0;
    private static int curPointer = -1;
    
    public static InputAdapter getAdapter() {
        return new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                positions[pointer] = new MyPoint(screenX, screenY);
                numDown++;
                curPointer = pointer;
                return false;
            }
            
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                positions[pointer] = null;
                numDown--;
                curPointer = -1;
                return false;
            }
            
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                positions[pointer] = new MyPoint(screenX, screenY);
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
    
    public static MyPoint getPosition() {
        return getPosition(curPointer);
    }
    
    public static MyPoint getPosition(int pointer) {
        return positions[pointer];
    }
}
