package src.domain;
import java.util.*;

public class ConfusedEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        boolean canAct = true;

        if (random.nextInt(100) < 33) {
            System.out.println(pokemon.getName() + " está confuso y se golpeó a sí mismo!");
            int damage = (int)((((2.0 * pokemon.getLevel() / 5.0 + 2.0) * 40 *
                    (pokemon.getAttack() / (double)pokemon.getDefense())) / 50.0) + 2.0);
            pokemon.losePS(damage);
            canAct = false;
        }

        if (random.nextInt(100) < 20) {
            System.out.println(pokemon.getName() + " ya no está confuso!");
            return true;
        }

        return canAct;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        return false;
    }

    @Override
    public String getName() {
        return "confuso";
    }
}
