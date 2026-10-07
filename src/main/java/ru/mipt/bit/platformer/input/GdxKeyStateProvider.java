package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;

/**
 * Reads the keyboard state from libGDX.
 */
public class GdxKeyStateProvider implements KeyStateProvider {

    @Override
    public boolean isKeyPressed(int keyCode) {
        return Gdx.input.isKeyPressed(keyCode);
    }
}
