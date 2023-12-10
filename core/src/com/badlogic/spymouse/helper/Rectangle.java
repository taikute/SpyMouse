package com.badlogic.spymouse.helper;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;

public final class Rectangle {
    public float x, y, width, height;
    
    public Rectangle() {
    }
    
    public Rectangle(Point p, float width, float height) {
        this(p.x, p.y, width, height);
    }
    
    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    public boolean contains(Point p) {
        return p.x >= x && p.x <= x + width && p.y >= y && p.y <= y + height;
    }
    
    public boolean intersects(Rectangle r) {
        return (x < r.x + r.width) && (x + width > r.x) && (y < r.y + r.height) && (y + height > r.y);
    }
    
    public Rectangle findIntersection(Rectangle r) {
        float x1 = Math.max(this.x, r.x);
        float y1 = Math.max(this.y, r.y);
        float x2 = Math.min(this.x + this.width, r.x + r.width);
        float y2 = Math.min(this.y + this.height, r.y + r.height);
        
        if (x1 < x2 && y1 < y2) {
            return new Rectangle(x1, y1, x2 - x1, y2 - y1);
        } else {
            return null;
        }
    }
    
    public boolean inside(Rectangle r) {
        return (x >= r.x) && (y >= r.y) && (x + width <= r.x + r.width) && (y + height <= r.y + r.height);
    }
    
    public boolean outside(Rectangle r) {
        return !inside(r);
    }
    
    public Point center() {
        return new Point(x + width / 2, y + height / 2);
    }
    
    @Override
    public String toString() {
        return String.format("[%s, %s, %s, %s]", x, y, width, height);
    }
    
    public void draw(ShapeRenderer shape) {
        shape.rect(x, y, width, height);
    }
    
    public Rectangle copy() {
        return new Rectangle(x, y, width, height);
    }
    
    public Rectangle combineWith(Rectangle r) {
        float combinedX = Math.min(this.x, r.x);
        float combinedY = Math.min(this.y, r.y);
        float combinedWidth = Math.max(this.x + this.width, r.x + r.width) - combinedX;
        float combinedHeight = Math.max(this.y + this.height, r.y + r.height) - combinedY;
        return new Rectangle(combinedX, combinedY, combinedWidth, combinedHeight);
    }
    
    public ArrayList<Rectangle> subtract(Rectangle r) {
        ArrayList<Rectangle> rectangles = new ArrayList<>();
        if (!intersects(r)) {
            rectangles.add(r);
        } else {
            if (y < r.y) {
                rectangles.add(new Rectangle(x, y, width, r.y - y));
            }
            if (x < r.x) {
                rectangles.add(new Rectangle(x, Math.max(y, r.y), r.x - x, Math.min(y + height, r.y + r.height) - Math.max(y, r.y)));
            }
            if (x + width > r.x + r.width) {
                rectangles.add(new Rectangle(r.x + r.width, Math.max(y, r.y), (x + width) - (r.x + r.width), Math.min(y + height, r.y + r.height) - Math.max(y, r.y)));
            }
            if (y + height > r.y + r.height) {
                rectangles.add(new Rectangle(x, Math.min(y + height, r.y + r.height), width, (y + height) - (r.y + r.height)));
            }
        }
        return rectangles;
    }
    
    public Polygon toPolygon() {
        return new Polygon();
    }
}