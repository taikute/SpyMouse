package com.badlogic.spymouse.map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapManager {
    int mapWidth;
    int mapHeight;
    
    public MapManager(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }
    
    public int getMapWidth() {
        return mapWidth;
    }
    
    public int getMapHeight() {
        return mapHeight;
    }
    
    public void draw(SpriteBatch batch) {
    
    }
}
