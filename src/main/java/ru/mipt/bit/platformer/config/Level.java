package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Result of filling a level: where the player and tanks start, which cells are
 * occupied by obstacles and how large the field is.
 */
public class Level {

    private final GridPoint2 playerCoordinates;
    private final List<GridPoint2> tankCoordinates;
    private final List<GridPoint2> obstacleCoordinates;
    private final GridPoint2 size;

    public Level(GridPoint2 playerCoordinates, List<GridPoint2> tankCoordinates, List<GridPoint2> obstacleCoordinates, GridPoint2 size) {
        this.playerCoordinates = new GridPoint2(playerCoordinates);
        this.tankCoordinates = Collections.unmodifiableList(new ArrayList<>(tankCoordinates));
        this.obstacleCoordinates = Collections.unmodifiableList(new ArrayList<>(obstacleCoordinates));
        this.size = new GridPoint2(size);
    }

    public GridPoint2 getPlayerCoordinates() {
        return playerCoordinates;
    }

    public List<GridPoint2> getTankCoordinates() {
        return tankCoordinates;
    }

    public List<GridPoint2> getObstacleCoordinates() {
        return obstacleCoordinates;
    }

    public GridPoint2 getSize() {
        return new GridPoint2(size);
    }
}
