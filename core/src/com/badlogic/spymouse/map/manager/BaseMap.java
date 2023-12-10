package com.badlogic.spymouse.map.manager;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.helper.Polygon;
import com.badlogic.spymouse.helper.Rectangle;

import java.util.ArrayList;

public abstract class BaseMap {
    private final static TextureAtlas itemAtlas = new TextureAtlas("item.txt");
    private final static AtlasRegion wallRegion = itemAtlas.findRegion("wall");
    
    protected int[][] grid;
    
    private final Point mouseStartingPoint;
    protected final Rectangle mapArea = new Rectangle();
    protected final Polygon moveArea = new Polygon();
    //protected final Polygon secretArea = new Polygon();
    protected final ArrayList<ArrayList<Locations>> layers = new ArrayList<>();
    protected final ArrayList<Polygon> hitObjects = new ArrayList<>();
    
    protected BaseMap(Point mouseStartingPoint) {
        this.mouseStartingPoint = mouseStartingPoint;
    }
    
    public void draw(SpriteBatch batch) {
        if (grid != null) {
            for (int i = grid.length - 1; i >= 0; i--) {
                int y = (grid.length - i) * 100;
                for (int j = 0; j < grid[0].length; j++) {
                    int x = (j + 1) * 100;
                    if (grid[i][j] == 1) {
                        batch.draw(wallRegion, x, y, 100, 100);
                    }
                }
            }
            for (int i = 0; i < grid.length; i++) {
                batch.draw(wallRegion, 0, i * 100 + 100, 100, 100);
                batch.draw(wallRegion, grid[0].length * 100 + 100, i * 100 + 100, 100, 100);
            }
            for (int i = 0; i < grid[0].length + 2; i++) {
                batch.draw(wallRegion, i * 100, 0, 100, 100);
                batch.draw(wallRegion, i * 100, grid.length * 100 + 100, 100, 100);
            }
        }
    }
    
    public static BaseMap curMap;
    
    public Polygon getMoveArea() {
        return curMap.moveArea;
    }
    
    public Point getMouseStartingPoint() {
        return curMap.mouseStartingPoint.copy();
    }
    
    public boolean moveAble(Point p) {
        if (!curMap.moveArea.contains(p)) {
            return false;
        }
        if (grid != null) {
            int row = grid.length - (int) (p.y / 100);
            int col = (int) (p.x / 100) - 1;
            return grid[row][col] != 1;
        }
        return true;
    }
    
    public boolean sawAble(Point cat, Point mouse) {
        if (grid != null) {
            for (int i = grid.length - 1; i >= 0; i--) {
                int y = (grid.length - i) * 100;
                for (int j = 0; j < grid[0].length; j++) {
                    int x = (j + 1) * 100;
                    if (grid[i][j] == 1) {
                        if (Intersector.intersectSegmentRectangle(cat.x, cat.y, mouse.x, mouse.y, new com.badlogic.gdx.math.Rectangle(x, y, 100, 100))) {
                            System.out.println("ALOOO");
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    public ArrayList<Polygon> getHitObject() {
        return new ArrayList<>(hitObjects);
    }
}
