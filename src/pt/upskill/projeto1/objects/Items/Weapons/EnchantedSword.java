package pt.upskill.projeto1.objects.Items.Weapons;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines EnchantedSword object.
 *
 */

public class EnchantedSword extends Weapon{

    public EnchantedSword(Position position, int points, int damage) {
        super(position, points, damage);
    }

    @Override
    public String getName() {
        return "EnchantedSword";
    }

}
