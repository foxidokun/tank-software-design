package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.MovableEntity;

public class Bot {

    private final MovableEntity tank;
    private final BotStrategy strategy;

    public Bot(MovableEntity tank, BotStrategy strategy) {
        this.tank = tank;
        this.strategy = strategy;
    }

    public Command createCommand() {
        return strategy.nextCommand(tank);
    }
}
