package ru.mipt.bit.platformer.model;

public interface TankState {

    float movementDurationMultiplier();

    boolean canShoot();
}
