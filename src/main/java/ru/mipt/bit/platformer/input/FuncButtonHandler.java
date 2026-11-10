package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Command;

import java.util.Collections;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.function.Supplier;

public class FuncButtonHandler implements ButtonHandler {

    private final IntPredicate keyDown;
    private final Supplier<Command> commandFactory;
    private final int[] keyCodes;

    private FuncButtonHandler(IntPredicate keyDown, Supplier<Command> commandFactory, int... keyCodes) {
        this.keyDown = keyDown;
        this.commandFactory = commandFactory;
        this.keyCodes = keyCodes;
    }

    public static FuncButtonHandler whileHeld(KeyStateProvider keyState, Supplier<Command> commandFactory, int... keyCodes) {
        return new FuncButtonHandler(keyState::isKeyPressed, commandFactory, keyCodes);
    }

    public static FuncButtonHandler onPressed(KeyStateProvider keyState, Supplier<Command> commandFactory, int... keyCodes) {
        return new FuncButtonHandler(keyState::isKeyJustPressed, commandFactory, keyCodes);
    }

    @Override
    public List<Command> createCommands() {
        for (int keyCode : keyCodes) {
            if (keyDown.test(keyCode)) {
                return Collections.singletonList(commandFactory.get());
            }
        }
        return Collections.emptyList();
    }
}
