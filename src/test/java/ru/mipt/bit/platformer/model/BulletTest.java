package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BulletTest {

    private static final int DAMAGE = 1;
    private static final float SPEED = 0.1f;

    private final GameLevel level = new GameLevel(new GridPoint2(10, 8), DAMAGE, SPEED);

    @Test
    public void fliesInItsDirectionCellByCell() {
        Bullet bullet = new Bullet(new GridPoint2(1, 1), Direction.RIGHT, level, DAMAGE, SPEED);
        level.add(bullet);

        bullet.live(SPEED);
        bullet.live(SPEED);

        assertEquals(new GridPoint2(2, 1), bullet.getCoordinates());
        assertEquals(new GridPoint2(3, 1), bullet.getDestinationCoordinates());
    }

    @Test
    public void disappearsWhenTryingToLeaveTheLevel() {
        GameLevel narrowLevel = new GameLevel(new GridPoint2(2, 8), DAMAGE, SPEED);
        Bullet bullet = new Bullet(new GridPoint2(1, 1), Direction.RIGHT, narrowLevel, DAMAGE, SPEED);
        narrowLevel.add(bullet);

        bullet.live(SPEED);

        assertTrue(narrowLevel.getObjects().isEmpty());
    }

    @Test
    public void explodesOnObstacleWithoutDamagingIt() {
        GameObject tree = new GameObject(new GridPoint2(3, 1));
        level.add(tree);
        Bullet bullet = new Bullet(new GridPoint2(1, 1), Direction.RIGHT, level, DAMAGE, SPEED);
        level.add(bullet);

        bullet.live(SPEED);
        bullet.live(SPEED);

        assertEquals(1, level.getObjects().size());
        assertTrue(level.getObjects().contains(tree));
    }

    @Test
    public void damagesTankItExplodesOn() {
        MovableEntity tank = new MovableEntity(new GridPoint2(3, 1), level, 0.4f, 3);
        level.add(tank);
        Bullet bullet = new Bullet(new GridPoint2(1, 1), Direction.RIGHT, level, DAMAGE, SPEED);
        level.add(bullet);

        bullet.live(SPEED);
        bullet.live(SPEED);

        assertEquals(2, tank.getHealth());
        assertEquals(1, level.getObjects().size());
        assertTrue(level.getObjects().contains(tank));
    }

    @Test
    public void killsTankWhenDamageExceedsItsHealth() {
        MovableEntity tank = new MovableEntity(new GridPoint2(3, 1), level, 0.4f, 1);
        level.add(tank);
        Bullet bullet = new Bullet(new GridPoint2(1, 1), Direction.RIGHT, level, DAMAGE, SPEED);
        level.add(bullet);

        bullet.live(SPEED);
        bullet.live(SPEED);

        assertTrue(level.getObjects().isEmpty());
    }
}
