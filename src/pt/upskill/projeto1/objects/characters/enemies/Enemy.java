package pt.upskill.projeto1.objects.characters.enemies;

import pt.upskill.projeto1.game.BonesThread;
import pt.upskill.projeto1.objects.environment.Bones;
import pt.upskill.projeto1.objects.interfaces.Combat;
import pt.upskill.projeto1.objects.Items.Meat;
import pt.upskill.projeto1.rogue.utils.enums.Chance;
import pt.upskill.projeto1.rogue.utils.enums.Convergence;
import pt.upskill.projeto1.rogue.utils.enums.Points;
import pt.upskill.projeto1.rooms.Room;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Character;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.environment.Doors.Door;
import pt.upskill.projeto1.objects.environment.Wall;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;

/**
 *
 * Superclass for enemies. Implements methods for movement, damage (through
 * the Combat interface) and drops.
 * Implemented as an abstract class, since the getName() method from ImageTile
 * is only implemented at the level of each individual enemy.
 *
 */

public abstract class Enemy extends Character implements Combat<Hero> {

    private int points;

    public Enemy(int hp, Position position, int damage, int points) {
        super(hp, position, damage);
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public void move(Position heroPosition) {
        Hero hero = Hero.getInstance();
        Room currentRoom = hero.getCurrentRoom();
        int distToHeroX = this.getPosition().getX() - heroPosition.getX();
        int distToHeroY = this.getPosition().getY() - heroPosition.getY();
        // Move at random if distance to hero is greater than a given constant
        if (Math.abs(distToHeroX) > Convergence.DISTANCE.getDistance() || Math.abs(distToHeroY) > Convergence.DISTANCE.getDistance()) {
            this.moveRandom();
        } else {
            if (Math.abs(distToHeroX) != Math.abs(distToHeroY)) {
                Position newPosition;
                // Evaluate the axis on which the distance is largest and try to converge on that one
                if (Math.abs(distToHeroX) > Math.abs(distToHeroY)) {
                    //Converge on x
                    if (distToHeroX > 0) {
                        newPosition = this.getPosition().plus(Direction.LEFT.asVector());
                    } else {
                        newPosition = this.getPosition().plus(Direction.RIGHT.asVector());
                    }
                } else {
                    //Converge on y
                    if (distToHeroY > 0) {
                        newPosition = this.getPosition().plus(Direction.UP.asVector());
                    } else {
                        newPosition = this.getPosition().plus(Direction.DOWN.asVector());
                    }
                }
                ImageTile tile = currentRoom.getTile(newPosition);
                if (canMoveHere(tile))
                    this.setPosition(newPosition);
                else if (tile instanceof Hero) {
                    hero.sufferDamage(this);
                }
            } else {
                //If the distance is the same, there are two possible tiles to move, one on x, the other on y
                //Try x first
                Position positionX;
                if (distToHeroX > 0) {
                    positionX = this.getPosition().plus(Direction.LEFT.asVector());
                } else {
                    positionX = this.getPosition().plus(Direction.RIGHT.asVector());
                }
                ImageTile tileX = currentRoom.getTile(positionX);
                if (canMoveHere(tileX))
                    this.setPosition(positionX);
                else if (tileX instanceof Hero) {
                    hero.sufferDamage(this);
                } else {
                    //If moving on x didn't work, try on y
                    Position positionY;
                    if (distToHeroY > 0) {
                        positionY = this.getPosition().plus(Direction.UP.asVector());
                    } else {
                        positionY = this.getPosition().plus(Direction.DOWN.asVector());
                    }
                    ImageTile tileY = currentRoom.getTile(positionY);
                    if (canMoveHere(tileY))
                        this.setPosition(positionY);
                    else if (tileY instanceof Hero) {
                        hero.sufferDamage(this);
                    }
                }
            }
        }
    }

    public void moveRandom() {
        int tileIndex = (int) (Math.random() * 4);
        Hero hero = Hero.getInstance();
        Room currentRoom = hero.getCurrentRoom();
        Position newPosition = this.getPosition();
        switch (tileIndex) {
            case 0:
                //down
                newPosition = this.getPosition().plus(Direction.DOWN.asVector());
                break;
            case 1:
                //up
                newPosition = this.getPosition().plus(Direction.UP.asVector());
                break;
            case 2:
                //left
                newPosition = this.getPosition().plus(Direction.LEFT.asVector());
                break;
            case 3:
                //right
                newPosition = this.getPosition().plus(Direction.RIGHT.asVector());
                break;
        }
        ImageTile tile = currentRoom.getTile(newPosition);
        if (canMoveHere(tile))
            this.setPosition(newPosition);
    }

    @Override
    public boolean canMoveHere(ImageTile tile) {
        return !(tile instanceof Hero || tile instanceof Enemy || tile instanceof Wall || tile instanceof Door || tile == null);
    }

    @Override
    public void sufferDamage(Hero hero) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        this.setHp(this.getHp() - hero.getDamage());
        gui.setStatus("Enemy HP: " + this.getHp());
        if (this.getHp() <= 0) {
            hero.getCurrentRoom().clearTile(this);
            hero.updateScore(this.getPoints());
            gui.setStatus("Enemy defeated! +" + this.getPoints() + " points");
            this.specialDrop();
            BonesThread bonesThread = new BonesThread(new Bones(this.getPosition()));
            bonesThread.start();
        }
    }

    //All enemies have a chance of dropping meat on death
    public void specialDrop(){
        double rand = Math.random();
        if (rand >= Chance.MEAT.getChance()){
            Meat meat = new Meat(this.getPosition(), Points.MEAT.getPoints());
            Hero.getInstance().getCurrentRoom().addItem(meat, this.getPosition());
        }
    }

}
