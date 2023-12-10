package com.badlogic.spymouse.screen.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Button;
import com.badlogic.spymouse.helper.CameraManager;
import com.badlogic.spymouse.helper.Device;
import com.badlogic.spymouse.helper.PauseMenu;
import com.badlogic.spymouse.map.manager.BaseMap;
import com.badlogic.spymouse.mouse.Mouse;
import com.badlogic.spymouse.screen.BaseScreen;

public abstract class BaseStage extends BaseScreen {
    protected final MyGame game;
    
    private boolean isPause = false;
    private final Texture pauseImage = new Texture(Gdx.files.internal("image/pause.png"));
    private final Button pauseButton = new Button(Device.WIDTH - 120, Device.HEIGHT - 150, 100F * pauseImage.getWidth() / pauseImage.getHeight(), 100);
    private final PauseMenu pauseMenu;
    
    private final OrthographicCamera camera;
    private final CameraManager cameraManager;
    protected final Mouse mouse;
    
    protected BaseStage(MyGame game, BaseMap map) {
        this.game = game;
        BaseMap.curMap = map;
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        cameraManager = new CameraManager(camera);
        mouse = new Mouse(BaseMap.curMap.getMouseStartingPoint(), camera);
        pauseMenu = new PauseMenu(game);
    }
    
    /**
     * Draw method here
     */
    public abstract void draw();
    
    /**
     * Update method here
     */
    public abstract void update(float delta);
    
    /**
     * Dispose method here
     */
    public abstract void stageDispose();
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        game.combineBatch.setProjectionMatrix(camera.combined);
        game.combineShape.setProjectionMatrix(camera.combined);
        game.combineBatch.begin();
        BaseMap.curMap.draw(game.combineBatch);
        mouse.draw(game.combineBatch);
        game.combineBatch.end();
        draw();
        if (isPause) {
            pauseMenu.handle();
        } else {
            update(delta);
            mouse.update(delta);
            cameraManager.zoomApply();
            camera.position.set(mouse.getHitBox().center().x, mouse.getHitBox().center().y, 0);
            camera.update();
        }
        game.baseBatch.begin();
        pauseButton.draw(game.baseBatch, pauseImage);
        game.baseBatch.end();
        if (pauseButton.isPress()) {
            isPause = !isPause;
        }
    }
    
    @Override
    public void dispose() {
        pauseImage.dispose();
        pauseMenu.dispose();
        stageDispose();
    }
}
