package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result of filling a level: where the player starts and which obstacles are placed.
 */
public class Level {

    private final GridPoint2 playerCoordinates;
    private final List<ObstacleSpec> obstacles;

    public Level(GridPoint2 playerCoordinates, List<ObstacleSpec> obstacles) {
        this.playerCoordinates = new GridPoint2(playerCoordinates);
        this.obstacles = Collections.unmodifiableList(new ArrayList<>(obstacles));
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public List<ObstacleSpec> getObstacles() {
        return obstacles;
    }
}
