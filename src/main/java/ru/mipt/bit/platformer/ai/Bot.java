package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MoveCommand;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Collection;

public class Bot {

    private final MovableEntity tank;
    private final MoveStrategy strategy;
    private final Collection<GameObject> obstacles;

    public Bot(MovableEntity tank, MoveStrategy strategy, Collection<GameObject> obstacles) {
        this.tank = tank;
        this.strategy = strategy;
        this.obstacles = obstacles;
    }

    public void update(float deltaTime, float speed) {
        new MoveCommand(tank, strategy.chooseDirection(), obstacles).execute();
        tank.update(deltaTime, speed);
    }
}
