package com.badlogic.spymouse.helper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.screen.menu.MainMenuScreen;

public final class PauseMenu implements Disposable {
    private final MyGame game;
    
    public PauseMenu(MyGame game) {
        this.game = game;
        
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.5f, 0.5f, 0.5f, 0.5f);
        pixmap.fill();
        blurTexture = new Texture(pixmap);
        
        float bgY = (float) Device.HEIGHT / 8;
        float bgHeight = bgY * 6;
        float bgWidth = bgHeight * bgImage.getWidth() / bgImage.getHeight();
        float bgX = (Device.WIDTH - bgWidth) / 2;
        bgBounds = new Rectangle(bgX, bgY, bgWidth, bgHeight);
        
        float innerX = bgX + bgWidth / 10;
        float innerY = bgY + bgHeight / 6;
        float innerWidth = bgWidth - bgWidth / 5;
        float innerHeight = bgHeight - bgHeight / 2;
        bgInner = new Rectangle(innerX, innerY, innerWidth, innerHeight);
        
        float quitHeight = innerHeight / 2.5F;
        float quitWidth = quitHeight * quitImage.getWidth() / quitImage.getHeight();
        float quitX = innerX + innerWidth / 2 - quitWidth / 2;
        quitButton = new Button(quitX, innerY, quitWidth, quitHeight);
        
        float paddingX = innerHeight / 6;
        float trackSize = innerHeight / 2.5F;
        float trackX = innerX + innerWidth / 2 - trackSize - paddingX;
        float trackY = innerY + innerHeight - trackSize;
        trackSwapButton = new Button(trackX, trackY, trackSize, trackSize);
        
        float effectX = trackX + trackSize + paddingX * 2;
        effectSwapButton = new Button(effectX, trackY, trackSize, trackSize);
    }
    
    private final Texture blurTexture;
    private final Texture bgImage = new Texture(Gdx.files.internal("image/pause-menu-bg.png"));
    private final Texture quitImage = new Texture(Gdx.files.internal("image/quit.png"));
    private final Texture trackOnImage = new Texture(Gdx.files.internal("image/track-on.png"));
    private final Texture trackOffImage = new Texture(Gdx.files.internal("image/track-off.png"));
    private final Texture effectOnImage = new Texture(Gdx.files.internal("image/effect-on.png"));
    private final Texture effectOffImage = new Texture(Gdx.files.internal("image/effect-off.png"));
    
    private final Rectangle bgInner;
    private final Rectangle bgBounds;
    private final Button quitButton;
    private final Button trackSwapButton;
    private final Button effectSwapButton;
    
    /**
     * Draw and update
     */
    public void handle() {
        // Draw button
        game.baseBatch.begin();
        game.baseBatch.draw(blurTexture, 0, 0, Device.WIDTH, Device.HEIGHT);
        game.baseBatch.draw(bgImage, bgBounds.x, bgBounds.y, bgBounds.width, bgBounds.height);
        quitButton.draw(game.baseBatch, quitImage);
        if (game.preferences.getBoolean(PrefKeys.TRACK_ON)) {
            game.soundTrack.play();
            trackSwapButton.draw(game.baseBatch, trackOnImage);
        } else {
            game.soundTrack.pause();
            trackSwapButton.draw(game.baseBatch, trackOffImage);
        }
        if (game.preferences.getBoolean(PrefKeys.EFFECT_ON)) {
            effectSwapButton.draw(game.baseBatch, effectOnImage);
        } else {
            effectSwapButton.draw(game.baseBatch, effectOffImage);
        }
        game.baseBatch.end();
        
        game.baseShape.begin(ShapeRenderer.ShapeType.Line);
        game.baseShape.setColor(Color.BLACK);
        bgInner.draw(game.baseShape);
        bgBounds.draw(game.baseShape);
        game.baseShape.end();
        
        // Button listener
        if (quitButton.isPress()) {
            game.clickEffect.play();
            game.setScreen(new MainMenuScreen(game));
        }
        if (trackSwapButton.isPress()) {
            game.clickEffect.play();
            game.preferences.putBoolean(PrefKeys.TRACK_ON, !game.preferences.getBoolean(PrefKeys.TRACK_ON));
            game.preferences.flush();
        }
        if (effectSwapButton.isPress()) {
            game.clickEffect.play();
            game.preferences.putBoolean(PrefKeys.EFFECT_ON, !game.preferences.getBoolean(PrefKeys.EFFECT_ON));
            game.preferences.flush();
        }
    }
    
    @Override
    public void dispose() {
        blurTexture.dispose();
        bgImage.dispose();
        quitImage.dispose();
        trackOnImage.dispose();
        trackOffImage.dispose();
        effectOnImage.dispose();
        effectOffImage.dispose();
    }
}
