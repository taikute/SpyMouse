package com.badlogic.spymouse.cat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Deque;
import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.map.manager.BaseMap;
import com.badlogic.spymouse.mouse.Mouse;

public final class FastCat extends BaseCat {
    private static final Texture IDLE_GHOST = new Texture(Gdx.files.internal("image/idle-ghost.png"));
    private static final Texture CHASE_GHOST = new Texture(Gdx.files.internal("image/chase-ghost.png"));
    
    private static final float CHASE_SPEED = 180;
    private static final float NORMAL_SPEED = 100;
    private Point targetPoint;
    private final Point[] wayPoints;
    private final Deque<Point> tempPoint = new Deque<>();
    private int currentIndex;
    private boolean isIncreasing = true;
    private final Point startPoint;
    
    public FastCat(MyGame game, Mouse mouse, Point[] wayPoints) {
        super(game, mouse, wayPoints[0].copy());
        this.wayPoints = wayPoints;
        currentIndex = 0;
        targetPoint = wayPoints[currentIndex].copy();
        startPoint = wayPoints[0].copy();
    }
    
    @Override
    public void draw() {
        TextureRegion region;
        if (tempPoint.isEmpty()) {
            region = new TextureRegion(IDLE_GHOST);
        } else {
            region = new TextureRegion(CHASE_GHOST);
            if (position.x > targetPoint.x) {
                region.flip(!region.isFlipY(), false);
            }
        }
        game.combineBatch.begin();
        game.combineBatch.draw(region, position.x - WIDTH / 2, position.y - HEIGHT / 2, WIDTH, HEIGHT);
        game.combineBatch.end();
    }
    
    @Override
    public void update(float delta) {
        updateTargetPoint(delta);
        updatePosition(delta);
    }
    
    private boolean isDetection() {
        return canDetection() && BaseMap.curMap.sawAble(position, mouse.getPosition());
    }
    
    private boolean canDetection() {
        Point mousePos = mouse.getPosition();
        
        // Game over
        if (position.dst(mousePos) < 50) {
            position.x = startPoint.x;
            position.y = startPoint.y;
            tempPoint.clear();
            targetPoint = wayPoints[0];
            isIncreasing = true;
            mouse.setPosition(BaseMap.curMap.getMouseStartingPoint());
            return false;
        }
        
        if (targetPoint.x == position.x) {
            if (targetPoint.y > position.y) {
                return mousePos.y > position.y;
            }
            return mousePos.y < position.y;
        }
        if (targetPoint.y == position.y) {
            if (targetPoint.x > position.x) {
                return mousePos.x > position.x;
            }
            return mousePos.x < position.x;
        }
        
        float mCat = (targetPoint.y - position.y) / (targetPoint.x - position.x);
        
        float mVision = -1 / mCat;
        float bVision = position.y - mVision * position.x;
        
        float yTarget = targetPoint.x * mVision + bVision;
        float yMouse = mouse.getPosition().x * mVision + bVision;
        
        if (targetPoint.y > yTarget) {
            return mousePos.y > yMouse;
        }
        return mousePos.y < yMouse;
    }
    
    private void updateTargetPoint(float delta) {
        if (isDetection()) {
            targetPoint = mouse.getPosition();
        } else {
            if (position.dst(targetPoint) < CHASE_SPEED * delta) {
                if (tempPoint.notEmpty()) {
                    targetPoint = tempPoint.removeLast();
                } else {
                    if ((isIncreasing && currentIndex == wayPoints.length - 1) || (!isIncreasing && currentIndex == 0)) {
                        isIncreasing = !isIncreasing;
                    }
                    currentIndex += (isIncreasing ? 1 : -1);
                    targetPoint = wayPoints[currentIndex].copy();
                }
            }
        }
    }
    
    private void updatePosition(float delta) {
        float distance = position.dst(targetPoint);
        float duration;
        if (tempPoint.notEmpty()) {
            duration = distance / CHASE_SPEED;
        } else {
            duration = distance / NORMAL_SPEED;
        }
        
        position.x += (targetPoint.x - position.x) / duration * delta;
        position.y += (targetPoint.y - position.y) / duration * delta;
        if (isDetection()) {
            tempPoint.addLast(position.copy());
        }
    }
    
    @Override
    public void dispose() {
    
    }
}