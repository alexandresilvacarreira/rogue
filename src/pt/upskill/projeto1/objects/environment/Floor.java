package pt.upskill.projeto1.objects.environment;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
/**
 *
 * Defines the Floor object.
 *
 */
public class Floor implements ImageTile, Serializable {

    private Position position;

    public Floor(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Floor";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
