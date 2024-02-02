package pt.upskill.projeto1.rogue.utils.enums;

/**
 *
 * Defines constants for chance of special attacks and drops
 *
 */

public enum Chance {

    STEAL(0.01),
    MEAT(0.1),
    TELEPORT(0.01),
    DROP(0.01);

    private double chance;

    Chance(double chance) {
        this.chance = chance;
    }

    public double getChance() {
        return chance;
    }
}
