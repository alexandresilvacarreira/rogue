package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Potion object.
 *
 */

public class Potion extends Item {

    public Potion(Position position, int points) {
        super(position, points);
    }


    @Override
    public String getName() {
        return "Potion";
    }

    @Override
    public String toString() {
        return "Potion (restores full HP and fireballs)";
    }
}
