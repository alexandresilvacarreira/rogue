package pt.upskill.projeto1.objects.characters.enemies;

import pt.upskill.projeto1.game.BonesThread;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.environment.Bones;
import pt.upskill.projeto1.objects.interfaces.Attack;
import pt.upskill.projeto1.rogue.utils.enums.Chance;
import pt.upskill.projeto1.rogue.utils.enums.Convergence;
import pt.upskill.projeto1.rooms.Room;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.Vector2D;

/**
 *
 * Defines Thief enemy. This enemy has its own special movement pattern,
 * hence the override from the enemy superclass. It also implements the
 * Attack interface for its special attack.
 *
 */

public class Thief extends Enemy implements Attack<Hero> {

    private Item[] pouch = new Item[2];

    public Thief(int hp, Position position, int damage, int points) {
        super(hp, position, damage, points);
    }

    public Item[] getPouch() {
        return pouch;
    }

    @Override
    public String getName() {
        return "Thief";
    }

    //Thief moves and attacks only diagonally
    @Override
    public void move(Position heroPosition) {
        Hero hero = Hero.getInstance();
        Room currentRoom = hero.getCurrentRoom();
        int distToHeroX = this.getPosition().getX() - heroPosition.getX();
        int distToHeroY = this.getPosition().getY() - heroPosition.getY();
        // Random movement if the distance is greater than a given constant
        if (Math.abs(distToHeroX) > Convergence.DISTANCE.getDistance() || Math.abs(distToHeroY) > Convergence.DISTANCE.getDistance()) {
            moveRandom();
        } else {
            Vector2D moveX;
            boolean equalX = false;
            if (distToHeroX < 0) {
                moveX = Direction.RIGHT.asVector(); //(1,0)
            } else if (distToHeroX > 0) {
                moveX = Direction.LEFT.asVector(); //(-1,0)
            } else {
                equalX = true;
                moveX = null;
            }
            Vector2D moveY;
            boolean equalY = false;
            if (distToHeroY < 0) {
                moveY = Direction.DOWN.asVector(); //(0,1)
            } else if (distToHeroY > 0) {
                moveY = Direction.UP.asVector(); //(0,-1)
            } else {
                equalY = true;
                moveY = null;
            }
            // If the thief is not on the same y or x as the hero,
            // simply sum the two vectors. Otherwise, there are two candidate tiles
            // which need to be checked
            if (!equalX && !equalY) {
                if (moveX == null || moveY == null) {
                    throw new NullPointerException("Movement vectors cannot be null!");
                } else {
                    // Sum the x and y vectors to obtain the position
                    Vector2D toMove = moveX.plus(moveY);
                    Position pos = this.getPosition().plus(toMove);
                    ImageTile tile = currentRoom.getTile(pos);
                    if (canMoveHere(tile)) {
                        this.setPosition(pos);
                    } else if (tile instanceof Hero) {
                        hero.sufferDamage(this);
                        this.attack(hero);
                    }
                }
            } else if (equalX && !equalY) {
                // Since x is the same, there are two candidate tiles (left or right, to be
                // added to the y vector). We will try one first and, if it doesn't work, we try the other
                if (moveY == null) {
                    throw new NullPointerException("Movement vector cannot be null!");
                } else {
                    // Let's start checking the left
                    Vector2D moveLeft = Direction.LEFT.asVector();
                    Vector2D toMove1 = moveLeft.plus(moveY);
                    Position pos1 = this.getPosition().plus(toMove1);
                    ImageTile tile1 = currentRoom.getTile(pos1);
                    if (canMoveHere(tile1)) {
                        this.setPosition(pos1);
                    } else if (tile1 instanceof Hero) {
                        hero.sufferDamage(this);
                        this.attack(hero);
                    } else {
                        //Left didn't work, let's try right
                        Vector2D moveRight = Direction.RIGHT.asVector();
                        Vector2D toMove2 = moveRight.plus(moveY);
                        Position pos2 = this.getPosition().plus(toMove2);
                        ImageTile tile2 = currentRoom.getTile(pos2);
                        if (canMoveHere(tile2)) {
                            this.setPosition(pos2);
                        } else if (tile2 instanceof Hero) {
                            hero.sufferDamage(this);
                            this.attack(hero);
                        }
                    }
                }
            } else if (!equalX && equalY) {
                if (moveX == null) {
                    throw new NullPointerException("Movement vectors cannot be null!");
                } else {
                    //Two options, see which one has an available tile, start with up
                    Vector2D moveUp = Direction.UP.asVector();
                    Vector2D toMove1 = moveUp.plus(moveX);
                    Position pos1 = this.getPosition().plus(toMove1);
                    ImageTile tile1 = currentRoom.getTile(pos1);
                    if (canMoveHere(tile1)) {
                        this.setPosition(pos1);
                    } else if (tile1 instanceof Hero) {
                        hero.sufferDamage(this);
                        this.attack(hero);
                    } else {
                        //Going up didn't work, let's try down
                        Vector2D moveDown = Direction.DOWN.asVector();
                        Vector2D toMove2 = moveDown.plus(moveX);
                        Position pos2 = this.getPosition().plus(toMove2);
                        ImageTile tile2 = currentRoom.getTile(pos2);
                        if (canMoveHere(tile2)) {
                            this.setPosition(pos2);
                        } else if (tile2 instanceof Hero) {
                            hero.sufferDamage(this);
                            this.attack(hero);
                        }
                    }
                }
                //equalX && equalY should never happen, because that would mean being on top of the hero!
            }
        }
    }

