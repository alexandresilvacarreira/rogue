package pt.upskill.projeto1.objects.statusBar.Health;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines HealthRedGreen object.
 *
 */

public class HealthRedGreen implements ImageTile, Serializable {

    private Position position;

    public HealthRedGreen(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "RedGreen";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
