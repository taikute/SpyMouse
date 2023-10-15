package com.badlogic.spymouse.mouse;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.spymouse.helper.*;

import java.util.ArrayList;

public class Mouse {
    public enum Status {
        STAND, MOVE
    }
    
    public Status status = Status.STAND;
    
    private final MyRectangle hitBox;
    private final Texture moveStepTexture = new Texture("Spy_Mouse_icon.png");
    
    private final TextureAtlas mouseAtlas = new TextureAtlas("atlas_test.txt");
    private final ArrayList<Animation<AtlasRegion>> animations = new ArrayList<>();
    private TextureRegion currentRegion = new TextureRegion(moveStepTexture);
    
    private final MyQueue<MyPoint> movePoints = new MyQueue<>();
    private final OrthographicCamera camera;
    
    private final float SPEED = Device.HEIGHT / 5F;
    private final float MIN_DISTANCE;
    
    private float stateTime = 0;
    private boolean isDragging = false;
    
    public Mouse(float x, float y, OrthographicCamera camera) {
        hitBox = new MyRectangle(x, y, 100, 60);
        this.camera = camera;
        
        MIN_DISTANCE = hitBox.center().dst(hitBox.x, hitBox.y);
        
        // Fill animations
        for (int i = 0; i < 5; i++) {
            Animation<AtlasRegion> animation = new Animation<>(0.3F, mouseAtlas.findRegions("mouse_" + i));
            animation.setPlayMode(Animation.PlayMode.LOOP);
            animations.add(i, animation);
        }
    }
    
    // Call in render()
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
            float duration = distance / SPEED;
            hitBox.x += (movePoints.first().x - hitBox.x - hitBox.width / 2) / duration * delta;
            hitBox.y += (movePoints.first().y - hitBox.y - hitBox.height / 2) / duration * delta;
            if (distance < SPEED * delta) {
                movePoints.remove();
            }
        } else {
            // STAND
            currentRegion = new TextureRegion(moveStepTexture);
        }
    }
    
    public MyRectangle getHitBox() {
        return hitBox;
    }
    
    public void draw(SpriteBatch batch) {
        for (MyPoint point : movePoints.toList()) {
            batch.draw(moveStepTexture, point.x - 7, point.y - 7, 15, 15);
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
        int degree = (int) Math.round(Math.toDegrees(Math.atan(x / y)));
        if (y > 0) {
            return degree;
        } else {
            if (x > 0) {
                return 180 + degree;
            } else {
                return degree - 180;
            }
        }
    }
    
    private void updateMovePoints() {
        if (InputListener.getNumDown() == 1 && InputListener.getCurPointer() == 0) {
            //Unproject
            Vector3 v3 = camera.unproject(InputListener.getPosition().toV3());
            MyPoint fingerPosition = new MyPoint(v3.x, v3.y);
            
            if (isDragging) {
                MyPoint lastPoint;
                if (movePoints.isEmpty()) {
                    lastPoint = hitBox.center();
                } else {
                    lastPoint = movePoints.last();
                }
                float distance = lastPoint.dst(fingerPosition);
                if (distance > MIN_DISTANCE) {
                    movePoints.add(fingerPosition);
                }
            } else if (hitBox.contains(fingerPosition)) {
                movePoints.clear();
                isDragging = true;
            }
        } else isDragging = false;
    }
    
    public void dispose() {
        moveStepTexture.dispose();
        mouseAtlas.dispose();
    }
}
