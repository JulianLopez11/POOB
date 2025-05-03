package src.domain;
import java.util.*;
public class TrappedEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        pokemon.losePS(pokemon.getMaxPs() / 16);

        Random random = new Random();
        if (random.nextInt(100) < 20) {
            System.out.println(pokemon.getName() + " se ha liberado!");
            return true;
        }

        return true;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        return false;
    }

    @Override
    public String getName() {
        return "atrapado";
    }
}
