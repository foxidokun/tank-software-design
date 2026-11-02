package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameObjectTest {

    @Test
    public void storesProvidedCoordinates() {
        GameObject object = new GameObject(new GridPoint2(2, 3));

        assertEquals(new GridPoint2(2, 3), object.getCoordinates());
    }

    @Test
    public void keepsItsOwnCoordinatesCopy() {
        GridPoint2 coordinates = new GridPoint2(2, 3);
        GameObject object = new GameObject(coordinates);

        coordinates.set(5, 5);

        assertEquals(new GridPoint2(2, 3), object.getCoordinates());
    }

    @Test
    public void returnedCoordinatesCannotBeMutated() {
        GameObject object = new GameObject(new GridPoint2(2, 3));

        object.getCoordinates().set(5, 5);

        assertEquals(new GridPoint2(2, 3), object.getCoordinates());
    }

    @Test
    public void returnedDestinationCoordinatesCannotBeMutated() {
        GameObject object = new GameObject(new GridPoint2(2, 3));

        object.getDestinationCoordinates().set(5, 5);

        assertEquals(new GridPoint2(2, 3), object.getDestinationCoordinates());
    }

    @Test
    public void collidesOnlyAtOwnCoordinates() {
        GameObject object = new GameObject(new GridPoint2(2, 3));

        assertTrue(object.collidesAt(new GridPoint2(2, 3)));
        assertFalse(object.collidesAt(new GridPoint2(5, 5)));
    }
}
