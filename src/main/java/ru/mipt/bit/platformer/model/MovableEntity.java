package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class MovableEntity extends GameObject {

    private final GridPoint2 destinationCoordinates;
    private final GridPoint2 fieldSize;
    private float movementProgress = 1f;
    private float rotation;

    public MovableEntity(GridPoint2 coordinates, GridPoint2 fieldSize) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
        this.fieldSize = new GridPoint2(fieldSize);
    }

    public void tryMove(Direction direction, Collection<GameObject> obstacles) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 target = direction.move(coordinates);
            if (insideField(target) && noCollision(target, obstacles)) {
                destinationCoordinates.set(target);
                movementProgress = 0f;
            }
            rotation = direction.getRotation();
        }
    }

    public void update(float deltaTime, float speed) {
        movementProgress = continueProgress(movementProgress, deltaTime, speed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
        }
    }

    @Override
    public GridPoint2 getDestinationCoordinates() {
        return destinationCoordinates;
    }

    @Override
    public float getMovementProgress() {
        return movementProgress;
    }

    @Override
    public float getRotation() {
        return rotation;
    }

    private boolean insideField(GridPoint2 target) {
        return target.x >= 0 && target.x < fieldSize.x && target.y >= 0 && target.y < fieldSize.y;
    }

    private boolean noCollision(GridPoint2 target, Collection<GameObject> obstacles) {
        for (GameObject obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(target)) {
                return false;
            }
        }
        return true;
    }
}
