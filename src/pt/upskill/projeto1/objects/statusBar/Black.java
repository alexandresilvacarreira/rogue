package pt.upskill.projeto1.objects.statusBar;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines Black object.
 *
 */

public class Black implements ImageTile, Serializable {

    private Position position;

    public Black(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Black";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
