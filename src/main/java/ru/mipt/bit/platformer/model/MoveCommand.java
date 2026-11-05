package ru.mipt.bit.platformer.model;

import java.util.Collection;

public class MoveCommand implements Command {

    private final MovableEntity tank;
    private final Direction direction;
    private final Collection<GameObject> obstacles;

    public MoveCommand(MovableEntity tank, Direction direction, Collection<GameObject> obstacles) {
        this.tank = tank;
        this.direction = direction;
        this.obstacles = obstacles;
    }

    @Override
    public void execute() {
        tank.tryMove(direction, obstacles);
    }
}
