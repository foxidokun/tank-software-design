package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Command;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    private final List<ButtonHandler> buttonHandlers = new ArrayList<>();

    public void add(ButtonHandler handler) {
        buttonHandlers.add(handler);
    }

    public List<Command> createCommands() {
        List<Command> commands = new ArrayList<>();
        for (ButtonHandler handler : buttonHandlers) {
            commands.addAll(handler.createCommands());
        }
        return commands;
    }
}
