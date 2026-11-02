package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Interpolation;
import ru.mipt.bit.platformer.model.Direction;

import java.util.EnumMap;
import java.util.Map;

/**
 * Tunable parameters of the game. Everything that used to be a hardcoded constant
 * inside {@code GameDesktopLauncher} now lives here, so a level, a set of obstacles,
 * the movement speed or the key layout can be changed without editing game code.
 */
public class GameConfig {

    private final String levelPath;
    private final int windowWidth;
    private final int windowHeight;
    private final float movementSpeed;
    private final Interpolation movementInterpolation;
    private final String playerTexturePath;
    private final String tankTexturePath;
    private final LevelProvider levelProvider;
    private final Map<Direction, int[]> keyBindings;

    private GameConfig(Builder builder) {
        this.levelPath = builder.levelPath;
        this.windowWidth = builder.windowWidth;
        this.windowHeight = builder.windowHeight;
        this.movementSpeed = builder.movementSpeed;
        this.movementInterpolation = builder.movementInterpolation;
        this.playerTexturePath = builder.playerTexturePath;
        this.tankTexturePath = builder.tankTexturePath;
        this.levelProvider = builder.levelProvider;
        this.keyBindings = builder.keyBindings;
    }

    public static Builder builder() {
        return new Builder();
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

    public String getPlayerTexturePath() {
        return playerTexturePath;
    }

    public String getTankTexturePath() {
        return tankTexturePath;
    }

    public LevelProvider getLevelProvider() {
        return levelProvider;
    }

    public Map<Direction, int[]> getKeyBindings() {
        return keyBindings;
    }

    public static class Builder {

        private String levelPath = "level.tmx";
        private int windowWidth = 1280;
        private int windowHeight = 1024;
        private float movementSpeed = 0.4f;
        private Interpolation movementInterpolation = Interpolation.smooth;
        private String playerTexturePath = "images/tank_blue.png";
        private String tankTexturePath = "images/tank_blue.png";
        private LevelProvider levelProvider = new FileLevelProvider("level.txt", "images/greenTree.png");
        private Map<Direction, int[]> keyBindings = defaultKeyBindings();

        public Builder levelPath(String levelPath) {
            this.levelPath = levelPath;
            return this;
        }

        public Builder windowWidth(int windowWidth) {
            this.windowWidth = windowWidth;
            return this;
        }

        public Builder windowHeight(int windowHeight) {
            this.windowHeight = windowHeight;
            return this;
        }

        public Builder movementSpeed(float movementSpeed) {
            this.movementSpeed = movementSpeed;
            return this;
        }

        public Builder movementInterpolation(Interpolation movementInterpolation) {
            this.movementInterpolation = movementInterpolation;
            return this;
        }

        public Builder playerTexturePath(String playerTexturePath) {
            this.playerTexturePath = playerTexturePath;
            return this;
        }

        public Builder tankTexturePath(String tankTexturePath) {
            this.tankTexturePath = tankTexturePath;
            return this;
        }

        public Builder levelProvider(LevelProvider levelProvider) {
            this.levelProvider = levelProvider;
            return this;
        }

        public Builder keyBindings(Map<Direction, int[]> keyBindings) {
            this.keyBindings = keyBindings;
            return this;
        }

        public GameConfig build() {
            return new GameConfig(this);
        }

        private static Map<Direction, int[]> defaultKeyBindings() {
            Map<Direction, int[]> bindings = new EnumMap<>(Direction.class);
            bindings.put(Direction.UP, new int[]{Keys.UP, Keys.W});
            bindings.put(Direction.LEFT, new int[]{Keys.LEFT, Keys.A});
            bindings.put(Direction.DOWN, new int[]{Keys.DOWN, Keys.S});
            bindings.put(Direction.RIGHT, new int[]{Keys.RIGHT, Keys.D});
            return bindings;
        }
    }
}
