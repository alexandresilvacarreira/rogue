package pt.upskill.projeto1.objects.statusBar.Health;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines HealthGreen object.
 *
 */

public class HealthGreen implements ImageTile, Serializable {

    private Position position;

    public HealthGreen(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Green";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
