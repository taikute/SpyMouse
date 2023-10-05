package com.badlogic.spymouse.mouse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.spymouse.Device;

public class Mouse {
    private final Texture mouseTexture = new Texture("Spy_Mouse_icon.png");
    private float stateTime = 0.5F;
    private final Rectangle bounds;
    public Queue<Vector2> movePoints = new Queue<>();

    public Mouse(float x, float y) {
        bounds = new Rectangle(x, y, 200, 150);
        MouseAnimation.init();
    }

    public void update(float delta, SpriteBatch batch) {
        float frameDuration = 0.3F;
        if (movePoints.notEmpty()) {
            if (stateTime >= frameDuration) {
                MouseAnimation.nextRegion();
                stateTime = 0;
            } else {
                stateTime += delta;
            }

            float deltaX = movePoints.first().x - getCenter().x;
            float deltaY = movePoints.first().y - getCenter().y;
            int degree = getDegree(deltaX, deltaY);
            MouseAnimation.setFlipX(degree <= 0);
            MouseAnimation.setAnimation(getAnimationIndex(degree));

            float distance = getCenter().dst(movePoints.first());
            int speed = Device.HEIGHT / 5;
            float duration = distance / speed;
            bounds.x += (movePoints.first().x - bounds.x - bounds.width / 2) / duration * delta;
            bounds.y += (movePoints.first().y - bounds.y - bounds.height / 2) / duration * delta;
            if (distance < speed * delta) {
                movePoints.removeFirst();
            }
        } else {
            stateTime = frameDuration;
        }

        draw(batch);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public Vector2 getCenter() {
        return new Vector2(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
    }

    public Rectangle getHitBox() {
        return new Rectangle(bounds.x + bounds.width / 4, bounds.y + bounds.height / 16, bounds.width / 2, bounds.height / 2);
    }

    private void draw(SpriteBatch batch) {
        batch.begin();
        batch.draw(MouseAnimation.getRegion(), bounds.x, bounds.y, bounds.width, bounds.height);
        if (movePoints.notEmpty()) {
            for (Vector2 point : movePoints) {
                batch.draw(mouseTexture, point.x, point.y, 30, 30);
            }
        }
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

    public void dispose() {
        MouseAnimation.dispose();
    }
}
