package ru.mipt.bit.platformer.input;

import java.util.ArrayList;
import java.util.List;

public class InputHandler {

    private final List<ButtonHandler> handlers = new ArrayList<>();

    public void add(ButtonHandler handler) {
        handlers.add(handler);
    }

    public void handle() {
        for (ButtonHandler handler : handlers) {
            handler.handle();
        }
    }
}
