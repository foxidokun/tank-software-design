package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MovableEntityTest {

    private static final float SPEED = 0.4f;
    private static final int HEALTH = 3;

    private final GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
    private final MovableEntity player = new MovableEntity(new GridPoint2(1, 1), level, SPEED, HEALTH);

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
        GameLevel smallLevel = new GameLevel(new GridPoint2(1, 1), 1, 0.1f);
        MovableEntity tank = new MovableEntity(new GridPoint2(0, 0), smallLevel, SPEED, HEALTH);

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
        MovableEntity moving = new MovableEntity(new GridPoint2(1, 1), level, SPEED, HEALTH);
        level.add(moving);
        moving.tryMove(Direction.UP);

        MovableEntity neighbour = new MovableEntity(new GridPoint2(0, 1), level, SPEED, HEALTH);
        neighbour.tryMove(Direction.RIGHT);
        assertEquals(new GridPoint2(0, 1), neighbour.getDestinationCoordinates());

        MovableEntity upperNeighbour = new MovableEntity(new GridPoint2(0, 2), level, SPEED, HEALTH);
        upperNeighbour.tryMove(Direction.RIGHT);
        assertEquals(new GridPoint2(0, 2), upperNeighbour.getDestinationCoordinates());
    }

    @Test
    public void shootRegistersBulletInFacedNeighbourCell() {
        player.shoot();

        assertEquals(1, level.getObjects().size());
        GameObject bullet = level.getObjects().get(0);
        assertTrue(bullet instanceof Bullet);
        assertEquals(new GridPoint2(2, 1), bullet.getCoordinates());
    }

    @Test
    public void shootIsIgnoredWhileMovementIsNotFinished() {
        player.tryMove(Direction.UP);

        player.shoot();

        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void shootTowardsObstacleDoesNotCreateBullet() {
        level.add(new GameObject(new GridPoint2(2, 1)));

        player.shoot();

        assertEquals(1, level.getObjects().size());
        assertFalse(level.getObjects().get(0) instanceof Bullet);
    }

    @Test
    public void shootTowardsLevelEdgeDoesNotCreateBullet() {
        MovableEntity tank = new MovableEntity(new GridPoint2(9, 1), level, SPEED, HEALTH);

        tank.shoot();

        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void shootHitsAdjacentTankWithoutCreatingBullet() {
        MovableEntity enemy = new MovableEntity(new GridPoint2(2, 1), level, SPEED, HEALTH);
        level.add(enemy);

        player.shoot();

        assertEquals(HEALTH - 1, enemy.getHealth());
        assertEquals(1, level.getObjects().size());
    }

    @Test
    public void shootKillsAdjacentTankWhenHealthRunsOut() {
        MovableEntity enemy = new MovableEntity(new GridPoint2(2, 1), level, SPEED, 1);
        level.add(enemy);

        player.shoot();

        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void hitReducesHealthAndRemovesDeadTankFromLevel() {
        level.add(player);

        player.hit(2);

        assertEquals(HEALTH - 2, player.getHealth());
        assertFalse(player.isDead());
        assertEquals(1, level.getObjects().size());

        player.hit(1);

        assertTrue(player.isDead());
        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void healthFractionFallsWithTakenDamage() {
        level.add(player);

        assertEquals(1f, player.getHealthFraction(), 0f);

        player.hit(1);

        assertEquals(2f / 3f, player.getHealthFraction(), 0.0001f);
    }

    @Test
    public void deadTankIgnoresMoveAndShoot() {
        level.add(player);
        player.hit(HEALTH);

        player.tryMove(Direction.UP);
        player.shoot();

        assertEquals(new GridPoint2(1, 1), player.getDestinationCoordinates());
        assertTrue(level.getObjects().isEmpty());
    }

    @Test
    public void lightConditionKeepsNormalMovement() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, SPEED, 100);
        level.add(tank);
        tank.hit(10);

        tank.tryMove(Direction.RIGHT);
        tank.live(SPEED);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    public void mediumConditionDoublesMovementDuration() {
        level.add(player);
        player.hit(1);

        player.tryMove(Direction.RIGHT);
        player.live(SPEED);

        assertEquals(0.5f, player.getMovementProgress(), 0.0001f);
        assertEquals(new GridPoint2(1, 1), player.getCoordinates());

        player.live(SPEED);

        assertEquals(new GridPoint2(2, 1), player.getCoordinates());
    }

    @Test
    public void mediumConditionStillAllowsShooting() {
        level.add(player);
        player.hit(1);

        player.shoot();

        assertEquals(2, level.getObjects().size());
    }

    @Test
    public void heavyConditionTriplesMovementDuration() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, SPEED, 100);
        level.add(tank);
        tank.hit(90);

        tank.tryMove(Direction.RIGHT);
        tank.live(SPEED);

        assertEquals(1f / 3f, tank.getMovementProgress(), 0.0001f);
        assertEquals(new GridPoint2(1, 1), tank.getCoordinates());
    }

    @Test
    public void heavyConditionPreventsShooting() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, SPEED, 100);
        tank.hit(90);

        tank.shoot();

        assertTrue(level.getObjects().isEmpty());
    }
}
