package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class FuncButtonHandler implements ButtonHandler {

    private final MovableEntity tank;
    private final KeyStateProvider keyState;
    private final Function<MovableEntity, Command> commandFactory;
    private final int[] keyCodes;

    public FuncButtonHandler(MovableEntity tank, KeyStateProvider keyState, Function<MovableEntity, Command> commandFactory, int... keyCodes) {
        this.tank = tank;
        this.keyState = keyState;
        this.commandFactory = commandFactory;
        this.keyCodes = keyCodes;
    }

    @Override
    public List<Command> createCommands() {
        for (int keyCode : keyCodes) {
            if (keyState.isKeyPressed(keyCode)) {
                return Collections.singletonList(commandFactory.apply(tank));
            }
        }
        return Collections.emptyList();
    }
}
