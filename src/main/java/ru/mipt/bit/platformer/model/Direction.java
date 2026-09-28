package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public enum Direction {
    UP(new GridPoint2(0, 1), 90f),
    LEFT(new GridPoint2(-1, 0), -180f),
    DOWN(new GridPoint2(0, -1), -90f),
    RIGHT(new GridPoint2(1, 0), 0f);

    private final GridPoint2 delta;
    private final float rotation;

    Direction(GridPoint2 delta, float rotation) {
        this.delta = delta;
        this.rotation = rotation;
    }

    public GridPoint2 move(GridPoint2 from) {
        return new GridPoint2(from).add(delta);
    }

    public float getRotation() {
        return rotation;
    }
}
