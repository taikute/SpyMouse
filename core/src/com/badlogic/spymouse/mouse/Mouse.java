package com.badlogic.spymouse.mouse;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.spymouse.helper.Deque;
import com.badlogic.spymouse.helper.Listener;
import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.helper.Rectangle;
import com.badlogic.spymouse.map.manager.BaseMap;

import java.util.ArrayList;

public final class Mouse implements Disposable {
    public enum Status {
        STAND, MOVE
    }
    
    private final Rectangle hitBox;
    private final Texture moveStepImage = new Texture("image/spy-mouse-icon.png");
    private final TextureAtlas mouseAtlas = new TextureAtlas("mouse_animation.txt");
    private final ArrayList<Animation<AtlasRegion>> animations = new ArrayList<>();
    private final Deque<Point> movePoints = new Deque<>();
    private final OrthographicCamera camera;
    
    public Status status = Status.STAND;
    private TextureRegion currentRegion;
    private float stateTime = 0;
    private boolean isDragging = false;
    
    public Mouse(Point p, OrthographicCamera camera) {
        hitBox = new Rectangle(p.x, p.y, MouseConstant.HIT_BOX_WIDTH, MouseConstant.HIT_BOX_HEIGHT);
        this.camera = camera;
        
        // Fill animations
        for (int i = 0; i < 5; i++) {
            Animation<AtlasRegion> animation = new Animation<>(0.3F, mouseAtlas.findRegions("mouse_" + i));
            animation.setPlayMode(Animation.PlayMode.LOOP);
            animations.add(i, animation);
        }
        currentRegion = animations.get(0).getKeyFrame(stateTime);
    }
    
    public void setPosition(float x, float y) {
        hitBox.x = x;
        hitBox.y = y;
        movePoints.clear();
    }
    
    public void setPosition(Point p) {
        setPosition(p.x, p.y);
    }
    
    public Point getPosition() {
        return hitBox.center();
    }
    
    public Rectangle getHitBox() {
        return hitBox.copy();
    }
    
    public void update(float delta) {
        updateMovePoints();
        // Set status
        if (movePoints.isEmpty()) {
            status = Status.STAND;
        } else {
            status = Status.MOVE;
        }
        
        if (status == Status.MOVE) {
            // Calculator degree
            float deltaX = movePoints.first().x - hitBox.center().x;
            float deltaY = movePoints.first().y - hitBox.center().y;
            int degree = getDegree(deltaX, deltaY);
            
            // Get region
            stateTime += delta;
            currentRegion = animations.get(getAnimationIndex(degree)).getKeyFrame(stateTime);
            
            // Flip region
            if (degree < 0) {
                currentRegion.flip(!currentRegion.isFlipX(), false);
            } else {
                currentRegion.flip(currentRegion.isFlipX(), false);
            }
            
            // Update mouse position
            float distance = hitBox.center().dst(movePoints.first());
            float duration = distance / MouseConstant.SPEED;
            hitBox.x += (movePoints.first().x - hitBox.x - hitBox.width / 2) / duration * delta;
            hitBox.y += (movePoints.first().y - hitBox.y - hitBox.height / 2) / duration * delta;
            if (distance < MouseConstant.SPEED * delta) {
                movePoints.removeFirst();
            }
        } else {
            // Stand Animation
            // currentRegion = new TextureRegion(moveStepTexture);
        }
    }
    
    public void draw(SpriteBatch batch) {
        Deque<Point> copyMovePoints = movePoints.copy();
        while (copyMovePoints.notEmpty()) {
            Point point = copyMovePoints.removeFirst();
            batch.draw(moveStepImage, point.x - 7, point.y - 7, 15, 15);
        }
        batch.draw(currentRegion, hitBox.x - 50, hitBox.y - 10, 200, 150);
    }
    
    private int getAnimationIndex(int degree) {
        int absDegree = Math.abs(degree);
        if (absDegree <= 18) {
            return 0;
        }
        if (absDegree <= 72) {
            return 1;
        }
        if (absDegree <= 108) {
            return 2;
        }
        if (absDegree <= 162) {
            return 3;
        }
        return 4;
    }
    
    private int getDegree(float x, float y) {
        if (y == 0) {
            if (x > 0) {
                return 90;
            }
            return -90;
        }
        int degree = (int) Math.round(Math.toDegrees(Math.atan(x / y)));
        if (y > 0) {
            return degree;
        }
        if (x > 0) {
            return 180 + degree;
        }
        return degree - 180;
    }
    
    private void updateMovePoints() {
        Point fingerPosition = Listener.getDraggedPosition(0);
        if (Listener.getNumDown() == 1 && fingerPosition != null) {
            Vector3 v3 = camera.unproject(fingerPosition.toV3());
            fingerPosition = new Point(v3.x, v3.y);
            
            if (isDragging) {
                Point lastPoint = movePoints.isEmpty() ? hitBox.center() : movePoints.last();
                
                float distance = lastPoint.dst(fingerPosition);
                if (distance < MouseConstant.MIN_DISTANCE) {
                    return;
                }
                while (distance > MouseConstant.MAX_DISTANCE) {
                    fingerPosition.x -= (fingerPosition.x - lastPoint.x) / 10;
                    fingerPosition.y -= (fingerPosition.y - lastPoint.y) / 10;
                    distance = lastPoint.dst(fingerPosition);
                }
                
                if (!BaseMap.curMap.moveAble(fingerPosition)) {
                    float tempX = fingerPosition.x;
                    fingerPosition.x = lastPoint.x;
                    if (!BaseMap.curMap.moveAble(fingerPosition)) {
                        fingerPosition.x = tempX;
                        fingerPosition.y = lastPoint.y;
                        if (!BaseMap.curMap.moveAble(fingerPosition)) {
                            return;
                        }
                    }
                }
                
                distance = lastPoint.dst(fingerPosition);
                if (distance < MouseConstant.MIN_DISTANCE) {
                    return;
                }
                
                if (BaseMap.curMap.moveAble(fingerPosition)) {
                    movePoints.addLast(fingerPosition);
                }
            } else if (hitBox.contains(fingerPosition)) {
                movePoints.clear();
                isDragging = true;
            }
        } else isDragging = false;
    }
    
    @Override
    public void dispose() {
        moveStepImage.dispose();
        mouseAtlas.dispose();
    }
}
