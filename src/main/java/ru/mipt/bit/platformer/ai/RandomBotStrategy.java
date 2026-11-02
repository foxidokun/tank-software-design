package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Random;

public class RandomBotStrategy implements BotStrategy {

    private final Random random = new Random();

    @Override
    public Command nextCommand(MovableEntity tank) {
        Direction[] directions = Direction.values();
        return new MoveCommand(tank, directions[random.nextInt(directions.length)]);
    }
}
