package ru.mipt.bit.platformer.config;

/**
 * Fills the level with a player start position and obstacles.
 * A new way to fill a level is a new implementation, the game code stays untouched.
 */
public interface LevelProvider {

    Level provide();
}
