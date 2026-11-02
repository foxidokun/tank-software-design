package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.Interpolation;
import org.junit.Test;
import ru.mipt.bit.platformer.model.Direction;

import java.util.EnumMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class GameConfigTest {

    @Test
    public void builderAppliesOverriddenParameters() {
        LevelProvider provider = new RandomLevelProvider(10, 8, 5, "images/greenTree.png");
        Map<Direction, int[]> bindings = new EnumMap<>(Direction.class);
        bindings.put(Direction.UP, new int[]{19});

        GameConfig config = GameConfig.builder()
                .levelPath("other.tmx")
                .windowWidth(640)
                .windowHeight(480)
                .movementSpeed(1f)
                .movementInterpolation(Interpolation.linear)
                .playerTexturePath("images/tank_green.png")
                .tankTexturePath("images/tank_red.png")
                .levelProvider(provider)
                .keyBindings(bindings)
                .build();

        assertEquals("other.tmx", config.getLevelPath());
        assertEquals(640, config.getWindowWidth());
        assertEquals(480, config.getWindowHeight());
        assertEquals(1f, config.getMovementSpeed(), 0f);
        assertEquals(Interpolation.linear, config.getMovementInterpolation());
        assertEquals("images/tank_green.png", config.getPlayerTexturePath());
        assertEquals("images/tank_red.png", config.getTankTexturePath());
        assertSame(provider, config.getLevelProvider());
        assertSame(bindings, config.getKeyBindings());
    }

    @Test
    public void builderDefaultsToFileLevelProvider() {
        GameConfig config = GameConfig.builder().build();

        assertTrue(config.getLevelProvider() instanceof FileLevelProvider);
    }
}
