package src.domain;
import java.util.*;
public class SleepEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        if (random.nextInt(100) < 33) {
            System.out.println(pokemon.getName() + " se ha despertado!");
            return true;
        } else {
            System.out.println(pokemon.getName() + " sigue dormido!");
            return false;
        }
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        return false;
    }

    @Override
    public String getName() {
        return "dormido";
    }
}
