package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.Direction;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Tunable parameters of the game. Everything that used to be a hardcoded constant
 * inside {@code GameDesktopLauncher} now lives here, so a level, a set of obstacles,
 * the movement speed or the key layout can be changed without editing game code.
 */
public class GameConfig {

    private String levelPath = "level.tmx";
    private int windowWidth = 1280;
    private int windowHeight = 1024;
    private float movementSpeed = 0.4f;
    private Interpolation movementInterpolation = Interpolation.smooth;

    private GridPoint2 playerCoordinates = new GridPoint2(1, 1);
    private String playerTexturePath = "images/tank_blue.png";

    private List<ObstacleSpec> obstacles = Collections.singletonList(
            new ObstacleSpec("images/greenTree.png", 1, 3));

    private Map<Direction, int[]> keyBindings = defaultKeyBindings();

    private static Map<Direction, int[]> defaultKeyBindings() {
        Map<Direction, int[]> bindings = new EnumMap<>(Direction.class);
        bindings.put(Direction.UP, new int[]{Keys.UP, Keys.W});
        bindings.put(Direction.LEFT, new int[]{Keys.LEFT, Keys.A});
        bindings.put(Direction.DOWN, new int[]{Keys.DOWN, Keys.S});
        bindings.put(Direction.RIGHT, new int[]{Keys.RIGHT, Keys.D});
        return bindings;
    }

    public String getLevelPath() {
        return levelPath;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public float getMovementSpeed() {
        return movementSpeed;
    }

    public Interpolation getMovementInterpolation() {
        return movementInterpolation;
    }

    public GridPoint2 getPlayerCoordinates() {
        return new GridPoint2(playerCoordinates);
    }

    public String getPlayerTexturePath() {
        return playerTexturePath;
    }

    public List<ObstacleSpec> getObstacles() {
        return obstacles;
    }

    public Map<Direction, int[]> getKeyBindings() {
        return keyBindings;
    }
}
