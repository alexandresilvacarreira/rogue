package pt.upskill.projeto1.objects.Items.Weapons;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Mace object.
 *
 */

public class Mace extends Weapon {

    public Mace(Position position, int points, int damage) {
        super(position, points, damage);
    }

    @Override
    public String getName() {
        return "Mace";
    }

}
