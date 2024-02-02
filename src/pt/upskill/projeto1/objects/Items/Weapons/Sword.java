package pt.upskill.projeto1.objects.Items.Weapons;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Sword object.
 *
 */

public class Sword extends Weapon {

    public Sword(Position position, int points, int damage) {
        super(position, points, damage);
    }

    @Override
    public String getName() {
        return "Sword";
    }

}
