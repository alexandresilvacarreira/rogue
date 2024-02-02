package pt.upskill.projeto1.objects.environment;

import pt.upskill.projeto1.game.GameManager;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.interfaces.Attack;
import pt.upskill.projeto1.rogue.utils.Position;
import pt.upskill.projeto1.rogue.utils.enums.Damage;

import java.io.Serializable;
/**
 *
 * Defines the Trap object. Trap implements the Attack interface, to deal damage
 * if the hero moves to its tile
 *
 */
public class Trap implements ImageTile, Attack<Hero>, Serializable {

    Position position;

    public Trap(Position position){
        this.position = position;
    }

    @Override
    public String getName() {
        return "Trap";
    }

    @Override
    public Position getPosition() {
        return this.position;
    }

    @Override
    public void attack(Hero hero) {
        hero.setHp(hero.getHp() - this.getDamage());
        hero.getStatusBar().updateHealthBar();
        if (hero.getHp() <= 0) {
            GameManager.getInstance().gameOver();
        }
    }

    private int getDamage() {
        return Damage.TRAP.getDamage();
    }
}
