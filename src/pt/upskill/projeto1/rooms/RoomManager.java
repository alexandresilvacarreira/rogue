package pt.upskill.projeto1.rooms;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Items.Potion;
import pt.upskill.projeto1.objects.Items.Weapons.EnchantedSword;
import pt.upskill.projeto1.objects.Items.Weapons.Mace;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.characters.enemies.*;
import pt.upskill.projeto1.objects.environment.*;
import pt.upskill.projeto1.objects.environment.Doors.Door;
import pt.upskill.projeto1.objects.environment.Doors.DoorClosed;
import pt.upskill.projeto1.objects.environment.Doors.DoorOpen;
import pt.upskill.projeto1.objects.environment.Doors.DoorWay;
import pt.upskill.projeto1.objects.Items.Weapons.Hammer;
import pt.upskill.projeto1.objects.Items.Key;
import pt.upskill.projeto1.objects.Items.Meat;
import pt.upskill.projeto1.objects.Items.Weapons.Sword;
import pt.upskill.projeto1.rogue.utils.enums.Damage;
import pt.upskill.projeto1.rogue.utils.enums.Hp;
import pt.upskill.projeto1.rogue.utils.enums.Points;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 *
 * Defines RoomManager object. This class provides a method for
 * building rooms based on text files, as well as for managing room changes.
 *
 */

public class RoomManager implements Serializable {

    private HashMap<String, Room> rooms = new HashMap<>();

    public void updateRooms(Room room) {
        this.rooms.put(room.getName(), room);
    }

