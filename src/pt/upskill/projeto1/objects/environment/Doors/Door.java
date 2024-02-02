package pt.upskill.projeto1.objects.environment.Doors;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.interfaces.Interactible;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rooms.RoomManager;

import java.io.Serializable;

/**
 *
 * Superclass for Door tiles. Implements Interactible interface, for changing rooms.
 *
 */

public abstract class Door implements ImageTile, Interactible, Serializable {

    private Position position;
    private int doorNumber;
    private String nextRoomName;
    private int nextRoomDoor;

    public Door(int doorNumber, String nextRoomName, int nextRoomDoor) {
        this.doorNumber = doorNumber;
        this.nextRoomName = nextRoomName;
        this.nextRoomDoor = nextRoomDoor;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDoorNumber() {
        return doorNumber;
    }

    public String getNextRoomName() {
        return nextRoomName;
    }

    public int getNextRoomDoor() {
        return nextRoomDoor;
    }

    @Override
    public Position getPosition() {
        return position;
    }

    //Door open and doorway act the same, door closed needs a different implementation of interact method
    @Override
    public void interact(Hero hero) {
        RoomManager roomManager = hero.getRoomManager();
        roomManager.changeRooms(this.getNextRoomName(), this.getNextRoomDoor());
    }

    @Override
    public String toString() {
        return "Door{" +
                "position=" + position +
                ", doorNumber=" + doorNumber +
                ", nextRoomName='" + nextRoomName + '\'' +
                ", nextRoomDoor=" + nextRoomDoor +
                '}';
    }

}
