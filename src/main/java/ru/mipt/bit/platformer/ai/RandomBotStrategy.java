package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;
import ru.mipt.bit.platformer.model.ShootCommand;

import java.util.Random;

public class RandomBotStrategy implements BotStrategy {

    private static final int SHOOT_CHANCE = 4;

    private final Random random = new Random();

    @Override
    public Command nextCommand(MovableEntity tank) {
        if (random.nextInt(SHOOT_CHANCE) == 0) {
            return new ShootCommand(tank);
        }
        Direction[] directions = Direction.values();
        return new MoveCommand(tank, directions[random.nextInt(directions.length)]);
    }
}
