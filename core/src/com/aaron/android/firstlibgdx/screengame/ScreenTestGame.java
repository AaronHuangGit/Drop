package com.aaron.android.firstlibgdx.screengame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created on 15/9/25.
 *
 * @author aaron.huang
 * @version 1.0.0
 */
public class ScreenTestGame extends Game {
    public SpriteBatch mSpriteBatch;
    public BitmapFont mBitmapFont;
    @Override
    public void create() {
        init();
        setScreen(new MainScreen(this));
    }

    private void init() {
        mSpriteBatch = new SpriteBatch();
        mBitmapFont = new BitmapFont();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose() {
        super.dispose();
        disposeAll();
    }

    private void disposeAll() {
        mBitmapFont.dispose();
        mSpriteBatch.dispose();
    }
}
