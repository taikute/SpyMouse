package com.badlogic.spymouse.mouse;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.Device;
import com.badlogic.spymouse.InputListener;

public class Mouse {
    private final Texture mouseTexture = new Texture("Spy_Mouse_icon.png");
    private final float frameDuration = 0.3F;
    
    private final Rectangle hitBox;
    private final Queue<Vector2> movePoints = new Queue<>();
    private final OrthographicCamera camera;
    private final Viewport viewport;
    
    private float stateTime;
    private boolean isDragging = false;
    
    public Mouse(float x, float y, OrthographicCamera camera, Viewport viewport) {
        hitBox = new Rectangle(x + 50, y + 10, 100, 60);
        MouseAnimation.init();
        stateTime = frameDuration;
        
        this.camera = camera;
        this.viewport = viewport;
    }
    
    // Call in render()
    public void update(float delta, SpriteBatch batch) {
        updateMovePoints();
        if (movePoints.notEmpty()) {
            changeFrame(delta);
            setAnimation();
            updateMousePosition(delta, Device.HEIGHT / 5);
        } else {
            resetStateTime();
        }
        draw(batch);
    }
    
    public Rectangle getHitBox() {
        return hitBox;
    }
    
    public Vector2 getCenter(Rectangle rectangle) {
        return new Vector2(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
    }
    
    private void draw(SpriteBatch batch) {
        batch.begin();
        if (movePoints.notEmpty()) {
            for (Vector2 point : movePoints) {
                batch.draw(mouseTexture, point.x - 7, point.y - 7, 15, 15);
            }
        }
        batch.draw(MouseAnimation.getRegion(), hitBox.x - 50, hitBox.y - 10, 200, 150);
        batch.end();
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
    
    private void setAnimation() {
        float deltaX = movePoints.first().x - getCenter(hitBox).x;
        float deltaY = movePoints.first().y - getCenter(hitBox).y;
        int degree = getDegree(deltaX, deltaY);
        MouseAnimation.setFlipX(degree <= 0);
        MouseAnimation.setAnimation(getAnimationIndex(degree));
    }
    
    private void changeFrame(float delta) {
        if (stateTime >= frameDuration) {
            MouseAnimation.nextRegion();
            stateTime = 0;
        } else {
            stateTime += delta;
        }
    }
    
    private void resetStateTime() {
        stateTime = frameDuration;
    }
    
    private void updateMousePosition(float delta, int speed) {
        float distance = getCenter(hitBox).dst(movePoints.first());
        float duration = distance / speed;
        hitBox.x += (movePoints.first().x - hitBox.x - hitBox.width / 2) / duration * delta;
        hitBox.y += (movePoints.first().y - hitBox.y - hitBox.height / 2) / duration * delta;
        if (distance < speed * delta) {
            movePoints.removeFirst();
        }
    }
    
    private void updateMovePoints() {
        if (InputListener.getNumDown() == 1 && InputListener.getCurPointer() == 0) {
            Vector2 fingerPosition = InputListener.getPosition(0, camera, viewport);
            if (hitBox.contains(fingerPosition)) {
                if (!isDragging) {
                    movePoints.clear();
                    isDragging = true;
                }
            }
            if (isDragging) {
                float distance;
                if (movePoints.isEmpty()) {
                    distance = getCenter(hitBox).dst(fingerPosition);
                } else {
                    distance = movePoints.last().dst(fingerPosition);
                }
                if (distance > getCenter(hitBox).dst(hitBox.x, hitBox.y)) {
                    movePoints.addLast(fingerPosition);
                }
            }
        } else isDragging = false;
    }
    
    
    
    
    public void dispose() {
        MouseAnimation.dispose();
    }
}
