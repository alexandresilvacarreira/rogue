package pt.upskill.projeto1.rogue.utils.enums;

import java.io.Serializable;

/**
 *
 * Defines HP of hero, enemies and health regen items
 *
 * */

public enum Hp implements Serializable {

    HERO(8),
    BAT(2),
    SKELETON(4),
    BADGUY(4),
    WARLOCK(15),
    THIEF(4),
    MEAT(4),
    POTION(8);

    private int hp;

    Hp(int hp){
        this.hp = hp;
    }

    public int getHp(){
        return this.hp;
    }
}
