package pt.upskill.projeto1.objects.characters.enemies;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Skeleton enemy.
 *
 */

public class Skeleton extends Enemy {

    public Skeleton(int hp, Position position, int damage, int points) {
        super(hp, position, damage, points);
    }

    @Override
    public String getName() {
        return "Skeleton";
    }


}
