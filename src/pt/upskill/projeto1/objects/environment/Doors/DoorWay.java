package pt.upskill.projeto1.objects.environment.Doors;
/**
 *
 * Defines the DoorWay object.
 *
 */
public class DoorWay extends Door {

    public DoorWay(int doorNumber, String nextRoomName, int nextRoomDoor) {
        super(doorNumber, nextRoomName, nextRoomDoor);
    }

    @Override
    public String getName() {
        return "DoorWay";
    }

}
