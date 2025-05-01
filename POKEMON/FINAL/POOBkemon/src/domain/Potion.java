package src.domain;

/**
 * Clase abstracta que representa una poción en el juego
 * Las pociones son ítems que restauran PS a un Pokémon
 */
public class Potion extends Item {
    protected final int healAmount;
    
    /**
     * Constructor para crear una poción
     * @param name Nombre de la poción
     * @param description Descripción de la poción
     * @param healAmount Cantidad de PS que restaura
     */
    public Potion(String name, String description, int healAmount) {
        super(name, description);
        this.healAmount = healAmount;
    }
    
    /**
     * Método para usar la poción en un Pokémon
     * @param target El Pokémon al que se aplicará la poción
     * @return true si se usó correctamente, false si no
     */
    @Override
    public boolean use(Object target) {
        if (!(target instanceof Pokemon)) {
            return false;
        }
        
        Pokemon pokemon = (Pokemon) target;
        
        // Verificar si el Pokémon está debilitado (no se pueden usar pociones en Pokémon debilitados)
        if (pokemon.isFainted()) {
            return false;
        }
        
        // Verificar si los PS ya están al máximo
        if (pokemon.getCurrentPs() >= pokemon.getMaxPs()) {
            return false;
        }
        
        // Aplicar la curación
        pokemon.heal(healAmount);
        return true;
    }
    
    /**
     * Obtiene la cantidad de curación
     * @return Cantidad de PS que restaura
     */
    public int getHealAmount() {
        return healAmount;
    }
}
