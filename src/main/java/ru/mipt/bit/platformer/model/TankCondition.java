package ru.mipt.bit.platformer.model;

public enum TankCondition implements TankState {
    LIGHT(1f, true),
    MEDIUM(2f, true),
    HEAVY(3f, false);

    private static final float LIGHT_THRESHOLD = 0.7f;
    private static final float HEAVY_THRESHOLD = 0.15f;

    private final float movementDurationMultiplier;
    private final boolean canShoot;

    TankCondition(float movementDurationMultiplier, boolean canShoot) {
        this.movementDurationMultiplier = movementDurationMultiplier;
        this.canShoot = canShoot;
    }

    @Override
    public float movementDurationMultiplier() {
        return movementDurationMultiplier;
    }

    @Override
    public boolean canShoot() {
        return canShoot;
    }

    public static TankCondition forHealthFraction(float healthFraction) {
        if (healthFraction > LIGHT_THRESHOLD) {
            return LIGHT;
        }
        if (healthFraction >= HEAVY_THRESHOLD) {
            return MEDIUM;
        }
        return HEAVY;
    }
}
