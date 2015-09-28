package com.aaron.android.firstlibgdx.android;

import android.os.Bundle;

import com.aaron.android.firstlibgdx.screengame.ScreenTestGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initialize(new ScreenTestGame(), getConfiguration());
	}

	private AndroidApplicationConfiguration getConfiguration() {
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useAccelerometer = false;
		config.useCompass = false;
        config.useGLSurfaceView20API18 = true;
        return config;
	}

}
