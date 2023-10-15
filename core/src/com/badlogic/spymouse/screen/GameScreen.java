package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.CameraManager;
import com.badlogic.spymouse.helper.Device;
import com.badlogic.spymouse.helper.MyRectangle;
import com.badlogic.spymouse.mouse.Mouse;

final class GameScreen implements Screen {
    private final MyGame game;
    private final OrthographicCamera camera;
    private final Mouse mouse;
    private final CameraManager cameraManager;
    
    public GameScreen(MyGame game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Device.WIDTH, Device.HEIGHT);
        mouse = new Mouse(300, 300, camera);
        
        cameraManager = new CameraManager(camera);
    }
    
    @Override
    public void show() {
        System.out.println("On GameScreen");
    }
    
    int count = 0;
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        game.batch.setProjectionMatrix(camera.combined);
        game.shape.setProjectionMatrix(camera.combined);
        
        //Update Mouse
        mouse.update(delta);
        
        game.batch.begin();
        mouse.draw(game.batch);
        game.batch.end();
        
        game.shape.begin(ShapeRenderer.ShapeType.Line);
        game.shape.setColor(Color.BLACK);
        //Draw Hit Box
        MyRectangle hitBox = mouse.getHitBox();
        game.shape.rect(hitBox.x, hitBox.y, hitBox.width, hitBox.height);
        //Draw Map Bounds
        MyRectangle map = new MyRectangle(0, 0, Device.WIDTH, Device.HEIGHT);
        game.shape.rect(map.x, map.y, map.width, map.height);
        game.shape.end();
        
        camera.position.set(mouse.getHitBox().center().x, mouse.getHitBox().center().y, 0);
        camera.update();
        
        cameraManager.zoomApply();
    }
    
    @Override
    public void resize(int width, int height) {
    
    }
    
    @Override
    public void pause() {
    
    }
    
    @Override
    public void resume() {
    
    }
    
    @Override
    public void hide() {
    
    }
    
    @Override
    public void dispose() {
        mouse.dispose();
    }
}
