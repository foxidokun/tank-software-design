package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovableEntityTest {

    private static final float SPEED = 0.4f;

    private final GameLevel level = new GameLevel(new GridPoint2(10, 8));
    private final MovableEntity player = new MovableEntity(new GridPoint2(1, 1), level, SPEED);

    @Test
    public void tryMoveSetsDestinationToNeighbourTile() {
        player.tryMove(Direction.UP);

        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
        assertEquals(0f, player.getMovementProgress(), 0f);
        assertEquals(90f, player.getRotation(), 0f);
    }

    @Test
    public void tryMoveIntoObstacleOnlyTurnsThePlayer() {
        level.add(new GameObject(new GridPoint2(1, 0)));

        player.tryMove(Direction.DOWN);

        assertEquals(new GridPoint2(1, 1), player.getDestinationCoordinates());
        assertEquals(-90f, player.getRotation(), 0f);
    }

    @Test
    public void tryMoveIsIgnoredWhilePreviousMovementIsNotFinished() {
        player.tryMove(Direction.RIGHT);
        player.live(0.1f); // movement is still in progress

        player.tryMove(Direction.UP);

        assertEquals(new GridPoint2(2, 1), player.getDestinationCoordinates());
    }

    @Test
    public void liveMovesCoordinatesToDestinationWhenMovementFinishes() {
        player.tryMove(Direction.RIGHT);

        player.live(SPEED);

        assertEquals(1f, player.getMovementProgress(), 0f);
        assertEquals(new GridPoint2(2, 1), player.getCoordinates());
    }

    @Test
    public void returnedDestinationCoordinatesCannotBeMutated() {
        player.tryMove(Direction.UP);

        player.getDestinationCoordinates().set(5, 5);

        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
    }

    @Test
    public void tryMoveBeyondFieldBoundsOnlyTurnsThePlayer() {
        GameLevel smallLevel = new GameLevel(new GridPoint2(1, 1));
        MovableEntity tank = new MovableEntity(new GridPoint2(0, 0), smallLevel, SPEED);

        tank.tryMove(Direction.LEFT);

        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
        assertEquals(-180f, tank.getRotation(), 0f);

        tank.tryMove(Direction.UP);

        assertEquals(new GridPoint2(0, 0), tank.getDestinationCoordinates());
    }

    @Test
    public void movingTankOccupiesBothSourceAndDestinationCells() {
        player.tryMove(Direction.UP);

        assertTrue(player.collidesAt(new GridPoint2(1, 1)));
        assertTrue(player.collidesAt(new GridPoint2(1, 2)));
        assertFalse(player.collidesAt(new GridPoint2(2, 1)));
    }

    @Test
    public void tankCannotMoveIntoCellsOfMovingTank() {
        MovableEntity moving = new MovableEntity(new GridPoint2(1, 1), level, SPEED);
        level.add(moving);
        moving.tryMove(Direction.UP);

        MovableEntity neighbour = new MovableEntity(new GridPoint2(0, 1), level, SPEED);
        neighbour.tryMove(Direction.RIGHT);
        assertEquals(new GridPoint2(0, 1), neighbour.getDestinationCoordinates());

        MovableEntity upperNeighbour = new MovableEntity(new GridPoint2(0, 2), level, SPEED);
        upperNeighbour.tryMove(Direction.RIGHT);
        assertEquals(new GridPoint2(0, 2), upperNeighbour.getDestinationCoordinates());
    }
}