    @Override
    public void moveRandom() {
        int tileIndex = (int) (Math.random() * 4);
        Hero hero = Hero.getInstance();
        Room currentRoom = hero.getCurrentRoom();
        Position newPosition = this.getPosition();
        switch (tileIndex) {
            case 0:
                //southWest
                newPosition = this.getPosition().plus(Direction.DOWN.asVector()).plus(Direction.LEFT.asVector());
                break;
            case 1:
                //northWest
                newPosition = this.getPosition().plus(Direction.UP.asVector()).plus(Direction.LEFT.asVector());
                break;
            case 2:
                //southEast
                newPosition = this.getPosition().plus(Direction.DOWN.asVector()).plus(Direction.RIGHT.asVector());
                break;
            case 3:
                //northEast
                newPosition = this.getPosition().plus(Direction.UP.asVector()).plus(Direction.RIGHT.asVector());
                break;
        }
        ImageTile tile = currentRoom.getTile(newPosition);
        if (canMoveHere(tile))
            this.setPosition(newPosition);
        else if (tile instanceof Hero) {
            hero.sufferDamage(this);
            this.attack(hero);
        }
    }

    // Special attack: Thief has a chance of stealing items from hero inventory
    // (max 2 items can be stolen)
    @Override
    public void attack(Hero hero) {
        if (!this.isPouchFull()) {
            double rand = Math.random();
            if (rand >= Chance.STEAL.getChance()) {
                Item[] heroInventory = hero.getInventory();
                for (int i = 0; i < heroInventory.length; i++) {
                    Item stolenItem = heroInventory[i];
                    if (stolenItem != null) {
                        this.addToPouch(stolenItem);
                        hero.removeFromInventory(stolenItem);
                        // Only steals one item per successful attack, so we break out of the loop
                        break;
                    }
                }
            }
        }
    }

    public boolean isPouchFull() {
        Item[] pouch = this.getPouch();
        for (int i = 0; i < pouch.length; i++) {
            if (pouch[i] == null) {
                return false;
            }
        }
        return true;
    }

    public void addToPouch(Item item) {
        Item[] pouch = this.getPouch();
        for (int i = 0; i < pouch.length; i++) {
            if (pouch[i] == null) {
                pouch[i] = item;
                break;
            }
        }
    }

    public void emptyPouch() {
        Item[] pouch = this.getPouch();
        for (int i = 0; i < pouch.length; i++) {
            Item itemToDrop = pouch[i];
            if (itemToDrop != null) {
                itemToDrop.setPosition(this.getPosition());
                pouch[i] = null;
                Hero.getInstance().getCurrentRoom().addItem(itemToDrop, this.getPosition());
            }
        }
    }

    @Override
    public void sufferDamage(Hero hero) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        gui.setStatus("Enemy HP: " + this.getHp());
        this.setHp(this.getHp() - hero.getDamage());
        if (this.getHp() <= 0) {
            hero.getCurrentRoom().clearTile(this);
            hero.updateScore(this.getPoints());
            gui.setStatus("Enemy defeated! +" + this.getPoints() + " points");
            this.emptyPouch();
            this.specialDrop();
            BonesThread bonesThread = new BonesThread(new Bones(this.getPosition()));
            bonesThread.start();
        }
    }
}
