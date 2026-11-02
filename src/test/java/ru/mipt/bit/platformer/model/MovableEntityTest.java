package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovableEntityTest {

    private static final GridPoint2 FIELD_SIZE = new GridPoint2(10, 8);

    private final MovableEntity player = new MovableEntity(new GridPoint2(1, 1), FIELD_SIZE);

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

    @Test
    public void returnedDestinationCoordinatesCannotBeMutated() {
        player.tryMove(Direction.UP, Collections.emptyList());

        player.getDestinationCoordinates().set(5, 5);

        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
    }

    @Test
    public void tryMoveBeyondFieldBoundsOnlyTurnsThePlayer() {
        MovableEntity tank = new MovableEntity(new GridPoint2(0, 0), new GridPoint2(1, 1));

        tank.tryMove(Direction.LEFT, Collections.emptyList());

        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
        assertEquals(-180f, tank.getRotation(), 0f);

        tank.tryMove(Direction.UP, Collections.emptyList());

        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
    }

    @Test
    public void movingTankOccupiesBothSourceAndDestinationCells() {
        player.tryMove(Direction.UP, Collections.emptyList());

        assertTrue(player.collidesAt(new GridPoint2(1, 1)));
        assertTrue(player.collidesAt(new GridPoint2(1, 2)));
        assertFalse(player.collidesAt(new GridPoint2(2, 1)));
    }

    @Test
    public void tankCannotMoveIntoCellsOfMovingTank() {
        MovableEntity moving = new MovableEntity(new GridPoint2(1, 1), FIELD_SIZE);
        moving.tryMove(Direction.UP, Collections.emptyList());
        List<GameObject> obstacles = Collections.singletonList(moving);

        MovableEntity neighbour = new MovableEntity(new GridPoint2(0, 1), FIELD_SIZE);
        neighbour.tryMove(Direction.RIGHT, obstacles);
        assertEquals(new GridPoint2(0, 1), neighbour.getDestinationCoordinates());

        MovableEntity upperNeighbour = new MovableEntity(new GridPoint2(0, 2), FIELD_SIZE);
        upperNeighbour.tryMove(Direction.RIGHT, obstacles);
        assertEquals(new GridPoint2(0, 2), upperNeighbour.getDestinationCoordinates());
    }
}
