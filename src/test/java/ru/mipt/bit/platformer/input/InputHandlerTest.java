package ru.mipt.bit.platformer.input;

import org.junit.Test;
import ru.mipt.bit.platformer.model.Command;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputHandlerTest {

    @Test
    public void createCommandsCollectsCommandsOfAllRegisteredHandlers() {
        InputHandler inputHandler = new InputHandler();
        List<String> handled = new ArrayList<>();
        inputHandler.add(() -> List.of(new Command() {
            @Override
            public void execute() {
                handled.add("first");
            }
        }));
        inputHandler.add(() -> List.of(new Command() {
            @Override
            public void execute() {
                handled.add("second");
            }
        }));

        List<Command> commands = inputHandler.createCommands();
        commands.forEach(Command::execute);

        assertEquals(2, commands.size());
        assertEquals(List.of("first", "second"), handled);
    }
}
