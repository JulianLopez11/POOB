package src.domain;

public class PoisonedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        // Pierde 1/8 de los PS máximos por veneno
        pokemon.losePS(pokemon.getMaxPs() / 8);
        return true; // Puede actuar normalmente aunque esté envenenado
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        // Los Pokémon tipo veneno y acero son inmunes al veneno
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
