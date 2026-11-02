package ru.mipt.bit.platformer.model;

public class MoveCommand implements Command {

    private final MovableEntity tank;
    private final Direction direction;

    public MoveCommand(MovableEntity tank, Direction direction) {
        this.tank = tank;
        this.direction = direction;
    }

    @Override
    public void execute() {
        tank.tryMove(direction);
    }
}
