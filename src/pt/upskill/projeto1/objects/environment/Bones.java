package pt.upskill.projeto1.objects.environment;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines Bones object.
 *
 */
public class Bones implements ImageTile, Serializable {

    private Position position;

    public Bones(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Bones";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
