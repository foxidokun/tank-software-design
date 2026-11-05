package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameLevel;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.model.ShootCommand;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FuncButtonHandlerTest {

    private final GameLevel level = new GameLevel(new GridPoint2(10, 8), 1, 0.1f);
    private final MovableEntity player = new MovableEntity(new GridPoint2(1, 1), level, 0.4f, 3);

    @Test
    public void pressedKeyCreatesMoveCommandOfTheBoundFactory() {
        KeyStateProvider pressedKeys = keyCode -> keyCode == Keys.W;
        FuncButtonHandler handler = new FuncButtonHandler(player, pressedKeys, tank -> new MoveCommand(tank, Direction.UP), Keys.UP, Keys.W);

        List<Command> commands = handler.createCommands();

        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof MoveCommand);
        commands.forEach(Command::execute);
        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
    }

    @Test
    public void pressedKeyCreatesShootCommandOfTheBoundFactory() {
        KeyStateProvider pressedKeys = keyCode -> keyCode == Keys.SPACE;
        FuncButtonHandler handler = new FuncButtonHandler(player, pressedKeys, ShootCommand::new, Keys.SPACE);

        List<Command> commands = handler.createCommands();

        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof ShootCommand);
        commands.forEach(Command::execute);
        assertEquals(1, level.getObjects().size());
    }

    @Test
    public void noPressedKeyCreatesNoCommands() {
        KeyStateProvider pressedKeys = keyCode -> false;
        FuncButtonHandler handler = new FuncButtonHandler(player, pressedKeys, tank -> new MoveCommand(tank, Direction.LEFT), Keys.LEFT, Keys.A);

        assertTrue(handler.createCommands().isEmpty());
    }
}
