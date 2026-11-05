package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RandomTankProviderTest {

    @Test
    public void addsTanksOnDistinctFreeCells() {
        Level terrain = new Level(
                new GridPoint2(0, 0),
                Collections.emptyList(),
                Collections.singletonList(new GridPoint2(1, 1)),
                new GridPoint2(3, 3));
        RandomTankProvider provider = new RandomTankProvider(() -> terrain, 2);

        Level level = provider.provide();

        assertEquals(2, level.getTankCoordinates().size());

        List<GridPoint2> occupiedCells = new ArrayList<>();
        occupiedCells.add(level.getPlayerCoordinates());
        occupiedCells.addAll(level.getObstacleCoordinates());
        for (GridPoint2 tank : level.getTankCoordinates()) {
            assertTrue(tank.x >= 0 && tank.x < 3);
            assertTrue(tank.y >= 0 && tank.y < 3);
            assertFalse(occupiedCells.contains(tank));

            occupiedCells.add(tank);
        }
    }

    @Test
    public void keepsTerrainOfDecoratedProvider() {
        Level terrain = new Level(
                new GridPoint2(2, 2),
                Collections.emptyList(),
                Collections.singletonList(new GridPoint2(0, 0)),
                new GridPoint2(5, 5));
        RandomTankProvider provider = new RandomTankProvider(() -> terrain, 1);

        Level level = provider.provide();

        assertEquals(terrain.getPlayerCoordinates(), level.getPlayerCoordinates());
        assertEquals(terrain.getObstacleCoordinates(), level.getObstacleCoordinates());
        assertEquals(terrain.getSize(), level.getSize());
    }

    @Test
    public void doesNotExceedFreeCellsAvailable() {
        Level terrain = new Level(
                new GridPoint2(0, 0),
                Collections.emptyList(),
                Collections.emptyList(),
                new GridPoint2(1, 1));
        RandomTankProvider provider = new RandomTankProvider(() -> terrain, 5);

        Level level = provider.provide();

        assertEquals(0, level.getTankCoordinates().size());
    }
}
