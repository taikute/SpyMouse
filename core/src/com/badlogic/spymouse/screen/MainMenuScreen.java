package com.badlogic.spymouse.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.spymouse.Device;
import com.badlogic.spymouse.SpyMouse;
import com.badlogic.spymouse.adapter.InputManager;
import com.badlogic.spymouse.mouse.Mouse;

public final class MainMenuScreen implements Screen {
    public InputManager inputManager;
    private final SpyMouse game;
    private OrthographicCamera camera;
    private ShapeRenderer shapeRenderer;
    private ExtendViewport extendViewport;
    private Mouse mouse;
    
    int viewportHeight = Device.HEIGHT;
    
    public MainMenuScreen(SpyMouse game) {
        this.game = game;
    }
    
    @Override
    public void show() {
        shapeRenderer = new ShapeRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        
        extendViewport = new ExtendViewport(0, viewportHeight, camera);
        mouse = new Mouse(0, 0, camera,extendViewport);
        
        inputManager = new InputManager(camera, extendViewport);
        inputManager.setGameMode(mouse);
        Gdx.input.setInputProcessor(inputManager.getMultiplexer());
    }
    
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.WHITE);
        extendViewport.apply();
        game.batch.setProjectionMatrix(camera.combined);
        mouse.update(delta, game.batch);
        
        //Draw HitBox
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(mouse.getHitBox().x, mouse.getHitBox().y, mouse.getHitBox().width, mouse.getHitBox().height);
        shapeRenderer.end();
        
        //Center Mouse
        camera.position.x = mouse.getCenter(mouse.getHitBox()).x;
        camera.position.y = mouse.getCenter(mouse.getHitBox()).y;
        camera.update();
        
        System.out.println(camera.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0)));
    }
    
    @Override
    public void resize(int width, int height) {
        extendViewport.update(width, height);
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
