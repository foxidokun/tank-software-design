package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f, Keys.UP, Keys.W),
    LEFT(new GridPoint2(-1, 0), -180f, Keys.LEFT, Keys.A),
    DOWN(new GridPoint2(0, -1), -90f, Keys.DOWN, Keys.S),
    RIGHT(new GridPoint2(1, 0), 0f, Keys.RIGHT, Keys.D);

    private final GridPoint2 delta;
    private final float rotation;
    private final int key1;
    private final int key2;

    Direction(GridPoint2 delta, float rotation, int key1, int key2) {
        this.delta = delta;
        this.rotation = rotation;
        this.key1 = key1;
        this.key2 = key2;
    }

    public GridPoint2 move(GridPoint2 from) {
        return new GridPoint2(from).add(delta);
    }

    public float getRotation() {
        return rotation;
    }

    public boolean isKeyPressed() {
        return Gdx.input.isKeyPressed(key1) || Gdx.input.isKeyPressed(key2);
    }
}
