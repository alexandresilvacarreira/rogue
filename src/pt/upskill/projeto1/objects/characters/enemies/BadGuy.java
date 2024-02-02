package pt.upskill.projeto1.objects.characters.enemies;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines BadGuy enemy.
 *
 */

public class BadGuy extends Enemy {

    public BadGuy(int hp, Position position, int damage, int points) {
        super(hp, position, damage, points);
    }

    @Override
    public String getName() {
        return "BadGuy";
    }
}
