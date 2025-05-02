package src.domain;

import java.util.*;

public class Inventory {

    private int capacity;
    private List<Pokemon> pokemons = new ArrayList<>();
    private List<Item> items = new ArrayList<>();

    public Inventory(int capacity) {
        this.capacity = capacity;
    }

    public void addPokemon(Pokemon pokemon){
        if(pokemons.size() + items.size() < capacity){
            pokemons.add(pokemon);
        }
    }

    public void removePokemon(Pokemon pokemon){
        if(pokemons.contains(pokemon)){
            pokemons.remove(pokemon);
        }
    }

    public void addItem(Item item){
        if(pokemons.size() + items.size() < capacity){
            items.add(item);
        }
    }

    public void removeItem(Item item){
        if(items.contains(item)){
            items.remove(item);
        }
    }
    
    /**
     * @param item El ítem a usar
     * @param target El objetivo sobre el que usar el ítem
     * @return true si el ítem se usó correctamente
     */
    public boolean useItem(Item item, Pokemon target) {
        if (!items.contains(item)) {
            return false;
        }
        
        boolean itemUsed = item.use(target);
        
        // Si el ítem se usó correctamente, lo eliminamos del inventario
        if (itemUsed) {
            items.remove(item);
        }
        
        return itemUsed;
    }

    /**
     * Obtiene todos los ítems del inventario
     * @return Lista con todos los ítems
     */
    public List<Item> getAllItems() {
        return new ArrayList<>(items); // Devuelve una copia para evitar modificaciones no deseadas
    }

    /**
     * Obtiene todos los Pokémon del inventario
     * @return Lista con todos los Pokémon
     */
    public List<Pokemon> getAllPokemons() {
        return new ArrayList<>(pokemons); // Devuelve una copia para evitar modificaciones no deseadas
    }

    /**
     * Verifica si el inventario está lleno
     * @return true si el inventario está lleno, false en caso contrario
     */
    public boolean isFull() {
        return pokemons.size() + items.size() >= capacity;
    }

    /**
     * Obtiene la capacidad total del inventario
     * @return Capacidad total
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Obtiene el espacio disponible en el inventario
     * @return Espacio disponible
     */
    public int getAvailableSpace() {
        return capacity - (pokemons.size() + items.size());
    }
}
