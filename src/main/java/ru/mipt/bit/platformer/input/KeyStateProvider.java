package ru.mipt.bit.platformer.input;

/**
 * Abstraction over the keyboard state. The game code depends on this interface
 * instead of the static {@code Gdx.input} singleton, which makes handlers
 * testable and lets a different input source be plugged in.
 */
public interface KeyStateProvider {

    boolean isKeyPressed(int keyCode);
}
