package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.interfaces.Interactible;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Superclass for items. Implements the interactible interface.
 * Method interact has the same implementations for all items except weapons.
 *
 */

public abstract class Item implements ImageTile, Interactible, Serializable {

    private Position position;
    private int points;
    private boolean pickedUp = false;

    public Item(Position position, int points) {
        this.position = position;
        this.points = points;
    }

    public int getPoints(){
        return this.points;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean wasPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void interact(Hero hero) {
        if (!hero.inventoryIsFull()) {
            hero.addToInventory(this);
            ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
            if (!this.wasPickedUp()) {
                this.setPickedUp(true);
                hero.updateScore(this.getPoints());
                gui.setStatus("Item pick up: " + this + " Points: +" + this.getPoints());
            } else {
                gui.setStatus("Item pick up: " + this);
            }
        }
    }

}
