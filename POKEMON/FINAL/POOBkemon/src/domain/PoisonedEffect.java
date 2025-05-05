package src.domain;

public class PoisonedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        pokemon.losePS(pokemon.getMaxPs() / 8);
        return true;
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        PokemonType principalType = pokemon.getPrincipalType();
        PokemonType secondaryType = pokemon.getSecondaryType();

        return principalType == PokemonType.ACERO || principalType == PokemonType.VENENO ||
                (secondaryType != null && (secondaryType == PokemonType.ACERO ||
                        secondaryType == PokemonType.VENENO));
    }

    @Override
    public String getName() {
        return "envenenado";
    }
}
