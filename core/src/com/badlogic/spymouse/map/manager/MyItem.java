package com.badlogic.spymouse.map.manager;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;

public final class MyItem {
    private final static TextureAtlas itemAtlas = new TextureAtlas("item.txt");
    
    public final static MyItem WALL = new MyItem("wall");
    public final static MyItem FLOOR = new MyItem("floor");
    
    private final AtlasRegion region;
    
    private MyItem(String name) {
        region = itemAtlas.findRegion(name);
        if (region == null) {
            throw new NullPointerException(String.format("Can't find region name '%s'", name));
        }
    }
    
    public AtlasRegion getRegion() {
        return region;
    }
}
