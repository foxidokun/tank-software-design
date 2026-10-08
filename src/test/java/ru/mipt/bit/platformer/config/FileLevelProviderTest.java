package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class FileLevelProviderTest {

    private final FileLevelProvider provider = new FileLevelProvider("level.txt", "images/greenTree.png");

    @Test
    public void parsesTreesAndPlayerPosition() {
        Level level = provider.parse(Arrays.asList("T__", "_X_"));

        assertEquals(new GridPoint2(1, 0), level.getPlayerCoordinates());
        assertEquals(1, level.getObstacles().size());
        assertEquals(new GridPoint2(0, 1), level.getObstacles().get(0).getCoordinates());
        assertEquals("images/greenTree.png", level.getObstacles().get(0).getTexturePath());
    }

    @Test
    public void readsBundledLevelFile() {
        Level level = provider.provide();

        assertEquals(new GridPoint2(5, 3), level.getPlayerCoordinates());
        assertEquals(15, level.getObstacles().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsWhenPlayerIsMissing() {
        provider.parse(Collections.singletonList("TT_T"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void failsOnUnknownCell() {
        provider.parse(Collections.singletonList("T?"));
    }
}
