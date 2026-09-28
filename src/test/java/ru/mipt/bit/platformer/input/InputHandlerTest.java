package ru.mipt.bit.platformer.input;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class InputHandlerTest {

    @Test
    public void handleInvokesAllRegisteredHandlers() {
        InputHandler inputHandler = new InputHandler();
        List<String> handled = new ArrayList<>();
        inputHandler.add(() -> handled.add("first"));
        inputHandler.add(() -> handled.add("second"));

        inputHandler.handle();

        assertEquals(Arrays.asList("first", "second"), handled);
    }
}
