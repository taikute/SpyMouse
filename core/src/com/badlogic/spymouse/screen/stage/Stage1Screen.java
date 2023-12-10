package com.badlogic.spymouse.screen.stage;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.spymouse.MyGame;
import com.badlogic.spymouse.cat.FastCat;
import com.badlogic.spymouse.helper.Point;
import com.badlogic.spymouse.helper.PrefKeys;
import com.badlogic.spymouse.helper.Rectangle;
import com.badlogic.spymouse.map.Map1;
import com.badlogic.spymouse.screen.menu.MainMenuScreen;

public final class Stage1Screen extends BaseStage {
    private static final Texture CHEESE_IMAGE = new Texture(Gdx.files.internal("image/cheese.png"));
    private final FastCat fastCat1;
    private final FastCat fastCat2;
    private final FastCat fastCat3;
    private final FastCat fastCat4;
    
    public static float record;
    private static final Rectangle exitBounds = new Rectangle(2000 + 10, 1000 + 10, 80, 80);
    
    public Stage1Screen(MyGame game) {
        super(game, new Map1());
        Point[] wayPoints1 = new Point[3];
        for (int i = 0; i < wayPoints1.length; i++) {
            wayPoints1[i] = new Point(i * 100 + 150, 450);
        }
        fastCat1 = new FastCat(game, mouse, wayPoints1);
        
        Point[] wayPoints2 = new Point[8];
        for (int i = 0; i < wayPoints2.length; i++) {
            wayPoints2[i] = new Point(i * 100 + 250, 850);
        }
        fastCat2 = new FastCat(game, mouse, wayPoints2);
        
        Point[] wayPoints3 = new Point[15];
        for (int i = 0; i < 5; i++) {
            wayPoints3[i] = new Point(i * 100 + 1150, 750);
        }
        for (int i = 0; i < 5; i++) {
            wayPoints3[i + 5] = new Point(1550, 650 - i * 100);
        }
        for (int i = 0; i < 5; i++) {
            wayPoints3[i + 10] = new Point(1550 - i * 100, 150);
        }
        fastCat3 = new FastCat(game, mouse, wayPoints3);
        
        Point[] wayPoints5 = new Point[2];
        for (int i = 0; i < wayPoints5.length; i++) {
            wayPoints5[i] = new Point(2050, 150 + i * 100);
        }
        fastCat4 = new FastCat(game, mouse, wayPoints5);
        
        record = 0;
    }
    
    @Override
    public void draw() {
        game.combineBatch.begin();
        game.combineBatch.draw(CHEESE_IMAGE, exitBounds.x, exitBounds.y, exitBounds.width, exitBounds.height);
        game.combineBatch.end();
        
        fastCat1.draw();
        fastCat2.draw();
        fastCat3.draw();
        fastCat4.draw();
    }
    
    @Override
    public void update(float delta) {
        fastCat1.update(delta);
        fastCat2.update(delta);
        fastCat3.update(delta);
        fastCat4.update(delta);
        
        record += delta;
        if (exitBounds.contains(mouse.getPosition())) {
            if (game.preferences.getFloat(PrefKeys.RECORD, Float.MAX_VALUE) > record){
                game.preferences.putFloat(PrefKeys.RECORD, record);
                game.preferences.flush();
            }
            game.setScreen(new MainMenuScreen(game));
        }
    }
    
    @Override
    public void stageDispose() {
        CHEESE_IMAGE.dispose();
    }
}
