package com.badlogic.spymouse.helper;

import com.badlogic.gdx.math.Vector3;

public final class Point {
    public float x, y;
    
    public Point() {
    
    }
    
    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public Point(Point p) {
        this(p.x, p.y);
    }
    
    public Point copy() {
        return new Point(x, y);
    }
    
    public float dst(Point p) {
        return dst(p.x, p.y);
    }
    
    public float dst(float x, float y) {
        final float dx = x - this.x;
        final float dy = y - this.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }
    
    public Vector3 toV3() {
        return new Vector3(x, y, 0);
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s]", x, y);
    }
}
