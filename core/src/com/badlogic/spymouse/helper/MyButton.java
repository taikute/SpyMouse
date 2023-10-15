package com.badlogic.spymouse.helper;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public final class MyButton {
    public final MyRectangle bounds;
    
    public MyButton() {
        bounds = new MyRectangle();
    }
    
    public MyButton(float x, float y, float width, float height) {
        bounds = new MyRectangle(x, y, width, height);
    }
    
    public void draw(SpriteBatch batch, TextureRegion region) {
        batch.draw(region, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    public void draw(SpriteBatch batch, Texture texture) {
        batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
    }
    
    private boolean contains(MyPoint p) {
        return p.x >= bounds.x && p.x <= bounds.x + bounds.width && p.y >= bounds.y && p.y <= bounds.y + bounds.height;
    }
    
    public boolean isPress() {
        MyPoint point = InputListener.getPosition(0);
        if (point != null && InputListener.getNumDown() == 1) {
            point.y = Device.HEIGHT - bounds.y;
            return contains(point);
        }
        return false;
    }
}
