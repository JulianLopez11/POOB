package src.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa a un entrenador Pokémon
 */
public class Trainer {
    private String nombre;
    private List<Pokemon> team;
    private Inventory inventory;
    private Pokemon activePokemon;
    private int badges;

    /**
     * Constructor para crear un entrenador
     * @param nombre Nombre del entrenador
     */
    public Trainer(String nombre) {
        this.nombre = nombre;
        this.team = new ArrayList<>();
        this.inventory = new Inventory(30); // Inventario con capacidad para 30 ítems
        this.badges = 0;
    }

    /**
     * Añade un Pokémon al equipo del entrenador
     * @param pokemon Pokémon a añadir
     * @return true si se añadió correctamente, false si el equipo está lleno
     */
    public boolean addPokemon(Pokemon pokemon) {
        if (team.size() < 6) { // Máximo 6 Pokémon en el equipo
            team.add(pokemon);

            // Si es el primer Pokémon, establecerlo como activo
            if (team.size() == 1) {
                activePokemon = pokemon;
            }

            return true;
        }
        return false;
    }

    /**
     * Cambia el Pokémon activo
     * @param index Índice del Pokémon en el equipo
     * @return true si se cambió correctamente, false en caso contrario
     */
    public boolean switchPokemon(int index) {
        if (index >= 0 && index < team.size()) {
            Pokemon newActive = team.get(index);

            // No cambiar a un Pokémon debilitado
            if (newActive.isFainted()) {
                return false;
            }

            activePokemon = newActive;
            return true;
        }
        return false;
    }

    /**
     * Añade un ítem al inventario
     * @param item Ítem a añadir
     */
    public void addItem(Item item) {
        inventory.addItem(item);
    }

    /**
     * Usa un ítem del inventario
     * @param item Ítem a usar
     * @param target Objetivo del ítem (normalmente un Pokémon)
     * @return true si se usó correctamente, false en caso contrario
     */
    public boolean useItem(Item item, Object target) {
        return inventory.useItem(item, target);
    }

    /**
     * Obtiene el nombre del entrenador
     * @return Nombre del entrenador
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del entrenador
     * @param nombre Nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el equipo de Pokémon
     * @return Lista con los Pokémon del equipo
     */
    public List<Pokemon> getTeam() {
        return new ArrayList<>(team); // Devuelve una copia para evitar modificaciones no deseadas
    }

    /**
     * Obtiene el Pokémon activo
     * @return Pokémon activo actual
     */
    public Pokemon getActivePokemon() {
        return activePokemon;
    }

    /**
     * Obtiene el inventario del entrenador
     * @return Inventario del entrenador
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Obtiene el número de medallas del entrenador
     * @return Número de medallas
     */
    public int getBadges() {
        return badges;
    }

    /**
     * Añade una medalla al entrenador
     */
    public void addBadge() {
        this.badges++;
    }

    /**
     * Verifica si todos los Pokémon del equipo están debilitados
     * @return true si todos están debilitados, false si al menos uno sigue en pie
     */
    public boolean isDefeated() {
        for (Pokemon pokemon : team) {
            if (!pokemon.isFainted()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return nombre + " (Medallas: " + badges + ", Pokémon: " + team.size() + ")";
    }
}