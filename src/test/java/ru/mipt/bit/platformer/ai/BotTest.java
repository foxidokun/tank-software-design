package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class BotTest {

    @Test
    public void updateDrivesTankInDirectionChosenByStrategy() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), new GridPoint2(10, 8));
        Bot bot = new Bot(tank, () -> Direction.UP, Collections.emptyList());

        bot.update(0.1f, 0.4f);

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void updateAdvancesTankMovement() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), new GridPoint2(10, 8));
        Bot bot = new Bot(tank, () -> Direction.UP, Collections.emptyList());

        bot.update(0.4f, 0.4f);

        assertEquals(new GridPoint2(1, 2), tank.getCoordinates());
        assertEquals(1f, tank.getMovementProgress(), 0f);
    }

    @Test
    public void updateDoesNotChangeDestinationWhileMovementIsInProgress() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), new GridPoint2(10, 8));
        Bot bot = new Bot(tank, () -> Direction.UP, Collections.emptyList());

        bot.update(0.1f, 0.4f);
        bot.update(0.1f, 0.4f);

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }
}
