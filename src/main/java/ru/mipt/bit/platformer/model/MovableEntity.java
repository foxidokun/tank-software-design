package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import java.util.Collection;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class MovableEntity extends GameObject {

    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;
    private float rotation;

    public MovableEntity(GridPoint2 coordinates) {
        super(coordinates);
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    public void tryMove(Direction direction, Collection<GameObject> obstacles) {
        if (isEqual(movementProgress, 1f)) {
            GridPoint2 target = direction.move(coordinates);
            if (noCollision(target, obstacles)) {
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

    private boolean noCollision(GridPoint2 target, Collection<GameObject> obstacles) {
        for (GameObject obstacle : obstacles) {
            if (obstacle.getCoordinates().equals(target)) {
                return false;
            }
        }
        return true;
    }
}
