package src.domain;
import java.util.*;
public class ParalyzedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        if (random.nextInt(100) < 25) {
            System.out.println(pokemon.getName() + " está paralizado y no puede moverse!");
            return false;
        }
        return true;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        PokemonType principalType = pokemon.getPrincipalType();
        PokemonType secondaryType = pokemon.getSecondaryType();

        return principalType == PokemonType.TIERRA ||
                (secondaryType != null && secondaryType == PokemonType.TIERRA);
    }

    @Override
    public String getName() {
        return "paralizado";
    }
}
