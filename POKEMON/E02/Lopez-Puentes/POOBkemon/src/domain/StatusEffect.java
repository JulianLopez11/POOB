package src.domain;

public interface StatusEffect {
    /**
     * Aplica el efecto al Pokémon al final de turno
     * @param pokemon Pokémon afectado
     * @return true si el Pokémon puede actuar, false si está incapacitado
     */
    boolean applyEffect(Pokemon pokemon);

    /**
     * Verifica si un Pokémon es inmune a este efecto
     * @param pokemon Pokémon a verificar
     * @return true si es inmune, false en caso contrario
     */
    boolean isImmune(Pokemon pokemon);

    /**
     * Obtiene el nombre del efecto
     * @return Nombre del efecto
     */
    String getName();
}
