package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.spymouse.mouse.Mouse;

final class MoveAdapter extends InputAdapter {
    private boolean isDragging = false;
    private final float minDistance;
    private final Mouse mouse;
    private final InputListener listener;

    public MoveAdapter(Mouse mouse, InputListener listener) {
        this.mouse = mouse;
        this.listener = listener;
        minDistance = mouse.getCenter().dst(mouse.getBounds().x, mouse.getBounds().y);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (listener.getNumDown() == 1) {
            if (pointer == 0) {
                Vector2 point = listener.getPosition(0);
                if (mouse.getBounds().contains(point)) {
                    isDragging = true;
                    mouse.movePoints.clear();
                } else {
                    isDragging = false;
                }
            } else {
                isDragging = false;
            }
        } else {
            isDragging = false;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (listener.getNumDown() != 1) {
            isDragging = false;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (isDragging) {
            Vector2 point = listener.getPosition(0);
            float distance;
            if (mouse.movePoints.isEmpty()) {
                distance = mouse.getCenter().dst(point);
            } else {
                distance = mouse.movePoints.last().dst(point);
            }
            if (distance > minDistance) {
                mouse.movePoints.addLast(point);
            }
        }
        return false;
    }
}
