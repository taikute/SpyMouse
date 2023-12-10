package com.badlogic.spymouse.cat;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.mouse.Mouse;

public abstract class BaseCat implements Disposable {
    protected final float WIDTH = 100;
    protected final float HEIGHT = 100;
    protected final Point position;
    protected final MyGame game;
    protected final Mouse mouse;
    
    protected BaseCat(MyGame game, Mouse mouse, Point startPosition) {
        this.game = game;
        this.mouse = mouse;
        position = startPosition;
    }
    
    public abstract void draw();
    
    public abstract void update(float delta);
}
