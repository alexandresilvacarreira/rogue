package pt.upskill.projeto1.objects.environment;

import pt.upskill.projeto1.game.GameManager;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.characters.Hero;
import pt.upskill.projeto1.objects.interfaces.Interactible;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.Serializable;

/**
 *
 * Defines the Chest object. Interacting with a chest means winning the game.
 *
 */

public class Chest implements ImageTile, Interactible, Serializable {

    Position position;
    public Chest(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Chest";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public void interact(Hero hero) {
        GameManager gameManager = GameManager.getInstance();
        gameManager.youWon();
    }

}
