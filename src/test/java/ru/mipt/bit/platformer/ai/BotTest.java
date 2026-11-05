package ru.mipt.bit.platformer.ai;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;

import static org.junit.Assert.assertEquals;

public class BotTest {

    @Test
    public void createCommandAsksStrategyForTheTank() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
        Bot bot = new Bot(tank, movingTank -> new MoveCommand(movingTank, Direction.UP));

        Command command = bot.createCommand();
        command.execute();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void createCommandExecutedRepeatedlyDrivesTheTank() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);
        Bot bot = new Bot(tank, movingTank -> new MoveCommand(movingTank, Direction.RIGHT));

        bot.createCommand().execute();
        tank.live(0.4f);
        bot.createCommand().execute();
        tank.live(0.4f);

        assertEquals(new GridPoint2(3, 1), tank.getCoordinates());
    }
}
