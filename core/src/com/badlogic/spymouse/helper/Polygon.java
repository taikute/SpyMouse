package com.badlogic.spymouse.helper;

import java.util.ArrayList;
import java.util.Collection;

public final class Polygon {
    public final ArrayList<Point> vertices = new ArrayList<>();
    
    public Polygon() {
    
    }
    
    public Polygon(Collection<Point> c) {
        vertices.addAll(c);
    }
    
    public Polygon(Rectangle r) {
        vertices.add(new Point(r.x, r.y));
        vertices.add(new Point(r.x + r.width, r.y));
        vertices.add(new Point(r.x + r.width, r.y + r.height));
        vertices.add(new Point(r.x, r.y + r.height));
    }
    
    public boolean contains(float x, float y) {
        int intersects = 0;
        
        for (int i = 0; i < vertices.size(); i++) {
            float x1 = vertices.get(i).x;
            float y1 = vertices.get(i).y;
            float x2 = vertices.get((i + 1) % vertices.size()).x;
            float y2 = vertices.get((i + 1) % vertices.size()).y;
            if (((y1 <= y && y < y2) || (y2 <= y && y < y1)) && x < ((x2 - x1) / (y2 - y1) * (y - y1) + x1)) {
                intersects++;
            }
        }
        return intersects % 2 == 1;
    }
    
    public boolean contains(Point point) {
        return contains(point.x, point.y);
    }
    
    public void add(float x, float y) {
        add(new Point(x, y));
    }
    
    public void add(Point p) {
        vertices.add(p);
    }
    
    public float[] toArray() {
        float[] arr = new float[vertices.size() * 2];
        for (int i = 0; i < vertices.size(); i++) {
            arr[i * 2] = vertices.get(i).x;
            arr[i * 2 + 1] = vertices.get(i).y;
        }
        return arr;
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        
        for (Point p : vertices) {
            s.append(p).append(" ");
        }
        return s.toString();
    }
}
