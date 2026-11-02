package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class MovableEntity extends GameObject {

    private final GameLevel level;
    private final GridPoint2 destinationCoordinates;
    private final float speed;
    private float movementProgress = 1f;
    private float rotation;

    public MovableEntity(GridPoint2 coordinates, GameLevel level, float speed) {
        super(coordinates);
        this.level = level;
        this.speed = speed;
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void tryMove(Direction direction) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 target = direction.move(coordinates);
            if (level.isInside(target) && level.objectAt(target) == null) {
                destinationCoordinates.set(target);
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
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
        return rotation;
    }
}
