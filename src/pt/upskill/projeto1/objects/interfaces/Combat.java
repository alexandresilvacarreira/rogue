package pt.upskill.projeto1.objects.interfaces;

import pt.upskill.projeto1.objects.characters.Character;

/**
 *
 * Interface for combat between hero and enemies.
 *
 */
public interface Combat<C extends Character> {
    void sufferDamage(C opponent);
}
