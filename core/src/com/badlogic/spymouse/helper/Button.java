package com.badlogic.spymouse.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public final class Button {
    private final Rectangle bounds;
    private boolean isHolding;
    private boolean disabled;
    
    public Button(float x, float y, float width, float height) {
        bounds = new Rectangle(x, y, width, height);
    }
    
    public void draw(SpriteBatch batch, Texture texture) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    public boolean isPress() {
        if (disabled) {
            return false;
        }
        
        Point downPos = Listener.getDownPosition(0);
        Point upPos = Listener.getUpPosition(0);
        
        if (downPos != null) {
            downPos.y = Device.HEIGHT - downPos.y;
            isHolding = bounds.contains(downPos);
        }
        if (isHolding && upPos != null) {
            isHolding = false;
            upPos.y = Device.HEIGHT - upPos.y;
            return bounds.contains(upPos);
        }
        return false;
    }
    
    public boolean isDisabled() {
        return disabled;
    }
    
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
