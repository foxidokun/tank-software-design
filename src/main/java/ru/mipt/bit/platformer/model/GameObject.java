package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

public class GameObject {

    protected final GridPoint2 coordinates;

    public GameObject(GridPoint2 coordinates) {
        this.coordinates = new GridPoint2(coordinates);
    }

    public GridPoint2 getCoordinates() {
        return coordinates;
    }

    /**
     * Rendering contract: the object occupies the segment between its source and
     * destination tiles, advanced by the movement progress. Static objects stand
     * exactly at their tile.
     */
    public GridPoint2 getDestinationCoordinates() {
        return coordinates;
    }

    public float getMovementProgress() {
        return 1f;
    }

    public float getRotation() {
        return 0f;
    }
}
