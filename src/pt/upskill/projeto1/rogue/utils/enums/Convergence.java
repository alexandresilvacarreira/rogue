package pt.upskill.projeto1.rogue.utils.enums;

/**
 *
 * Defines fistance from which enemies start converging towards hero
 * or try to teleport to its adjacent tiles (in the case of Warlock)
 *
 * */

public enum Convergence {

    DISTANCE(3),
    TELEPORT(5);


    private int distance;

    Convergence(int distance){
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }
}
