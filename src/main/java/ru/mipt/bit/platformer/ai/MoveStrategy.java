package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Direction;

public interface MoveStrategy {

    Direction chooseDirection();
}
