package ru.mipt.bit.platformer.graphics;

import ru.mipt.bit.platformer.model.Command;

public class ToggleHealthBarsCommand implements Command {

    private final HealthBarDisplay healthBarDisplay;

    public ToggleHealthBarsCommand(HealthBarDisplay healthBarDisplay) {
        this.healthBarDisplay = healthBarDisplay;
    }

    @Override
    public void execute() {
        healthBarDisplay.toggle();
    }
}
