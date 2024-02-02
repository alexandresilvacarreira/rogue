package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.enums.Hp;

/**
 *
 * Defines Meat object.
 *
 */

public class Meat extends Item {

    public Meat(Position position, int points) {
        super(position, points);
    }

    @Override
    public String getName() {
        return "GoodMeat";
    }

    @Override
    public String toString() {
        return "Meat (restores " + Hp.MEAT.getHp() + " HP)";
    }
}
