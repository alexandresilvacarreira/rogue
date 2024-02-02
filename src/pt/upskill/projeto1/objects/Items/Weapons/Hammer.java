package pt.upskill.projeto1.objects.Items.Weapons;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Hammer object.
 *
 */

public class Hammer extends Weapon {

    public Hammer(Position position, int points, int damage) {
        super(position, points, damage);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

}
