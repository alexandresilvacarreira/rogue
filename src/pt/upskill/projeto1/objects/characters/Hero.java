package pt.upskill.projeto1.objects.characters;

import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.Items.Meat;
import pt.upskill.projeto1.objects.Items.Potion;
import pt.upskill.projeto1.objects.characters.enemies.Thief;
import pt.upskill.projeto1.objects.environment.*;
import pt.upskill.projeto1.objects.interfaces.Combat;
import pt.upskill.projeto1.rooms.Room;
import pt.upskill.projeto1.rooms.RoomManager;
import pt.upskill.projeto1.game.FireBallThread;
import pt.upskill.projeto1.game.GameManager;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.enemies.Enemy;
import pt.upskill.projeto1.objects.environment.Doors.Door;
import pt.upskill.projeto1.objects.environment.Doors.DoorClosed;
import pt.upskill.projeto1.objects.environment.Doors.DoorOpen;
import pt.upskill.projeto1.objects.environment.Doors.DoorWay;
import pt.upskill.projeto1.objects.statusBar.Fire.Fireball;
import pt.upskill.projeto1.objects.Items.Key;
import pt.upskill.projeto1.objects.Items.Weapons.Weapon;
import pt.upskill.projeto1.objects.statusBar.StatusBar;
import pt.upskill.projeto1.rogue.utils.enums.Damage;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.enums.Hp;
import pt.upskill.projeto1.rogue.utils.enums.Points;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Hero class. Hero is the centerpiece of the game, through which the game state is
 * dictated (i.e., how and which tiles are to be displayed: rooms to be painted, enemy
 * movements, item pickups/drops, doors to be opened, etc.).
 * Hero extends from the Character superclass and implements the Combat interface.
 * For saving and loading the game, this is the only object that is serialized/de-serialized.
 * Hero implements the Singleton pattern and therefore a readResolve() method is provided for proper
 * de-serialization, otherwise the pattern is violated.
 */

public class Hero extends Character implements Combat<Enemy> {

    private static final Hero INSTANCE = new Hero();
    private RoomManager roomManager = new RoomManager();
    private Room currentRoom;
    private StatusBar statusBar;
    private Direction currentDirection;
    private Item[] inventory = new Item[3];
    private List<Weapon> weaponsWielded = new ArrayList<>();
    private Fireball[] fireballs = new Fireball[3];
    private int score = 0;

    private Hero() {
    }

    public static Hero getInstance() {
        return INSTANCE;
    }

    @Override
    public String getName() {
        return "Hero";
    }

    public RoomManager getRoomManager() {
        return roomManager;
    }

    public void setRoomManager(RoomManager roomManager) {
        this.roomManager = roomManager;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }


    public Room getCurrentRoom() {
        return this.currentRoom;
    }

    public Fireball[] getFireballs() {
        return fireballs;
    }

