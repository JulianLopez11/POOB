package src.domain;
import java.util.*;
public class FrozenEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        // 20% de probabilidad de descongelarse
        if (random.nextInt(100) < 20) {
            System.out.println(pokemon.getName() + " se ha descongelado!");
            return true; // Se descongeló y puede actuar
        } else {
            return false; // Sigue congelado y no puede actuar
        }
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        // Los Pokémon tipo hielo son inmunes a la congelación
        PokemonType principalType = pokemon.getPrincipalType();
        PokemonType secondaryType = pokemon.getSecondaryType();

        return principalType == PokemonType.HIELO ||
                (secondaryType != null && secondaryType == PokemonType.HIELO);
    }

    @Override
    public String getName() {
        return "congelado";
    }
}
