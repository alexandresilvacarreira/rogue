package pt.upskill.projeto1.objects.characters.enemies;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.objects.Items.Potion;
import pt.upskill.projeto1.objects.Items.Weapons.EnchantedSword;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.interfaces.Attack;
import pt.upskill.projeto1.rogue.utils.Direction;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.enums.Chance;
import pt.upskill.projeto1.rogue.utils.enums.Convergence;
import pt.upskill.projeto1.rogue.utils.enums.Points;
import pt.upskill.projeto1.rooms.Room;

/**
 *
 * Defines Warlock enemy. This enemy implements the Attack interface for its
 * special attack. It also has a special drop (Potion) and can only be defeated with an
 * EnchantedSword.
 *
 */

public class Warlock extends Enemy implements Attack<Hero> {

    public Warlock(int hp, Position position, int damage, int points) {
        super(hp, position, damage, points);
    }

    @Override
    public String getName() {
        return "Warlock";
    }

    // The implementation of the special attack affects the Warlock movement
    // and is triggered by the distance to the hero.
    @Override
    public void move(Position heroPosition) {
        int distToHeroX = this.getPosition().getX() - heroPosition.getX();
        int distToHeroY = this.getPosition().getY() - heroPosition.getY();
        //Try to teleport if distance is greater than a given number of tiles
        if (Math.abs(distToHeroX) > Convergence.TELEPORT.getDistance() || Math.abs(distToHeroY) > Convergence.TELEPORT.getDistance()) {
            this.attack(Hero.getInstance());
        } else {
            super.move(heroPosition);
        }
    }

    // Special attack: Warlock has a chance of teleporting to one of hero's
    // adjacent tiles and inflict damage when doing so
    @Override
    public void attack(Hero hero) {
        double rand = Math.random();
        if (rand > Chance.TELEPORT.getChance()) {
            Room currentRoom = hero.getCurrentRoom();
            Position[] positions = new Position[4];
            Position heroUp = hero.getPosition().plus(Direction.UP.asVector());
            Position heroDown = hero.getPosition().plus(Direction.DOWN.asVector());
            Position heroLeft = hero.getPosition().plus(Direction.LEFT.asVector());
            Position heroRight = hero.getPosition().plus(Direction.LEFT.asVector());
            positions[0] = heroUp;
            positions[1] = heroDown;
            positions[2] = heroLeft;
            positions[3] = heroRight;
            for (int i = 0; i < positions.length; i++) {
                ImageTile tileToTeleport = currentRoom.getTile(positions[i]);
                if (this.canMoveHere(tileToTeleport)){
                    this.setPosition(positions[i]);
                    hero.sufferDamage(this);
                    break;
                }
            }
        } else {
            //If it fails to teleport, move at random
            this.moveRandom();
        }
    }

    @Override
    public void specialDrop() {
        double rand = Math.random();
        if(rand > Chance.DROP.getChance()){
            Potion potion = new Potion(this.getPosition(), Points.POTION.getPoints());
            Hero.getInstance().getCurrentRoom().addItem(potion, this.getPosition());
        }
    }

    //Warlock only suffers damage if hero has the Enchanted Sword
    @Override
    public void sufferDamage(Hero hero) {
        Item[] heroInventory = hero.getInventory();
        boolean hasEnchantedSword = false;
        for (int i = 0; i < heroInventory.length; i++) {
            if (heroInventory[i] instanceof EnchantedSword){
                hasEnchantedSword = true;
                break;
            }
        }
        if (hasEnchantedSword) {
            super.sufferDamage(hero);
        } else {
            ImageMatrixGUI.getInstance().setStatus("You need an Enchanted Sword to damage this enemy!");
        }
    }
}

