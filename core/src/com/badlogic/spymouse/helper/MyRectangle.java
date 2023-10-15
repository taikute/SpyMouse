package com.badlogic.spymouse.helper;

public final class MyRectangle {
    public float x, y, width, height;
    
    public MyRectangle() {
    }
    
    public MyRectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean contains(MyPoint p) {
        return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }
    
    public boolean touch(MyRectangle r) {
        if (y + height < r.y) {
            return false;
        }
        if (y > r.y + r.height) {
            return false;
        }
        if (x + width < r.x) {
            return false;
        }
        if (x > r.x + r.width) {
            return false;
        }
        return true;
    }
    
    public MyPoint center() {
        return new MyPoint(x + width / 2, y + height / 2);
    }
}
