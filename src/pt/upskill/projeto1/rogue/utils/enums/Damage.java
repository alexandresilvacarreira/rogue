package pt.upskill.projeto1.rogue.utils.enums;

import java.io.Serializable;

/**
 *
 * Defines damage dealt by hero, enemies and weapons
 *
 * */

public enum Damage implements Serializable {

    HERO(2),
    BAT(1),
    SKELETON(1),
    BADGUY(1),
    THIEF(1),
    WARLOCK(1),
    SWORD(4),
    HAMMER(8),
    MACE(6),
    ENCHANTEDSWORD(10),
    TRAP(1),
    FIREBALL(10);

    private int damage;

    Damage(int damage){
        this.damage = damage;
    }

    public int getDamage(){
        return this.damage;
    }

}
