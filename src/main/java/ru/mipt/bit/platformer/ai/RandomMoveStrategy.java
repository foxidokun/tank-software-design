package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Direction;

import java.util.Random;

public class RandomMoveStrategy implements MoveStrategy {

    private final Random random = new Random();

    @Override
    public Direction chooseDirection() {
        Direction[] directions = Direction.values();
        return directions[random.nextInt(directions.length)];
    }
}
