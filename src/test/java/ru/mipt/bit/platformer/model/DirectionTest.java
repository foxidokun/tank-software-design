package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DirectionTest {

    @Test
    public void moveReturnsNeighbourTile() {
        GridPoint2 from = new GridPoint2(1, 1);

        assertEquals(new GridPoint2(1, 2), Direction.UP.move(from));
        assertEquals(new GridPoint2(0, 1), Direction.LEFT.move(from));
        assertEquals(new GridPoint2(1, 0), Direction.DOWN.move(from));
        assertEquals(new GridPoint2(2, 1), Direction.RIGHT.move(from));
    }

    @Test
    public void rotationMatchesDirection() {
        assertEquals(90f, Direction.UP.getRotation(), 0f);
        assertEquals(-180f, Direction.LEFT.getRotation(), 0f);
        assertEquals(-90f, Direction.DOWN.getRotation(), 0f);
        assertEquals(0f, Direction.RIGHT.getRotation(), 0f);
    }

    @Test
    public void moveDoesNotModifyOriginalCoordinates() {
        GridPoint2 from = new GridPoint2(1, 1);

        Direction.UP.move(from);

        assertEquals(new GridPoint2(1, 1), from);
    }
}
