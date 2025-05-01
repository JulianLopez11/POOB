package src.domain;

/**
 * Enumeración de todos los tipos de Pokémon con su categoría de ataque (Físico/Especial)
 */
public enum PokemonType {
    ACERO("Fisico"),
    AGUA("Especial"),
    BICHO("Fisico"),
    DRAGON("Especial"),
    ELECTRICO("Especial"),
    FANTASMA("Fisico"),
    FUEGO("Especial"),
    HADA("Especial"),
    HIELO("Especial"),
    LUCHA("Fisico"),
    NORMAL("Fisico"),
    PLANTA("Especial"),
    PSIQUICO("Especial"),
    ROCA("Fisico"),
    SINIESTRO("Especial"),
    TIERRA("Fisico"),
    VENENO("Fisico"),
    VOLADOR("Fisico");

    private final String typeMov;

    PokemonType(String newTypeMov) {
        typeMov = newTypeMov;
    }

    public String getTypeMov() {
        return typeMov;
    }

    public int getIndex() {
        return this.ordinal();
    }

    /**
     * Obtiene el nombre del tipo en formato legible
     *
     * @return Nombre del tipo con primera letra mayúscula
     */
    public String getName() {
        String name = this.name();
        return name.substring(0, 1) + name.substring(1).toLowerCase();
    }
}
