package src.domain;
import java.util.*;
public class SleepEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        // 33% de probabilidad de despertar
        if (random.nextInt(100) < 33) {
            System.out.println(pokemon.getName() + " se ha despertado!");
            return true; // Despertó y puede actuar
        } else {
            System.out.println(pokemon.getName() + " sigue dormido!");
            return false; // Sigue dormido y no puede actuar
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
