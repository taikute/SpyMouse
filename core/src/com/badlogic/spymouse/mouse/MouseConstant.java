package com.badlogic.spymouse.mouse;

public final class MouseConstant {
    public static final float HIT_BOX_WIDTH = 100;
    public static final float HIT_BOX_HEIGHT = 60;
    public static final float SPEED = 200;
    public static final float MIN_DISTANCE = (float) (Math.sqrt(HIT_BOX_WIDTH * HIT_BOX_WIDTH + HIT_BOX_HEIGHT * HIT_BOX_HEIGHT) / 3);
    public static final float MAX_DISTANCE = MIN_DISTANCE * 1.5F;
}
