package pt.upskill.projeto1.objects.environment;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;
/**
 *
 * Defines the Grass object.
 *
 */
public class Grass implements ImageTile, Serializable {

    private Position position;

    public Grass(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Grass";
    }

    @Override
    public Position getPosition() {
        return this.position;
    }
}
