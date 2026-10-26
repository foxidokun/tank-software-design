package ru.mipt.bit.platformer.ai;

import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;

import java.util.EnumSet;

import static org.junit.Assert.assertTrue;

public class RandomMoveStrategyTest {

    @Test
    public void choosesOnlyExistingDirections() {
        RandomMoveStrategy strategy = new RandomMoveStrategy();
        EnumSet<Direction> possible = EnumSet.allOf(Direction.class);

        for (int attempt = 0; attempt < 100; attempt++) {
            assertTrue(possible.contains(strategy.chooseDirection()));
        }
    }
}
