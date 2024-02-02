package pt.upskill.projeto1.objects.statusBar.Fire;

import pt.upskill.projeto1.game.BonesThread;
import pt.upskill.projeto1.objects.characters.enemies.Warlock;
import pt.upskill.projeto1.objects.environment.Bones;
import pt.upskill.projeto1.objects.environment.Grass;
import pt.upskill.projeto1.objects.environment.Trap;
import pt.upskill.projeto1.objects.interfaces.Attack;
import pt.upskill.projeto1.rooms.Room;
import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.characters.enemies.Enemy;
import pt.upskill.projeto1.objects.environment.Floor;
import pt.upskill.projeto1.objects.Items.Item;
import pt.upskill.projeto1.rogue.utils.enums.Damage;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 * Defines Fireball object. Implements the Attack interface,
 * for dealing damage to enemies on impact
 */

public class Fireball implements FireTile, Attack<Enemy>, Serializable {

    private Position position;

    public Fireball(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDamage() {
        return Damage.FIREBALL.getDamage();
    }

    @Override
    public boolean validateImpact() {
        Hero hero = Hero.getInstance();
        Room currentRoom = hero.getCurrentRoom();
        ImageTile tile = currentRoom.getTile(this.getPosition());
        if (!isValidTile(tile)) {
            if (tile instanceof Enemy) {
                Enemy enemy = (Enemy) tile;
                this.attack(enemy);
            }
            return false;
        }
        return true;
    }

    @Override
    public void attack(Enemy enemy) {
        ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
        //Warlock is immune to fireballs
        if (enemy instanceof Warlock) {
            gui.setStatus("Warlock is immune to fire!");
        } else {
            enemy.setHp(enemy.getHp() - this.getDamage());
            if (enemy.getHp() <= 0) {
                Hero hero = Hero.getInstance();
                hero.getCurrentRoom().clearTile(enemy);
                hero.updateScore(enemy.getPoints());
                gui.setStatus("Enemy defeated! +" + enemy.getPoints() + " points");
                enemy.specialDrop();
                BonesThread bonesThread = new BonesThread(new Bones(this.getPosition()));
                bonesThread.start();
            }
        }
    }
    
    public boolean isValidTile(ImageTile tile){
        if (tile instanceof Floor || tile instanceof Item || tile instanceof Trap || tile instanceof Grass || tile instanceof Bones){
            return true;
        }
        return false;
    }
}
