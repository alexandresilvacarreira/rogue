package pt.upskill.projeto1.objects.interfaces;
import pt.upskill.projeto1.objects.characters.Character;

/**
 *
 * Interface for thief, warlock, fireball and trap attacks.
 * Implemented as a generic class since attacks are not limited
 * to one Character type (example: fireballs attack enemies, traps
 * attack hero).
 *
 */

public interface Attack<C extends Character>{

    void attack(C opponent);
}
