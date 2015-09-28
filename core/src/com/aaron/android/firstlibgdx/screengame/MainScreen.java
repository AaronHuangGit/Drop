package com.aaron.android.firstlibgdx.screengame;

import com.aaron.android.firstlibgdx.raindrop.Drop;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * Created on 15/9/25.
 *
 * @author aaron.huang
 * @version 1.0.0
 */
public class MainScreen implements Screen {
    private final ScreenTestGame mScreenTestGame;
    private OrthographicCamera mCamera;

    /**
     * Constructor
     * @param screenTestGame ScreenTestGame
     */
    public MainScreen(final ScreenTestGame screenTestGame) {
        mScreenTestGame = screenTestGame;
        initCamera();

    }

    private void initCamera() {
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 800, 480);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mCamera.update();
        mScreenTestGame.mSpriteBatch.setProjectionMatrix(mCamera.combined);
        mScreenTestGame.mSpriteBatch.begin();
        mScreenTestGame.mBitmapFont.draw(mScreenTestGame.mSpriteBatch, "Welcome to My Game!", 100, 150);
        mScreenTestGame.mBitmapFont.draw(mScreenTestGame.mSpriteBatch, "tap AnyWhere to begin", 100, 100);
        mScreenTestGame.mSpriteBatch.end();
        if (Gdx.input.isTouched()) {
            mScreenTestGame.setScreen(new Drop(mScreenTestGame));
            dispose();
        }
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

    }
}
