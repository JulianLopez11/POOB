package src.domain;

public class BurnedEffect implements StatusEffect {

    @Override
    public boolean applyEffect(Pokemon pokemon) {
        // Pierde 1/16 de los PS máximos por quemadura
        pokemon.losePS(pokemon.getMaxPs() / 16);
        // El ataque se reduce a la mitad (contemplado en getAttack() de Pokemon)
        return true; // Puede actuar normalmente aunque esté quemado
    }

    @Override
    public boolean isImmune(Pokemon pokemon) {
        // Los Pokémon tipo fuego son inmunes a quemaduras
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
