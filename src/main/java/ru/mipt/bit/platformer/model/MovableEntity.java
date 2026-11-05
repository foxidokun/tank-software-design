package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class MovableEntity extends GameObject {

    private final GameLevel level;
    private final GridPoint2 destinationCoordinates;
    private final float speed;
    private int health;
    private Direction direction = Direction.RIGHT;
    private float movementProgress = 1f;

    public MovableEntity(GridPoint2 coordinates, GameLevel level, float speed, int health) {
        super(coordinates);
        this.level = level;
        this.speed = speed;
        this.health = health;
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void tryMove(Direction direction) {
        if (isDead()) {
            return;
        }
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 target = direction.move(coordinates);
            if (level.isInside(target) && level.objectAt(target) == null) {
                destinationCoordinates.set(target);
                movementProgress = 0f;
            }
            this.direction = direction;
        }
    }

    public void shoot() {
        if (isDead() || !isEqual(movementProgress, 1f)) {
            return;
        }
        GridPoint2 spawnCell = direction.move(coordinates);
        if (!level.isInside(spawnCell)) {
            return;
        }
        GameObject target = level.objectAt(spawnCell);
        if (target instanceof MovableEntity) {
            ((MovableEntity) target).hit(level.getBulletDamage());
            return;
        }
        if (target == null) {
            level.add(new Bullet(spawnCell, direction, level, level.getBulletDamage(), level.getBulletSpeed()));
        }
    }

    public void hit(int damage) {
        health -= damage;
        if (health <= 0) {
            level.remove(this);
        }
    }

    public boolean isDead() {
        return health <= 0;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public void live(float tick) {
        movementProgress = continueProgress(movementProgress, tick, speed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public boolean collidesAt(GridPoint2 coordinates) {
        return super.collidesAt(coordinates) || destinationCoordinates.equals(coordinates);
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return new GridPoint2(destinationCoordinates);
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return direction.getRotation();
    }
}
