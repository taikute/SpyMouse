package com.badlogic.spymouse.adapter;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.spymouse.mouse.Mouse;

public final class InputManager {
    private final OrthographicCamera camera;
    public final Viewport viewport;
    private final InputMultiplexer multiplexer = new InputMultiplexer();
    private final InputListener listener;

    public InputManager(OrthographicCamera camera, Viewport viewport) {
        this.camera = camera;
        this.viewport = viewport;

        listener = new InputListener(camera, viewport);
        multiplexer.addProcessor(listener);
    }

    public InputMultiplexer getMultiplexer() {
        return multiplexer;
    }

    public void setGameMode(Mouse mouse) {
        multiplexer.clear();
        multiplexer.addProcessor(listener);
        multiplexer.addProcessor(new MoveAdapter(mouse, listener));
        multiplexer.addProcessor(new ZoomAdapter(camera, listener));
    }

    public void setMenuMode() {
        multiplexer.clear();
        multiplexer.addProcessor(listener);
        //
    }
}
