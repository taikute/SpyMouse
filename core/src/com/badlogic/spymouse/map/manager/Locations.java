package com.badlogic.spymouse.map.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.spymouse.helper.Rectangle;

import java.util.ArrayList;

public final class Locations {
    private final ArrayList<Rectangle> locations = new ArrayList<>();
    private final AtlasRegion region;
    private final BaseMap baseMap;
    
    public Locations(AtlasRegion region, BaseMap baseMap) {
        this.region = region;
        this.baseMap = baseMap;
    }
    
    public void add(float x, float y, float width, float height) {
        Rectangle addLocation = new Rectangle(x, y, width, height);
        baseMap.mapArea.combineWith(addLocation);
        
        for (Rectangle location : locations) {
            if (location.intersects(addLocation)) {
                throw new RuntimeException(location + " intersects " + addLocation);
            }
        }
        locations.add(addLocation);
    }
    
    public void add(Rectangle addLocation) {
        add(addLocation.x, addLocation.y, addLocation.width, addLocation.height);
    }
    
    public ArrayList<Rectangle> getLocations() {
        return locations;
    }
    
    public AtlasRegion getRegion() {
        return region;
    }
}
