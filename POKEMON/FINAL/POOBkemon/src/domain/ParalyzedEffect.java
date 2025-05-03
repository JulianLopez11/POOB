package src.domain;
import java.util.*;
public class ParalyzedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        Random random = new Random();
        // 25% de probabilidad de no poder moverse por la parálisis
        if (random.nextInt(100) < 25) {
            System.out.println(pokemon.getName() + " está paralizado y no puede moverse!");
            return false; // No puede actuar este turno
        }
        // La velocidad se reduce a 1/4 (contemplado en getVelocity() de Pokemon)
        return true; // Puede actuar
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        // Los Pokémon tipo tierra son inmunes a la parálisis
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
