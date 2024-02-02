package pt.upskill.projeto1.objects.environment;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
/**
 *
 * Defines the Wall object.
 *
 */
public class Wall implements ImageTile, Serializable {

    private Position position;

    public Wall(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Wall";
    }

    @Override
    public Position getPosition() {
        return position;
    }

}
