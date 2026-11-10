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
    public void heldKeyCreatesMoveCommandOfTheBoundFactory() {
        FuncButtonHandler handler = FuncButtonHandler.whileHeld(held(Keys.W), () -> new MoveCommand(player, Direction.UP), Keys.UP, Keys.W);

        List<Command> commands = handler.createCommands();

        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof MoveCommand);
        commands.forEach(Command::execute);
        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
    }

    @Test
    public void heldKeyCreatesShootCommandOfTheBoundFactory() {
        FuncButtonHandler handler = FuncButtonHandler.whileHeld(held(Keys.SPACE), () -> new ShootCommand(player), Keys.SPACE);

        List<Command> commands = handler.createCommands();

        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof ShootCommand);
        commands.forEach(Command::execute);
        assertEquals(1, level.getObjects().size());
    }

    @Test
    public void justPressedKeyCreatesCommandOfTheBoundFactory() {
        FuncButtonHandler handler = FuncButtonHandler.onPressed(pressed(Keys.L), () -> new ShootCommand(player), Keys.L);

        List<Command> commands = handler.createCommands();

        assertEquals(1, commands.size());
        assertTrue(commands.get(0) instanceof ShootCommand);
    }

    @Test
    public void heldKeyDoesNotCreateJustPressedCommand() {
        FuncButtonHandler handler = FuncButtonHandler.onPressed(held(Keys.L), () -> new ShootCommand(player), Keys.L);

        assertTrue(handler.createCommands().isEmpty());
    }

    @Test
    public void noPressedKeyCreatesNoCommands() {
        FuncButtonHandler handler = FuncButtonHandler.whileHeld(held(Keys.ESCAPE), () -> new MoveCommand(player, Direction.LEFT), Keys.LEFT, Keys.A);

        assertTrue(handler.createCommands().isEmpty());
    }

    private static KeyStateProvider held(int keyCode) {
        return new KeyStateProvider() {
            @Override
            public boolean isKeyPressed(int code) {
                return code == keyCode;
            }

            @Override
            public boolean isKeyJustPressed(int code) {
                return false;
            }
        };
    }

    private static KeyStateProvider pressed(int keyCode) {
        return new KeyStateProvider() {
            @Override
            public boolean isKeyPressed(int code) {
                return false;
            }

            @Override
            public boolean isKeyJustPressed(int code) {
                return code == keyCode;
            }
        };
    }
}
