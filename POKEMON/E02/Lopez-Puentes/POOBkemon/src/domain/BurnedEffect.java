package src.domain;

public class BurnedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        pokemon.losePS(pokemon.getMaxPs() / 16);
        return true;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        PokemonType principalType = pokemon.getPrincipalType();
        PokemonType secondaryType = pokemon.getSecondaryType();

        return principalType == PokemonType.FUEGO ||
                (secondaryType != null && secondaryType == PokemonType.FUEGO);
    }

    @Override
    public String getName() {
        return "quemado";
    }
}
