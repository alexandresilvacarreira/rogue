package pt.upskill.projeto1.objects.Items;

import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Defines Key object.
 *
 */

public class Key extends Item {

    private String keyName;

    public Key(Position position, int points, String keyName) {
        super(position, points);
        this.keyName = keyName;
    }


    public String getKeyName() {
        return keyName;
    }

    @Override
    public String getName() {
        return "Key";
    }

    @Override
    public String toString() {
        return this.getName() + " (" + this.getKeyName() + ")";
    }
}
