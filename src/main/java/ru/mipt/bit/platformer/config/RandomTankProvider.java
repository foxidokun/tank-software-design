package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Level provider that adds randomly placed tanks on the empty cells of the level
 * built by another provider, so every level source gets its tanks.
 */
public class RandomTankProvider implements LevelProvider {

    private final LevelProvider delegate;
    private final int tankCount;
    private final Random random = new Random();

    public RandomTankProvider(LevelProvider delegate, int tankCount) {
        this.delegate = delegate;
        this.tankCount = tankCount;
    }

    @Override
    public Level provide() {
        Level level = delegate.provide();

        List<GridPoint2> freeCells = new ArrayList<>();
        for (int x = 0; x < level.getSize().x; x++) {
            for (int y = 0; y < level.getSize().y; y++) {
                freeCells.add(new GridPoint2(x, y));
            }
        }
        freeCells.remove(level.getPlayerCoordinates());
        freeCells.removeAll(level.getObstacleCoordinates());
        freeCells.removeAll(level.getTankCoordinates());
        Collections.shuffle(freeCells, random);

        List<GridPoint2> tankCoordinates = new ArrayList<>(level.getTankCoordinates());
        int count = Math.min(tankCount, freeCells.size());
        for (int i = 0; i < count; i++) {
            tankCoordinates.add(freeCells.get(i));
        }

        return new Level(level.getPlayerCoordinates(), tankCoordinates, level.getObstacleCoordinates(), level.getSize());
    }
}
