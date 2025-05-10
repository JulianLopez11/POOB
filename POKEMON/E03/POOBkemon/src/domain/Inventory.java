package src.domain;

import java.util.*;

public class Inventory {

    private final int capacity = 10;
    private TreeMap<String,Pokemon> pokemons = new TreeMap<>();
    private HashMap<Item,Integer> items = new HashMap<>();

    public Inventory() {
    }

    //-------------------ADDERS-------------------
    public void addPokemon(Pokemon pokemon){
        if(pokemons.size() + items.size() < capacity){
            pokemons.put(pokemon.getName(), pokemon);
        }
    }

    public void addItem(Item item){
        if(pokemons.size() + items.size() < capacity){
            items.put(item, items.getOrDefault(item, 0) + 1);
        }
    }

    //-------------------DELETERS-------------------
    public void removePokemon(Pokemon pokemon){
        if(pokemons.containsKey(pokemon.getName())){
            pokemons.remove(pokemon.getName());
        }
    }


    public void removeItem(Item item){
        if (!items.containsKey(item)) throw new POOBkemonException(POOBkemonException.INVALID_ITEM);
        Integer count = items.get(item);
        if (count <= 0) throw new POOBkemonException(POOBkemonException.INVALID_ITEM);
        if (count == 1) {
            items.remove(item);
        } else {
            items.put(item, count - 1);
        }
    }

    //--------------------USERS-------------------
    /**
     * @param item El ítem a usar
     * @param pokemon El objetivo sobre el que usar el ítem
     * @return true si el ítem se usó correctamente
     */
    public void useItem(Pokemon pokemon, Item item) {
        item.use(pokemon);
        items.remove(item.getName());
    }


    //-------------------GETTERS-------------------
    /**
     * Obtiene todos los ítems del inventario
     * @return Lista con todos los ítems
     */
    public HashMap<Item,Integer> getItems() {
        return items;
    }

    /**
     * Obtiene todos los Pokémon del inventario
     * @return Lista con todos los Pokémon
     */
    public TreeMap <String,Pokemon> getPokemons() {
        return pokemons;
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

    public ArrayList<Pokemon> getAlivePokemons(Pokemon pok){
        ArrayList<Pokemon> poke= new ArrayList<Pokemon> ();
        for(Pokemon p: pokemons.values()){
            if(p.isAlive() && !p.equals(pok)) poke.add(p);
        }
        return poke;
    }

    //-------------------BOOLEAN-------------------
    public boolean canChange(Pokemon pokemon){
        return pokemon.isAlive() && pokemons.containsValue(pokemon);
    }

    /**
     * Verifica si el inventario está lleno
     * @return true si el inventario está lleno, false en caso contrario
     */
    public boolean isFull() {
        return pokemons.size() + items.size() >= capacity;
    }

    public boolean contains(Pokemon pokemon){
        return pokemons.containsKey(pokemon.getName());
    }

    public boolean contains(Item item){
        return items.containsKey(item);
    }
}