    public Room buildRoom(File roomFile) throws FileNotFoundException {
        List<Enemy> enemies = new ArrayList<>();
        List<ImageTile> roomTiles = new ArrayList<>();
        List<Door> doors = new ArrayList<>();
        String keyInRoom = "";
        Scanner scanner = new Scanner(roomFile);
        int i = 0;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            //Read comments and update doors list and key
            if (line.charAt(0) == '#') {
                String[] comments = line.split(" ");
                if (comments.length > 1) {
                    if (isNumber(comments[1])) {
                        if (comments.length == 6) {
                            Door doorClosed = new DoorClosed(Integer.parseInt(comments[1]), comments[3], Integer.parseInt(comments[4]), comments[5]);
                            doors.add(doorClosed);
                        } else if (comments[2].equals("D")) {
                            Door doorOpen = new DoorOpen(Integer.parseInt(comments[1]), comments[3], Integer.parseInt(comments[4]));
                            doors.add(doorOpen);
                        } else if (comments[2].equals("E")) {
                            Door doorWay = new DoorWay(Integer.parseInt(comments[1]), comments[3], Integer.parseInt(comments[4]));
                            doors.add(doorWay);
                        }
                    } else if (comments[1].equals("k")) {
                        keyInRoom = comments[2];
                    }
                }
            } else {
                //Build room tiles lists - floors, walls, doors, enemies, items, etc.
                String[] columns = line.split("");
                for (int j = 0; j < 10; j++) {
                    //Only now can we place the doors
                    if (isNumber(columns[j])) {
                        Position doorPos = new Position(j, i);
                        int doorIndex = Integer.parseInt(columns[j]);
                        Door doorToPlace = doors.get(doorIndex);
                        doorToPlace.setPosition(doorPos);
                        roomTiles.add(doorToPlace);
                    } else {
                        switch (columns[j]) {
                            case (" "):
                                Floor floor = new Floor(new Position(j, i));
                                roomTiles.add(floor);
                                break;
                            case ("W"):
                                Position wallPos = new Position(j, i);
                                Wall wall = new Wall(wallPos);
                                roomTiles.add(wall);
                                break;
                            case ("S"):
                                Position skelPos = new Position(j, i);
                                Skeleton skeleton = new Skeleton(Hp.SKELETON.getHp(), skelPos, Damage.SKELETON.getDamage(), Points.SKELETON.getPoints());
                                roomTiles.add(new Floor(skelPos));
                                enemies.add(skeleton);
                                break;
                            case ("T"):
                                Position thiefPos = new Position(j, i);
                                Thief thief = new Thief(Hp.THIEF.getHp(), thiefPos, Damage.THIEF.getDamage(), Points.THIEF.getPoints());
                                roomTiles.add(new Floor(thiefPos));
                                enemies.add(thief);
                                break;
                            case ("B"):
                                Position batPos = new Position(j, i);
                                Bat bat = new Bat(Hp.BAT.getHp(), batPos, Damage.BAT.getDamage(), Points.BAT.getPoints());
                                roomTiles.add(new Floor(batPos));
                                enemies.add(bat);
                                break;
                            case ("G"):
                                Position guyPos = new Position(j, i);
                                BadGuy badGuy = new BadGuy(Hp.BADGUY.getHp(), guyPos, Damage.BADGUY.getDamage(), Points.BADGUY.getPoints());
                                ;
                                roomTiles.add(new Floor(guyPos));
                                enemies.add(badGuy);
                                break;
                            case ("R"):
                                Position warlockPos = new Position(j, i);
                                Warlock warlock = new Warlock(Hp.WARLOCK.getHp(), warlockPos, Damage.WARLOCK.getDamage(), Points.WARLOCK.getPoints());
                                ;
                                roomTiles.add(new Floor(warlockPos));
                                enemies.add(warlock);
                                break;
                            case ("k"):
                                Position keyPos = new Position(j, i);
                                Key key = new Key(keyPos, Points.KEY.getPoints(), keyInRoom);
                                roomTiles.add(new Floor(keyPos));
                                roomTiles.add(key);
                                break;
                            case ("m"):
                                Position meatPos = new Position(j, i);
                                roomTiles.add(new Floor(meatPos));
                                roomTiles.add(new Meat(meatPos, Points.MEAT.getPoints()));
                                break;
                            case ("s"):
                                Position swordPos = new Position(j, i);
                                roomTiles.add(new Floor(swordPos));
                                roomTiles.add(new Sword(swordPos, Points.SWORD.getPoints(), Damage.SWORD.getDamage()));
                                break;
                            case ("e"):
                                Position enchantedPos = new Position(j, i);
                                roomTiles.add(new Floor(enchantedPos));
                                roomTiles.add(new EnchantedSword(enchantedPos, Points.ENCHANTEDSWORD.getPoints(), Damage.ENCHANTEDSWORD.getDamage()));
                                break;
                            case ("h"):
                                Position hammerPos = new Position(j, i);
                                roomTiles.add(new Floor(hammerPos));
                                roomTiles.add(new Hammer(hammerPos, Points.HAMMER.getPoints(), Damage.HAMMER.getDamage()));
                                break;
                            case ("l"):
                                Position macePos = new Position(j, i);
                                roomTiles.add(new Floor(macePos));
                                roomTiles.add(new Mace(macePos, Points.MACE.getPoints(), Damage.MACE.getDamage()));
                                break;
                            case ("g"):
                                Position grassPos = new Position(j, i);
                                roomTiles.add(new Floor(grassPos));
                                roomTiles.add(new Grass(grassPos));
                                break;
                            case ("p"):
                                Position potionPos = new Position(j, i);
                                roomTiles.add(new Floor(potionPos));
                                roomTiles.add(new Potion(potionPos, Points.POTION.getPoints()));
                                break;
                            case ("t"):
                                Position trapPos = new Position(j, i);
                                roomTiles.add(new Trap(trapPos));
                                break;
                            case ("c"):
                                Position chestPos = new Position(j, i);
                                roomTiles.add(new Floor(chestPos));
                                roomTiles.add(new Chest(chestPos));
                                break;
                            case ("H"):
                                Hero hero = Hero.getInstance();
                                Position heroPos = new Position(j, i);
                                roomTiles.add(new Floor(heroPos));
                                hero.setPosition(heroPos);
                                roomTiles.add(hero);
                                break;
                        }
                    }
                }
                i++;
            }
        }
        enemies.forEach(enemy -> roomTiles.add(enemy));
        Room room = new Room(roomFile.getName(), enemies, roomTiles, doors);
        //Hero must come on top of the tiles list, then enemies, then items
        TileInstanceCompare tileInstanceCompare = new TileInstanceCompare();
        Collections.sort(room.getRoomTiles(), tileInstanceCompare);
        updateRooms(room);
        return room;
    }

    public boolean isNumber(String s) {
        try {
            Integer integer = Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public HashMap<String, Room> getRooms() {
        return rooms;
    }

    public void changeRooms(String newRoomName, int entranceDoorNumber) {
        Hero hero = Hero.getInstance();
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Room newRoom = getRooms().get(newRoomName);
        Door entranceDoor = null;
        for (Door d :
                newRoom.getDoors()) {
            if (d.getDoorNumber() == entranceDoorNumber) {
                entranceDoor = d;
                break;
            }
        }
        if (entranceDoor == null) throw new NullPointerException("No door found, check map file");
        //Clear hero from the room it is leaving
        hero.getCurrentRoom().clearTile(hero);
        //Set hero to new room
        hero.setCurrentRoom(newRoom);
        hero.setPosition(entranceDoor.getPosition());
        //Add hero tile to newRoom
        newRoom.getRoomTiles().add(hero);
        gui.clearImages();
        gui.newImages(newRoom.getRoomTiles());
    }

    //Open both the current room door and the one it leads to
    public void openDoors(Room room, DoorClosed closedDoor) {
        openDoor(room, closedDoor, true);
        Room nextRoom = getRooms().get(closedDoor.getNextRoomName());
        int nextRoomDoorNumber = closedDoor.getNextRoomDoor();
        for (Door d :
                nextRoom.getDoors()) {
            if (d.getDoorNumber() == nextRoomDoorNumber) {
                openDoor(nextRoom, (DoorClosed) d, false);
                break;
            }
        }
    }

    public void openDoor(Room room, DoorClosed closedDoor, boolean isCurrentRoom) {
        DoorOpen openedDoor = new DoorOpen(closedDoor.getDoorNumber(), closedDoor.getNextRoomName(), closedDoor.getNextRoomDoor());
        openedDoor.setPosition(closedDoor.getPosition());
        room.getRoomTiles().remove(closedDoor);
        room.getDoors().remove(closedDoor);
        room.getRoomTiles().add(openedDoor);
        //Hero must come on top of the tiles list, then enemies, then items
        TileInstanceCompare tileInstanceCompare = new TileInstanceCompare();
        Collections.sort(room.getRoomTiles(), tileInstanceCompare);
        room.getDoors().add(openedDoor);
        if (isCurrentRoom) {
            ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
            gui.removeImage(closedDoor);
            gui.addImage(openedDoor);
            //If I don't add it, it won't render before room change
            gui.addImage(Hero.getInstance());
        }

    }
}
