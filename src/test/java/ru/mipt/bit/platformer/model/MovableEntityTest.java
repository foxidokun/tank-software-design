package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MovableEntityTest {

    private final MovableEntity player = new MovableEntity(new GridPoint2(1, 1));

    @Test
    public void tryMoveSetsDestinationToNeighbourTile() {
        player.tryMove(Direction.UP, Collections.emptyList());

        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
        assertEquals(0f, player.getMovementProgress(), 0f);
        assertEquals(90f, player.getRotation(), 0f);
    }

    @Test
    public void tryMoveIntoObstacleOnlyTurnsThePlayer() {
        List<GameObject> obstacles = Collections.singletonList(new GameObject(new GridPoint2(1, 0)));

        player.tryMove(Direction.DOWN, obstacles);

        assertEquals(new GridPoint2(1, 1), player.getDestinationCoordinates());
        assertEquals(-90f, player.getRotation(), 0f);
    }

    @Test
    public void tryMoveIsIgnoredWhilePreviousMovementIsNotFinished() {
        player.tryMove(Direction.RIGHT, Collections.emptyList());
        player.update(0.1f, 0.4f); // movement is still in progress

        player.tryMove(Direction.UP, Collections.emptyList());

        assertEquals(new GridPoint2(2, 1), player.getDestinationCoordinates());
    }

    @Test
    public void updateMovesCoordinatesToDestinationWhenMovementFinishes() {
        player.tryMove(Direction.RIGHT, Collections.emptyList());

        player.update(0.4f, 0.4f);

        assertEquals(1f, player.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(2, 1), player.getCoordinates());
    }
}
