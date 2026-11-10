package ru.mipt.bit.platformer.graphics;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ToggleHealthBarsCommandTest {

    @Test
    public void executeSwitchesHealthBarDisplayOnAndOff() {
        HealthBarDisplay display = new HealthBarDisplay();
        ToggleHealthBarsCommand command = new ToggleHealthBarsCommand(display);

        assertFalse(display.isVisible());

        command.execute();
        assertTrue(display.isVisible());

        command.execute();
        assertFalse(display.isVisible());
    }
}
