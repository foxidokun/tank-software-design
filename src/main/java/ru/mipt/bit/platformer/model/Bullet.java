package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;

import static com.badlogic.gdx.math.MathUtils.isEqual;
import static ru.mipt.bit.platformer.util.GdxGameUtils.continueProgress;

public class Bullet extends GameObject {

    private final GameLevel level;
    private final Direction direction;
    private final float speed;
    private final GridPoint2 destinationCoordinates;
    private float movementProgress = 1f;

    public Bullet(GridPoint2 coordinates, Direction direction, GameLevel level, float speed) {
        super(coordinates);
        this.direction = direction;
        this.level = level;
        this.speed = speed;
        this.destinationCoordinates = new GridPoint2(coordinates);
    }

    @Override
    public void live(float tick) {
        movementProgress = continueProgress(movementProgress, tick, speed);
        if (isEqual(movementProgress, 1f)) {
            coordinates.set(destinationCoordinates);
            flyNext();
        }
    }

    @Override
    public void hit(int damage) {
        level.remove(this);
    }

    private void flyNext() {
        GridPoint2 next = direction.move(coordinates);
        if (!level.isInside(next) || level.hitAt(next)) {
            level.remove(this);
            return;
        }
        destinationCoordinates.set(next);
        movementProgress = 0f;
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
