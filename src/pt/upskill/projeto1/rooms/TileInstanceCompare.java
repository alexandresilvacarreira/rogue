package pt.upskill.projeto1.rooms;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.characters.enemies.Enemy;
import pt.upskill.projeto1.objects.environment.Trap;

import java.util.Comparator;

/**
 * Comparator for sorting ImageTiles according to type, so they are displayed
 * correctly on the GUI. Implemented as an external comparator since ImageTile
 * is an interface (otherwise this could be done through an implementation
 * of Comparable)
 */

public class TileInstanceCompare implements Comparator<ImageTile> {

    @Override
    public int compare(ImageTile tile1, ImageTile tile2) {
        //Hero always on top, then enemies, then everything else
        if (tile1 instanceof Hero)
            return 1;
        else if (tile2 instanceof Hero)
            return -1;
        else if (tile1 instanceof Enemy)
            return 1;
        else if (tile2 instanceof Enemy)
            return -1;
        else if (tile1 instanceof Trap)
            return 1;
        else if (tile2 instanceof Trap)
            return -1;
        else
            return 0;
    }
}
