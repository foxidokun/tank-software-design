package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result of filling a level: where the player and tanks start, which obstacles are
 * placed and how large the field is.
 */
public class Level {

    private final GridPoint2 playerCoordinates;
    private final List<GridPoint2> tankCoordinates;
    private final List<ObstacleSpec> obstacles;
    private final GridPoint2 size;

    public Level(GridPoint2 playerCoordinates, List<GridPoint2> tankCoordinates, List<ObstacleSpec> obstacles, GridPoint2 size) {
        this.playerCoordinates = new GridPoint2(playerCoordinates);
        this.tankCoordinates = Collections.unmodifiableList(new ArrayList<>(tankCoordinates));
        this.obstacles = Collections.unmodifiableList(new ArrayList<>(obstacles));
        this.size = new GridPoint2(size);
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public List<GridPoint2> getTankCoordinates() {
        return tankCoordinates;
    }

    public List<ObstacleSpec> getObstacles() {
        return obstacles;
    }

    public GridPoint2 getSize() {
        return new GridPoint2(size);
    }
}
