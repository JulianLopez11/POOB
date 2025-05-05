package src.domain;
import java.util.*;
public class FrozenEffect implements StatusEffect {
    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();

        if (random.nextInt(100) < 20) {
            System.out.println(pokemon.getName() + " se ha descongelado!");
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
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
