package com.badlogic.spymouse.map;

import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.map.manager.BaseMap;
import com.badlogic.spymouse.map.manager.Locations;
import com.badlogic.spymouse.map.manager.MyItem;

public final class Map1 extends BaseMap {
    public Map1() {
        super(new Point(150, 150));
        mapArea.width = 2000;
        mapArea.height = 1000;
        Locations walls = new Locations(MyItem.WALL.getRegion(), this);
        Locations floors = new Locations(MyItem.FLOOR.getRegion(), this);
        
        grid = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 0},
                {0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 1, 0},
                {1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 1, 0, 0, 1, 1, 1, 0},
                {1, 1, 1, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 1, 0, 1, 0},
                {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0},
        };
        
        moveArea.add(150, 130);
        moveArea.add(grid[0].length * 100 + 50, 130);
        moveArea.add(grid[0].length * 100 + 50, grid.length * 100 + 70);
        moveArea.add(150, grid.length * 100 + 70);
    }
}
