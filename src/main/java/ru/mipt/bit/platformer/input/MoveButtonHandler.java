package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Gdx;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Collection;

public class MoveButtonHandler implements ButtonHandler {

    private final int[] keyCodes;
    private final Direction direction;
    private final MovableEntity player;
    private final Collection<GameObject> obstacles;

    public MoveButtonHandler(Direction direction, MovableEntity player, Collection<GameObject> obstacles, int... keyCodes) {
        this.direction = direction;
        this.player = player;
        this.obstacles = obstacles;
        this.keyCodes = keyCodes;
    }

    @Override
    public void handle() {
        for (int keyCode : keyCodes) {
            if (Gdx.input.isKeyPressed(keyCode)) {
                player.tryMove(direction, obstacles);
                return;
            }
        }
    }
}
