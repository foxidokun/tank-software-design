package ru.mipt.bit.platformer.input;

import ru.mipt.bit.platformer.model.Command;

import java.util.List;

public interface ButtonHandler {

    List<Command> createCommands();
}
