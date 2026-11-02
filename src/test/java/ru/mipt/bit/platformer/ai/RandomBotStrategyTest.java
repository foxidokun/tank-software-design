package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Bullet;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RandomBotStrategyTest {

    private final RandomBotStrategy strategy = new RandomBotStrategy();

    @Test
    public void nextCommandMovesTankToANeighbourTileOrMakesItShoot() {
        List<GridPoint2> neighbours = Arrays.stream(Direction.values())
                .map(direction -> direction.move(new GridPoint2(1, 1)))
                .toList();

        for (int attempt = 0; attempt < 100; attempt++) {
            GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
            MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
            level.add(tank);

            strategy.nextCommand(tank).execute();

            if (tank.getDestinationCoordinates().equals(new GridPoint2(1, 1))) {
                assertEquals(2, level.getObjects().size());
                assertTrue(level.getObjects().get(1) instanceof Bullet);
            } else {
                assertTrue(neighbours.contains(tank.getDestinationCoordinates()));
            }
        }
    }

    @Test
    public void nextCommandSometimesMovesAndSometimesShoots() {
        boolean moved = false;
        boolean shot = false;

        for (int attempt = 0; attempt < 100; attempt++) {
            GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
            MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
            level.add(tank);

            strategy.nextCommand(tank).execute();

            moved |= !tank.getDestinationCoordinates().equals(new GridPoint2(1, 1));
            shot |= level.getObjects().size() == 2;
        }

        assertTrue(moved);
        assertTrue(shot);
    }
}
