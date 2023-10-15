package com.badlogic.spymouse.helper;

import com.badlogic.gdx.math.Vector3;

public final class MyPoint {
    public float x, y;
    
    public MyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }
    
    public float dst(MyPoint p) {
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
}
