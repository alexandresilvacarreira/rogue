package pt.upskill.projeto1.objects.characters;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.interfaces.Combat;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Character superclass. Extended by Hero and Enemy. Two constructors are
 * provided in this class: one for objects of type Enemy, and an empty constructor
 * for Hero, which implements the Singleton pattern.
 * Two abstract methods are defined: move() for Character movement, and canMoveHere()
 * for checking valid tiles.
 *
 */

public abstract class Character implements ImageTile, Serializable {

    private int hp;
    private Position position;
    private int damage;

    public Character(int hp, Position position, int damage) {
        this.hp = hp;
        this.position = position;
        this.damage = damage;
    }

    //Empty constructor is needed to implement Singleton pattern for Hero
    public Character() {
    }

    public abstract void move(Position newPosition);
    public abstract boolean canMoveHere(ImageTile tile);

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position newPosition) {
        this.position = newPosition;
    }

    public int getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


}
