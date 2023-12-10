package com.badlogic.spymouse.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

public final class Listener {
    private static final int maxPointers = Gdx.input.getMaxPointers();
    
    private static final Point[] downPositions = new Point[maxPointers];
    private static final Point[] upPositions = new Point[maxPointers];
    private static final Point[] draggedPositions = new Point[maxPointers];
    
    private static int numDown = 0;
    
    public static int getNumDown() {
        return numDown;
    }
    
    public static Point getDraggedPosition(int pointer) {
        return draggedPositions[pointer] == null ? null : draggedPositions[pointer].copy();
    }
    
    public static Point getUpPosition(int pointer) {
        return upPositions[pointer] == null ? null : upPositions[pointer].copy();
    }
    
    public static Point getDownPosition(int pointer) {
        return downPositions[pointer] == null ? null : downPositions[pointer].copy();
    }
    
    public static InputAdapter getAdapter() {
        return new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                downPositions[pointer] = new Point(screenX, screenY);
                upPositions[pointer] = null;
                draggedPositions[pointer] = new Point(screenX, screenY);
                numDown++;
                return false;
            }
            
            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                downPositions[pointer] = null;
                upPositions[pointer] = new Point(screenX, screenY);
                draggedPositions[pointer] = new Point(screenX, screenY);
                numDown--;
                return false;
            }
            
            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                draggedPositions[pointer] = new Point(screenX, screenY);
                return false;
            }
        };
    }
}
