package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class RandomLevelProviderTest {

    private static final int WIDTH = 5;
    private static final int HEIGHT = 4;
    private static final int OBSTACLE_COUNT = 6;

    private final RandomLevelProvider provider =
            new RandomLevelProvider(WIDTH, HEIGHT, OBSTACLE_COUNT, "images/greenTree.png");

    @Test
    public void placesPlayerSomewhereInsideTheLevel() {
        for (int attempt = 0; attempt < 20; attempt++) {
            GridPoint2 player = provider.provide().getPlayerCoordinates();

            assertTrue(player.x >= 0 && player.x < WIDTH);
            assertTrue(player.y >= 0 && player.y < HEIGHT);
        }
    }

    @Test
    public void fillsLevelWithDistinctObstaclesOutsidePlayerCell() {
        Level level = provider.provide();

        assertEquals(OBSTACLE_COUNT, level.getObstacles().size());

        List<GridPoint2> occupiedCells = new ArrayList<>();
        for (ObstacleSpec obstacle : level.getObstacles()) {
            GridPoint2 coordinates = obstacle.getCoordinates();

            assertTrue(coordinates.x >= 0 && coordinates.x < WIDTH);
            assertTrue(coordinates.y >= 0 && coordinates.y < HEIGHT);
            assertFalse(occupiedCells.contains(coordinates));
            assertNotEquals(level.getPlayerCoordinates(), coordinates);
            assertEquals("images/greenTree.png", obstacle.getTexturePath());

            occupiedCells.add(coordinates);
        }
    }
}
