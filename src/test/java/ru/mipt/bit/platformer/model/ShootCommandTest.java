package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ShootCommandTest {

    @Test
    public void executeMakesTankShoot() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);

        new ShootCommand(tank).execute();

        assertEquals(1, level.getObjects().size());
        assertTrue(level.getObjects().get(0) instanceof Bullet);
    }
}
