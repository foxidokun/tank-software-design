package ru.mipt.bit.platformer.ai;

import ru.mipt.bit.platformer.model.Command;
import ru.mipt.bit.platformer.model.MovableEntity;

public interface BotStrategy {

    Command nextCommand(MovableEntity tank);
}
