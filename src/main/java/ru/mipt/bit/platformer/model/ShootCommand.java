package ru.mipt.bit.platformer.model;

public class ShootCommand implements Command {

    private final MovableEntity tank;

    public ShootCommand(MovableEntity tank) {
        this.tank = tank;
    }

    @Override
    public void execute() {
        tank.shoot();
    }
}
