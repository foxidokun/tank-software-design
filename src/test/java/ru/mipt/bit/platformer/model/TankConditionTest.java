package ru.mipt.bit.platformer.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TankConditionTest {

    @Test
    public void healthAbove70PercentIsLight() {
        assertEquals(TankCondition.LIGHT, TankCondition.forHealthFraction(1f));
        assertEquals(TankCondition.LIGHT, TankCondition.forHealthFraction(0.71f));
    }

    @Test
    public void healthBetween15And70PercentIsMedium() {
        assertEquals(TankCondition.MEDIUM, TankCondition.forHealthFraction(0.7f));
        assertEquals(TankCondition.MEDIUM, TankCondition.forHealthFraction(0.5f));
        assertEquals(TankCondition.MEDIUM, TankCondition.forHealthFraction(0.15f));
    }

    @Test
    public void healthBelow15PercentIsHeavy() {
        assertEquals(TankCondition.HEAVY, TankCondition.forHealthFraction(0.14f));
    }

    @Test
    public void mediumDoublesMovementDurationAndHeavyTriplesIt() {
        assertEquals(2f, TankCondition.MEDIUM.movementDurationMultiplier(), 0f);
        assertEquals(3f, TankCondition.HEAVY.movementDurationMultiplier(), 0f);
    }

    @Test
    public void onlyHeavyTankCannotShoot() {
        assertTrue(TankCondition.LIGHT.canShoot());
        assertTrue(TankCondition.MEDIUM.canShoot());
        assertFalse(TankCondition.HEAVY.canShoot());
    }
}
