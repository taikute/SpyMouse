package com.badlogic.spymouse.actor;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class Mouse {
    Rectangle hitBox;
    Rectangle bounds;
    Texture mouseFrames;
    TextureRegion[][] frameRegions;

    public Mouse() {
        mouseFrames = new Texture("Spy_Mouse_icon.png");
        frameRegions = new TextureRegion[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                frameRegions[i][j] = new TextureRegion(mouseFrames, j * 72, i * 72, 72, 72);
            }
        }
        bounds = new Rectangle();
        bounds.width = 300;
        bounds.height = 300;
    }

    public void update(float delta) {

    }

    public void draw(SpriteBatch batch) {
        int count = 0;
        batch.begin();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                batch.draw(frameRegions[i][j], count * 300F, bounds.y, bounds.width, bounds.height);
                count++;
            }
        }
        batch.end();
    }
}
