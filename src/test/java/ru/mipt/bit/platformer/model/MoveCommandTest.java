package ru.mipt.bit.platformer.model;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class MoveCommandTest {

    @Test
    public void executeMovesTankInCommandDirection() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), new GridPoint2(10, 8));

        new MoveCommand(tank, Direction.UP, Collections.emptyList()).execute();

        assertEquals(new GridPoint2(1, 2), tank.getDestinationCoordinates());
    }

    @Test
    public void executeKeepsTankInPlaceWhenTargetCellIsOccupied() {
        MovableEntity tank = new MovableEntity(new GridPoint2(1, 1), new GridPoint2(10, 8));

        new MoveCommand(tank, Direction.DOWN, Collections.singletonList(new GameObject(new GridPoint2(1, 0)))).execute();

        assertEquals(new GridPoint2(1, 1), tank.getDestinationCoordinates());
    }
}
