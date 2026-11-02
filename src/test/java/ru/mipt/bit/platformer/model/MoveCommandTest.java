package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoveCommandTest {

    @Test
    public void executeMovesTankInCommandDirection() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8));
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f);

        new MoveCommand(tank, Direction.UP).execute();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void executeKeepsTankInPlaceWhenTargetCellIsOccupied() {
        GameLevel level = new GameLevel(new GridPoint2(10, 8));
        level.add(new GameObject(new GridPoint2(1, 0)));
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), level, 0.4f);

        new MoveCommand(tank, Direction.DOWN).execute();

        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
    }
}
