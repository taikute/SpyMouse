package com.badlogic.spymouse.mouse;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

final class MouseAnimation {
    private static final int NUM_ROW = 5;
    private static final int NUM_COL = 2;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 150;
    private static final Texture mouseTexture = new Texture("mouse_animation.png");
    private static final TextureRegion[][] mouseAni = new TextureRegion[NUM_ROW][];
    private static int curRow = 0;
    private static int curCol = 0;
    private static TextureRegion curRegion;
    private static boolean flipX = false;

    static void init() {
        for (int i = 0; i < NUM_ROW; i++) {
            mouseAni[i] = new TextureRegion[NUM_COL];
            for (int j = 0; j < NUM_COL; j++) {
                mouseAni[i][j] = new TextureRegion(mouseTexture, j * WIDTH, i * HEIGHT, WIDTH, HEIGHT);
            }
        }
        curRegion = mouseAni[1][1];
    }

    static void setAnimation(int animationRow) {
        curRow = animationRow;
    }

    static void setFlipX(boolean flipX) {
        MouseAnimation.flipX = flipX;
    }

    static TextureRegion getRegion() {
        TextureRegion r = new TextureRegion(curRegion);
        r.flip(flipX, false);
        return r;
    }

    static void nextRegion() {
        curCol++;
        if (curCol == NUM_COL) {
            curCol %= 2;
        }
        curRegion = mouseAni[curRow][curCol];
    }

    static void dispose() {
        mouseTexture.dispose();
    }
}
