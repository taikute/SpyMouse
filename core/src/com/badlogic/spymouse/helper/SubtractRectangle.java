package com.badlogic.spymouse.helper;

import java.util.ArrayList;

public final class SubtractRectangle {
    private final ArrayList<Rectangle> list = new ArrayList<>();
    
    public SubtractRectangle(float x, float y, float width, float height) {
        this(new Rectangle(x, y, width, height));
    }
    
    public SubtractRectangle(Rectangle r) {
        list.add(r);
    }
    
    public void subtract(Rectangle rectToSubtract) {
        ArrayList<Rectangle> rToAdd = new ArrayList<>();
        ArrayList<Rectangle> rToRemove = new ArrayList<>();
        
        for (Rectangle r : list) {
            if (r.intersects(rectToSubtract)) {
                Rectangle intersection = r.findIntersection(rectToSubtract);
                if (intersection != null) {
                    rToAdd.addAll(r.subtract(intersection));
                    rToRemove.add(r);
                }
            }
        }
        list.removeAll(rToRemove);
        list.addAll(rToAdd);
    }
    
    public ArrayList<Rectangle> getList() {
        return list;
    }
    
    public void merge() {
        /*ArrayList<Rectangle> rToRemove = new ArrayList<>();
        for (Rectangle r1 : list) {
            for (Rectangle r2 : list) {
                if (r2.inside(r1)){
                    rToRemove.add(r2);
                    break;
                }
            }
        }
        list.removeAll(rToRemove);*/
    }
}

