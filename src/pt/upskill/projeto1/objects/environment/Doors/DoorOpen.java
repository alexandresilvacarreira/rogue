package pt.upskill.projeto1.objects.environment.Doors;
/**
 *
 * Defines the DoorOpen object.
 *
 */
public class DoorOpen extends Door {

    public DoorOpen(int doorNumber, String nextRoomName, int nextRoomDoor) {
        super(doorNumber, nextRoomName, nextRoomDoor);
    }

    @Override
    public String getName() {
        return "DoorOpen";
    }

}
