package com.aaron.android.firstlibgdx.raindrop;

import com.aaron.android.firstlibgdx.screengame.MainScreen;
import com.aaron.android.firstlibgdx.screengame.ScreenTestGame;
import com.aaron.android.firstlibgdx.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

public class Drop implements Screen {

    private final ScreenTestGame mGame;
    private Texture mDropImage;
    private Texture mBucketImage;
    private Music mRainMusic;
    private Sound mDropSound;
    private OrthographicCamera mCamera;
    private Rectangle mBucketRectangle;
    private Vector3 mTouchPos;
    private Array<Rectangle> mRainDrops;
    private long mLastDropTime;
    private int mCollectDropCount;

    public Drop(final ScreenTestGame game) {
        mGame = game;
        createImageAssets();
        createAudioAssets();
        createCamera();
        createRainDrops();
        initBucketRectangle();
        printPeripheralAvailable();
        config();
    }

    private void config() {
        Gdx.input.setCatchBackKey(false);
        Gdx.input.setInputProcessor(new DropInputProcessor());
    }

    private void printPeripheralAvailable() {
        boolean isHardwareKeyboardAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.HardwareKeyboard);
        boolean isMultitouchScreenAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.MultitouchScreen);
        boolean isAccelerometerAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        boolean isOnscreenKeyboardAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.OnscreenKeyboard);
        boolean isCompassAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        Gdx.app.log(Constants.TAG, "HardwareKeyboard Available: " + isHardwareKeyboardAvailable);
        Gdx.app.log(Constants.TAG, "MultitouchScreen Available: " + isMultitouchScreenAvailable);
        Gdx.app.log(Constants.TAG, "Accelerometer Available: " + isAccelerometerAvailable);
        Gdx.app.log(Constants.TAG, "OnscreenKeyboard Available: " + isOnscreenKeyboardAvailable);
        Gdx.app.log(Constants.TAG, "Compass Available: " + isCompassAvailable);
    }

    @Override
    public void render(float delta) {
        renderBackground();
        mCamera.update();
        mGame.mSpriteBatch.setProjectionMatrix(mCamera.combined);
        mGame.mSpriteBatch.begin();
        mGame.mSpriteBatch.draw(mBucketImage, mBucketRectangle.x, mBucketRectangle.y);
        for (Rectangle rainDrop : mRainDrops) {
            mGame.mSpriteBatch.draw(mDropImage, rainDrop.x, rainDrop.y);
        }
        mGame.mBitmapFont.draw(mGame.mSpriteBatch, "Collect drop count: " + mCollectDropCount, 0, 480);
        mGame.mSpriteBatch.end();

        if (TimeUtils.nanoTime() - mLastDropTime >= 1000000000) {
            spawnRaindrop();
        }

        Iterator<Rectangle> iterator = mRainDrops.iterator();
        while (iterator.hasNext()) {
            Rectangle rainRectangle = iterator.next();
            rainRectangle.y -= 200 * delta;
            if (rainRectangle.y + 64 < 0) {
                iterator.remove();
            }
            if (rainRectangle.overlaps(mBucketRectangle)) {
                mCollectDropCount++;
                mDropSound.play();
                iterator.remove();
            }
        }
    }

    @Override
    public void show() {
        mRainMusic.play();
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
        mDropImage.dispose();
        mBucketImage.dispose();
        mRainMusic.dispose();
        mDropSound.dispose();
    }

    private void createCamera() {
        mCamera = new OrthographicCamera();
        mCamera.setToOrtho(false, 800, 480);
    }

    private void createAudioAssets() {
        mRainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        mDropSound = Gdx.audio.newSound(Gdx.files.internal("water.wav"));
        mRainMusic.setLooping(true);
        mRainMusic.play();
    }

    private void createImageAssets() {
        mDropImage = new Texture(Gdx.files.internal("droplet.png"));
        mBucketImage = new Texture(Gdx.files.internal("bucket.png"));
    }

    private void createRainDrops() {
        mRainDrops = new Array<Rectangle>();
        spawnRaindrop();
    }

    private void initBucketRectangle() {
        mBucketRectangle = new Rectangle();
        mBucketRectangle.x = 800 / 2 - 64 / 2;
        mBucketRectangle.y = 20;
        mBucketRectangle.height = 64;
        mBucketRectangle.width = 64;
    }

    private void renderBackground() {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    private void spawnRaindrop() {
        Rectangle raindrop = new Rectangle();
        raindrop.x = MathUtils.random(0, 800 - 64);
        raindrop.y = 480;
        raindrop.width = 64;
        raindrop.height = 64;
        mRainDrops.add(raindrop);
        mLastDropTime = TimeUtils.nanoTime();
        Gdx.input.getInputProcessor();
    }

    private class DropInputProcessor extends InputAdapter {

        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            getBucketXByTouch(screenX, screenY);
            return super.touchDown(screenX, screenY, pointer, button);
        }

        @Override
        public boolean touchDragged(int screenX, int screenY, int pointer) {
            getBucketXByTouch(screenX, screenY);
            return super.touchDragged(screenX, screenY, pointer);
        }
    }

    private void getBucketXByTouch(int screenX, int screenY) {
        mTouchPos = new Vector3();
        mTouchPos.set(screenX, screenY, 0);
        mCamera.unproject(mTouchPos);
        mBucketRectangle.x = mTouchPos.x - 64 / 2;
        mBucketRectangle.x = Math.max(0, mBucketRectangle.x);
        mBucketRectangle.x = Math.min(mBucketRectangle.x, 800 - 64);
    }
}
