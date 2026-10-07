package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

/**
 * Description of a single static object placed on the level.
 */
public class ObstacleSpec {

    private final String texturePath;
    private final GridPoint2 coordinates;

    public ObstacleSpec(String texturePath, GridPoint2 coordinates) {
        this.texturePath = texturePath;
        this.coordinates = new GridPoint2(coordinates);
    }

    public ObstacleSpec(String texturePath, int x, int y) {
        this(texturePath, new GridPoint2(x, y));
    }

    public String getTexturePath() {
        return texturePath;
    }

    public GridPoint2 getCoordinates() {
        return new GridPoint2(coordinates);
    }
}
