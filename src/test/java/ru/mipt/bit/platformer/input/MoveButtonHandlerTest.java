package ru.mipt.bit.platformer.input;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;
import ru.mipt.bit.platformer.model.GameObject;
import ru.mipt.bit.platformer.model.MovableEntity;

import java.util.Collection;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MoveButtonHandlerTest {

    @Test
    public void pressedKeyMovesPlayerInBoundDirection() {
        MovableEntity player = new MovableEntity(new GridPoint2(1, 1));
        KeyStateProvider pressedKeys = keyCode -> keyCode == Keys.W;
        MoveButtonHandler handler = new MoveButtonHandler(Direction.UP, player, noObstacles(), pressedKeys, Keys.UP, Keys.W);

        handler.handle();

        assertEquals(new GridPoint2(1, 2), player.getDestinationCoordinates());
    }

    @Test
    public void noPressedKeyKeepsPlayerInPlace() {
        MovableEntity player = new MovableEntity(new GridPoint2(1, 1));
        KeyStateProvider pressedKeys = keyCode -> false;
        MoveButtonHandler handler = new MoveButtonHandler(Direction.LEFT, player, noObstacles(), pressedKeys, Keys.LEFT, Keys.A);

        handler.handle();

        assertEquals(new GridPoint2(1, 1), player.getDestinationCoordinates());
    }

    private static Collection<GameObject> noObstacles() {
        return Collections.emptyList();
    }
}
