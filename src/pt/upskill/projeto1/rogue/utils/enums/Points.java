package pt.upskill.projeto1.rogue.utils.enums;

/**
 *
 * Defines points earned per action: movement, item pick up, enemy killed
 *
 * */

public enum Points {

    MOVE(-1),
    MEAT(10),
    POTION(50),
    SWORD(100),
    HAMMER(150),
    MACE(200),
    ENCHANTEDSWORD(250),
    KEY(100),
    BAT(50),
    SKELETON(100),
    BADGUY(200),
    WARLOCK(300),
    THIEF(250);

    private int points;
    Points(int points){
        this.points = points;
    }

    public int getPoints() {
        return points;
    }
}
