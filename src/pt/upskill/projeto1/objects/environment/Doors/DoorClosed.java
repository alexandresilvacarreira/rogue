package pt.upskill.projeto1.objects.environment.Doors;

import pt.upskill.projeto1.objects.characters.Hero;

/**
 *
 * Defines the DoorClosed object
 *
 */

public class DoorClosed extends Door {

    private String keyName;

    public DoorClosed(int doorNumber, String nextRoomName, int nextRoomDoor, String keyName) {
        super(doorNumber, nextRoomName, nextRoomDoor);
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    @Override
    public void interact(Hero hero) {
        hero.tryToOpenDoor(this);
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }

}
