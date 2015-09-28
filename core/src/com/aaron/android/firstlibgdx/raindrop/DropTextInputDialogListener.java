package com.aaron.android.firstlibgdx.raindrop;

import com.aaron.android.firstlibgdx.utils.Constants;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

/**
 * Created on 15/9/28.
 *
 * @author aaron.huang
 * @version 1.0.0
 */
public class DropTextInputDialogListener implements Input.TextInputListener {
    @Override
    public void input(String text) {
        Gdx.app.log(Constants.TAG, "Drop dialog input text = " + text);
    }

    @Override
    public void canceled() {
        Gdx.app.log(Constants.TAG, "Drop text dialog canceled");
    }
}
