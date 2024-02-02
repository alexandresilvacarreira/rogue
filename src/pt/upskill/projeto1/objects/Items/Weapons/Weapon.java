package pt.upskill.projeto1.objects.Items.Weapons;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Superclass for weapons. Implements Comparable interface to sort weapons
 * according to damage.
 *
 */

public abstract class Weapon extends Item implements Comparable<Weapon> {

    private int damage;

    public Weapon(Position position, int points, int damage) {
        super(position, points);
        this.damage = damage;
    }

    public int getDamage(){
        return this.damage;
    };

    @Override
    public void interact(Hero hero) {
        if (!hero.inventoryIsFull()) {
            hero.addToInventory(this);
            hero.updateDamage();
            ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
            //Only update score once per item pickup
            if (!this.wasPickedUp()) {
                this.setPickedUp(true);
                hero.updateScore(this.getPoints());
                gui.setStatus("Item pick up: " + this + " Points: +" + this.getPoints());
            } else {
                gui.setStatus("Item pick up: " + this);
            }

        }
    }

    @Override
    public int compareTo(Weapon weapon) {
        if (this.getDamage() == weapon.getDamage())
            return 0;
        else if (this.getDamage() > weapon.getDamage()) {
            return -1;
        }
        else return 1;
    }

    @Override
    public String toString() {
        return this.getName() + " (Damage: +" + this.getDamage() + ")";
    }
}
