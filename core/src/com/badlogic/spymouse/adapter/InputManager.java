package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.InputListener;
import com.badlogic.spymouse.mouse.Mouse;

public final class InputManager {
    private OrthographicCamera camera;
    private Viewport viewport;
    private InputMultiplexer multiplexer = new InputMultiplexer();
    
    public InputManager(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;
        
        multiplexer.addProcessor(InputListener.getListener());
    }
    
    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }
    
    public void setGameMode(Mouse mouse) {
        multiplexer.clear();
        multiplexer.addProcessor(InputListener.getListener());
        //multiplexer.addProcessor(new MoveAdapter(listener, mouse));
        multiplexer.addProcessor(new ZoomAdapter(camera));
    }
    
    public void setMenuMode() {
        multiplexer.clear();
        multiplexer.addProcessor(InputListener.getListener());
        //multiplexer.addProcessor(new ZoomAdapter());
    }
    
    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }
    
    public void setViewport(Viewport viewport) {
        this.viewport = viewport;
    }
}
