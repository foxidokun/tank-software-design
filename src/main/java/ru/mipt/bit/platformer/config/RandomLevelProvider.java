package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Fills the level with a random player start position and random obstacles
 * placed on distinct empty cells. The grid size comes from the constructor
 * and has to match the tile map the game is rendered on.
 */
public class RandomLevelProvider implements LevelProvider {

    private final int width;
    private final int height;
    private final int obstacleCount;
    private final String treeTexturePath;
    private final Random random = new Random();

    public RandomLevelProvider(int width, int height, int obstacleCount, String treeTexturePath) {
        this.width = width;
        this.height = height;
        this.obstacleCount = obstacleCount;
        this.treeTexturePath = treeTexturePath;
    }

    @Override
    public Level provide() {
        List<GridPoint2> cells = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                cells.add(new GridPoint2(x, y));
            }
        }
        Collections.shuffle(cells, random);

        GridPoint2 playerCoordinates = cells.get(0);

        List<ObstacleSpec> obstacles = new ArrayList<>();
        int count = Math.min(obstacleCount, cells.size() - 1);
        for (int i = 1; i <= count; i++) {
            obstacles.add(new ObstacleSpec(treeTexturePath, cells.get(i)));
        }

        return new Level(playerCoordinates, obstacles);
    }
}
