package pt.upskill.projeto1.objects.statusBar.Health;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines HealthRed object.
 *
 */

public class HealthRed implements ImageTile, Serializable {

    private Position position;

    public HealthRed(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Red";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