    public void setFireballs(Fireball[] fireballs) {
        this.fireballs = fireballs;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void updateScore(int points) {
        if (points > 0)
            this.score += points;
        else if (this.score > 0) {
            this.score += points;
        }
        ImageMatrixGUI.getInstance().setStatus("Score: " + this.getScore());
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }

    public void setStatusBar(StatusBar statusBar) {
        this.statusBar = statusBar;
    }

    public StatusBar getStatusBar() {
        return statusBar;
    }

    public void setInventory(Item[] inventory) {
        this.inventory = inventory;
    }

    public Item[] getInventory() {
        return this.inventory;
    }

    public List<Weapon> getWeaponsWielded() {
        return weaponsWielded;
    }

    public void setWeaponsWielded(List<Weapon> weaponsWielded) {
        this.weaponsWielded = weaponsWielded;
    }

    // Method readResolve() is needed for proper de-serialization,
    // otherwise the singleton pattern is violated (Java creates 2 instances of hero, even with a private constructor)
    public Object readResolve() {
        setHero();
        return getInstance();
    }

    //Set hero data to singleton, to properly access the instance after de-serializing
    public void setHero() {
        getInstance().setRoomManager(this.getRoomManager());
        getInstance().setCurrentRoom(this.getCurrentRoom());
        getInstance().getCurrentRoom().clearTile(this);
        getInstance().setPosition(this.getPosition());
        getInstance().setHp(this.getHp());
        getInstance().setDamage(this.getDamage());
        getInstance().setCurrentDirection(this.getCurrentDirection());
        getInstance().setFireballs(this.getFireballs());
        getInstance().setInventory(this.getInventory());
        getInstance().setWeaponsWielded(this.getWeaponsWielded());
        //Rebuild status bar
        this.getStatusBar().rebuildStatusBar();
        getInstance().setStatusBar(this.getStatusBar());
        getInstance().setScore(this.getScore());
    }

    // Initialization used when a new game is started
    public void init(Room room) {
        this.setHp(Hp.HERO.getHp());
        this.setCurrentRoom(room);
        this.setDamage(Damage.HERO.getDamage());
        this.setCurrentDirection(Direction.UP);
        //Reset inventory and fireballs
        for (int i = 0; i < 3; i++) {
            this.getFireballs()[i] = new Fireball(new Position(i, 0));
            this.getInventory()[i] = null;
        }
        StatusBar statusBar = new StatusBar();
        this.setStatusBar(statusBar);
        this.setScore(0);
    }

    // Evaluate method is called on arrow key press (on notify() method in the Engine class)
    // This method evaluates the adjacent tile corresponding to the direction provided and
    // calls the relevant method based on the tile instance
    public void evaluate(Direction direction) {
        this.setCurrentDirection(direction);
        Position newPosition = this.getPosition().plus(direction.asVector());
        Room currentRoom = this.getCurrentRoom();
        ImageTile tile = currentRoom.getTile(newPosition);
        if (canMoveHere(tile)) {
            this.move(newPosition);
            if (tile instanceof Trap) {
                ((Trap) tile).attack(this);
            } else if (tile instanceof Item) {
                Item item = (Item) tile;
                item.interact(this);
            }
        } else if (tile instanceof Enemy) {
            Enemy enemy = (Enemy) tile;
            //If it's adjacent, take damage from enemy without making them all move, EXCEPT for Thief,
            // which only attacks diagonally
            if (!(enemy instanceof Thief)) {
                this.sufferDamage(enemy);
            }
            enemy.sufferDamage(this);
        } else if (tile instanceof DoorClosed) {
            ((DoorClosed) tile).interact(this);
        } else if (tile == null) {
            // Being null means the current tile is an open door or doorway (underneath the hero tile)
            ImageTile currentTile = currentRoom.getTile(this.getPosition(), true);
            Door door = (Door) currentTile;
            door.interact(this);
        } else if (tile instanceof Chest) {
            ((Chest) tile).interact(this);
        }
    }

    @Override
    public void move(Position newPosition) {
        this.updateScore(Points.MOVE.getPoints());
        this.setPosition(newPosition);
        this.getCurrentRoom().getEnemies().forEach(enemy -> enemy.move(newPosition));
    }

    @Override
    public boolean canMoveHere(ImageTile tile) {
        return (tile instanceof Floor || tile instanceof DoorOpen || tile instanceof DoorWay || tile instanceof Item || tile instanceof Grass || tile instanceof Trap || tile instanceof Bones);
    }

    @Override
    public void sufferDamage(Enemy enemy) {
        this.setHp(this.getHp() - enemy.getDamage());
        this.getStatusBar().updateHealthBar();
        if (this.getHp() <= 0) {
            GameManager.getInstance().gameOver();
        }
    }

    public void addToInventory(Item item) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Item[] inv = this.getInventory();
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] == null) {
                inv[i] = item;
                //Index in status bar starts at 7
                this.getCurrentRoom().clearTile(item);
                item.setPosition(new Position(i + 7, 0));
                gui.addStatusImage(item);
                break;
            }
        }
    }

    public void removeFromInventory(Item item) {
        Item[] inv = this.getInventory();
        for (int i = 0; i < inv.length; i++) {
            if (inv[i] == item) {
                this.getInventory()[i] = null;
                ImageMatrixGUI.getInstance().removeStatusImage(item);
                break;
            }
        }
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        // Damage needs to reflect weapons in updated inventory
        if (item instanceof Weapon) {
            this.updateDamage();
        }
        gui.setStatus("Item stolen: " + item);
    }

    public void dropOrUseItem(int itemNumber) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        Item item = this.getInventory()[itemNumber];
        if (item != null) {
            this.getInventory()[itemNumber] = null;
            // Damage needs to reflect weapons in updated inventory
            if (item instanceof Weapon) {
                this.updateDamage();
                this.getCurrentRoom().addItem(item, this.getPosition());
                gui.setStatus("Item dropped: " + item);
            } else if (item instanceof Key) {
                this.getCurrentRoom().addItem(item, this.getPosition());
                gui.setStatus("Item dropped: " + item);
            } else if (item instanceof Meat) {
                this.regenHealth(Hp.MEAT.getHp());
            } else if (item instanceof Potion) {
                this.regenHealth(Hp.POTION.getHp());
                this.regenFireballs();
                gui.setStatus("Restored HP and fireballs!");
            }
            gui.removeStatusImage(item);
        }
    }

    public void regenHealth(int healthPoints) {
        if (this.getHp() < Hp.HERO.getHp()) {
            int restoredHp = Hp.HERO.getHp() - this.getHp();
            this.setHp(Math.min(this.getHp() + healthPoints, Hp.HERO.getHp()));
            this.getStatusBar().updateHealthBar();
            ImageMatrixGUI.getInstance().setStatus("Restored " + restoredHp + " HP!");
        }
    }

    public void regenFireballs() {
        for (int i = 0; i < 3; i++) {
            if (this.getFireballs()[i] == null) {
                Fireball fireball = new Fireball(new Position(i, 0));
                this.getFireballs()[i] = fireball;
                ImageMatrixGUI.getInstance().addStatusImage(fireball);
            }
        }
    }

    public void tryToOpenDoor(DoorClosed closedDoor) {
        String doorKeyName = closedDoor.getKeyName();
        Item[] inv = this.getInventory();
        boolean hasCorrectKey = false;
        for (Item item :
                inv) {
            if (item instanceof Key) {
                String keyName = ((Key) item).getKeyName();
                if (keyName.equals(doorKeyName)) {
                    //Open the door both in the current room and the room it leads to
                    RoomManager roomManager = this.getRoomManager();
                    roomManager.openDoors(this.getCurrentRoom(), closedDoor);
                    this.removeFromInventory(item);
                    hasCorrectKey = true;
                    ImageMatrixGUI.getInstance().setStatus(keyName + " was used!");
                    break;
                }
            }
        }
        if (!hasCorrectKey) {
            ImageMatrixGUI.getInstance().setStatus("You need " + doorKeyName + " to open this door.");
        }
    }

    public boolean inventoryIsFull() {
        for (Item i :
                this.getInventory()) {
            if (i == null) return false;
        }
        return true;
    }

    public void throwFireball() {
        Fireball[] fireballs = this.getFireballs();
        //Loop through hero fireballs and work with the first that is not null. Break from loop once it's done.
        for (int i = fireballs.length - 1; i >= 0; i--) {
            Fireball fireballToThrow = fireballs[i];
            if (fireballToThrow != null) {
                //Clear them first, otherwise it will only disappear from status bar after impact
                ImageMatrixGUI.getInstance().removeStatusImage(fireballToThrow);
                fireballs[i] = null;
                Fireball fireball = new Fireball(getPosition());
                FireBallThread fireBallThread = new FireBallThread(this.getCurrentDirection(), fireball);
                ImageMatrixGUI.getInstance().addImage(fireball);
                fireBallThread.start();
                break;
            }
        }
    }

    //Weapon damage only stacks up to a max of 2 weapons (even if the hero is carrying more)
    //Method updateDamage() counts the nb of weapons in inventory and updates damage prioritizing the 2 best weapons
    public void updateDamage() {
        Item[] inventory = this.getInventory();
        List<Weapon> weapons = new ArrayList<>();
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] instanceof Weapon) {
                weapons.add(((Weapon) inventory[i]));
            }
        }
        //Sort list according to damage, possible since we implemented a Comparable according to weapon damage
        Collections.sort(weapons);
        //Reset hero damage
        this.setDamage(Damage.HERO.getDamage());
        //Update damage based on the 2 best weapons
        switch (weapons.size()) {
            //If there are no weapons in inventory, i.e., size == 0, do nothing, damage has already been reset
            case 1:
                this.setDamage(this.getDamage() + weapons.get(0).getDamage());
                break;
            case 2:
                this.setDamage(this.getDamage() + weapons.get(0).getDamage() + weapons.get(1).getDamage());
                break;
            case 3:
                this.setDamage(this.getDamage() + weapons.get(0).getDamage() + weapons.get(1).getDamage());
                //weaponsWielded should have a max size of 2, so we remove the weakest weapon (at the end of the list)
                weapons.remove(weapons.size() - 1);
                break;
        }
        this.setWeaponsWielded(weapons);
    }


}
