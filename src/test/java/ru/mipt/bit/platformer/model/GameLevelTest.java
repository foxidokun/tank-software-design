package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class GameLevelTest {

    @Test
    public void addStoresObjectAndNotifiesListeners() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        List<GameObject> added = new ArrayList<>();
        level.addListener(new LevelListener() {
            @Override
            public void objectAdded(GameObject object) {
                added.add(object);
            }

            @Override
            public void objectRemoved(GameObject object) {
            }
        });
        GameObject tree = new GameObject(new GridPoint2(1, 1));

        level.add(tree);

        assertEquals(1, level.getObjects().size());
        assertSame(tree, level.getObjects().get(0));
        assertEquals(List.of(tree), added);
    }

    @Test
    public void removeDeletesObjectAndNotifiesListeners() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        List<GameObject> removed = new ArrayList<>();
        level.addListener(new LevelListener() {
            @Override
            public void objectAdded(GameObject object) {
            }

            @Override
            public void objectRemoved(GameObject object) {
                removed.add(object);
            }
        });
        GameObject tree = new GameObject(new GridPoint2(1, 1));
        level.add(tree);

        level.remove(tree);

        assertTrue(level.getObjects().isEmpty());
        assertEquals(List.of(tree), removed);
    }

    @Test
    public void liveTicksEveryObjectOfTheLevel() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
        level.add(tank);
        tank.tryMove(Direction.RIGHT);

        level.live(0.4f);

        assertEquals(new GridPoint2(2, 1), tank.getCoordinates());
    }

    @Test
    public void objectAtReturnsObjectOccupyingTheCell() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        GameObject tree = new GameObject(new GridPoint2(2, 2));
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
        level.add(tree);
        level.add(tank);
        tank.tryMove(Direction.UP);

        assertSame(tree, level.objectAt(new GridPoint2(2, 2)));
        assertSame(tank, level.objectAt(new GridPoint2(1, 1)));
        assertSame(tank, level.objectAt(new GridPoint2(1, 2)));
        assertNull(level.objectAt(new GridPoint2(5, 5)));
    }

    @Test
    public void isInsideChecksLevelBounds() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);

        assertTrue(level.isInside(new GridPoint2(0, 0)));
        assertTrue(level.isInside(new GridPoint2(9, 7)));
        assertFalse(level.isInside(new GridPoint2(-1, 0)));
        assertFalse(level.isInside(new GridPoint2(0, 8)));
        assertFalse(level.isInside(new GridPoint2(10, 0)));
    }
}
