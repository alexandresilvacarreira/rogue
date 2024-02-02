package pt.upskill.projeto1.objects.characters.enemies;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines Bat enemy.
 *
 */

public class Bat extends Enemy {


    public Bat(int hp, Position position, int damage, int points) {
        super(hp, position, damage, points);
    }

    @Override
    public String getName() {
        return "Bat";
    }
}
