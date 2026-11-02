package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RandomBotStrategyTest {

    @Test
    public void nextCommandMovesTankToANeighbourTile() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        RandomBotStrategy strategy = new RandomBotStrategy();
        List<GridPoint2> neighbours = Arrays.stream(Direction.values())
                .map(direction -> direction.move(new GridPoint2(1, 1)))
                .toList();

        for (int attempt = 0; attempt < 100; attempt++) {
            MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);

            strategy.nextCommand(tank).execute();

            assertTrue(neighbours.contains(tank.getDestinationCoordinates()));
        }
    }
}
