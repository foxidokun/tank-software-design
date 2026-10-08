package ru.mipt.bit.platformer.config;

import com.badlogic.gdx.math.GridPoint2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Fills the level from a text file: T - tree, X - player start position, _ - empty cell.
 * Example:
 * <pre>
 * ___T__T___
 * __TT__TTTT
 * _____X____
 * </pre>
 */
public class FileLevelProvider implements LevelProvider {

    private final String resourcePath;
    private final String treeTexturePath;

    public FileLevelProvider(String resourcePath, String treeTexturePath) {
        this.resourcePath = resourcePath;
        this.treeTexturePath = treeTexturePath;
    }

    @Override
    public Level provide() {
        return parse(readLines());
    }

    Level parse(List<String> lines) {
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Level file is empty");
        }

        GridPoint2 playerCoordinates = null;
        List<ObstacleSpec> obstacles = new ArrayList<>();

        for (int row = 0; row < lines.size(); row++) {
            String line = lines.get(row);
            int y = lines.size() - 1 - row;

            for (int x = 0; x < line.length(); x++) {
                char cell = line.charAt(x);
                if (cell == 'X') {
                    if (playerCoordinates != null) {
                        throw new IllegalArgumentException("Level must contain exactly one X");
                    }
                    playerCoordinates = new GridPoint2(x, y);
                } else if (cell == 'T') {
                    obstacles.add(new ObstacleSpec(treeTexturePath, x, y));
                } else if (cell != '_') {
                    throw new IllegalArgumentException("Unknown cell '" + cell + "' at line " + (row + 1));
                }
            }
        }

        if (playerCoordinates == null) {
            throw new IllegalArgumentException("Level must contain X with the player start position");
        }
        return new Level(playerCoordinates, obstacles);
    }

    private List<String> readLines() {
        try (InputStream stream = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (stream == null) {
                throw new IllegalArgumentException("Level file not found: " + resourcePath);
            }
            return new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))
                    .lines()
                    .toList();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to read level file: " + resourcePath, e);
        }
    }
}
