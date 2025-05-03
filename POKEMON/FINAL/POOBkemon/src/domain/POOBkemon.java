package src.domain;
import javax.swing.*;
import java.util.*;

public class POOBkemon {
    private HashSet<Trainer> trainers; // Lista de entrenadores sin duplicados
    private String entrenadorTurno;
    private HashMap<String, Pokemon> pokedex;
    private HashMap<String, Movement> defaultMovementsMap;
    private HashMap<String, Item> defaultItemsMap;

    public POOBkemon(String modalidad) {
        trainers = new HashSet<>();
        pokedex = new HashMap<>();
        defaultMovementsMap = new HashMap<>();
        defaultItemsMap = new HashMap<>();
        
        // Inicializar los Pokémon, movimientos e ítems por defecto
        defaultPokemons();
        defaultMovements();
        defaultItems();
    }

    public void turno() {
        // Lógica para los turnos
    }

    public void start() {
        // Iniciar juego
    }

    public void defaultPokemons() {
        pokedex.clear();
        Pokemon charizard = new Pokemon("Charizard", PokemonType.FUEGO, PokemonType.VOLADOR);
        Pokemon blastoise = new Pokemon("Blastoise", PokemonType.AGUA);
        Pokemon venusaur = new Pokemon("Venusaur", PokemonType.PLANTA, PokemonType.VENENO);
        Pokemon raichu = new Pokemon("Raichu", PokemonType.ELECTRICO);
        Pokemon gengar = new Pokemon("Gengar", PokemonType.FANTASMA, PokemonType.VENENO);
        Pokemon snorlax = new Pokemon("Snorlax", PokemonType.NORMAL);
        
        pokedex.put(charizard.getName(), charizard);
        pokedex.put(blastoise.getName(), blastoise);
        pokedex.put(venusaur.getName(), venusaur);
        pokedex.put(raichu.getName(), raichu);
        pokedex.put(gengar.getName(), gengar);
        pokedex.put(snorlax.getName(), snorlax);
    }

    public void defaultItems() {
        defaultItemsMap.clear();
        Item psPotion = new NormalPotion();
        Item superPotion = new SuperPotion();
        Item hyperPotion = new HyperPotion();
        
        defaultItemsMap.put(psPotion.getName(), psPotion);
        defaultItemsMap.put(superPotion.getName(), superPotion);
        defaultItemsMap.put(hyperPotion.getName(), hyperPotion);
    }

    public void defaultMovements() {
        defaultMovementsMap.clear();
        Movement lanzallamas = new Movement("Lanzallamas", PokemonType.FUEGO, 90, 100, 15, "Especial", "quemado", 10);
        Movement golpeAereo = new Movement("Golpe Aéreo", PokemonType.VOLADOR, 75, 95, 15, "Físico");
        Movement giroFuego = new Movement("Giro Fuego", PokemonType.FUEGO, 35, 85, 15, "Especial", "atrapado", 100);
        Movement cuchillada = new Movement("Cuchillada", PokemonType.NORMAL, 70, 100, 20, "Físico");
        Movement hidrobomba = new Movement("Hidrobomba", PokemonType.AGUA, 110, 80, 5, "Especial");
        Movement rayo_hielo = new Movement("Rayo Hielo", PokemonType.HIELO, 90, 100, 10, "Especial", "congelado", 10);
        Movement cabezazo = new Movement("Cabezazo", PokemonType.NORMAL, 70, 100, 15, "Físico", "confuso", 30);
        Movement hidropulso = new Movement("Hidropulso", PokemonType.AGUA, 60, 100, 20, "Especial", "confuso", 20);
        Movement rayoSolar = new Movement("Rayo Solar", PokemonType.PLANTA, 120, 100, 10, "Especial");
        Movement bombaLodo = new Movement("Bomba Lodo", PokemonType.VENENO, 90, 100, 10, "Especial", "envenenado", 30);
        Movement polvoVeneno = new Movement("Polvo Veneno", PokemonType.VENENO, 0, 75, 35, "Especial", "envenenado", 100);
        Movement drenadoras = new Movement("Drenadoras", PokemonType.PLANTA, 0, 90, 10, "Especial", "atrapado", 100);
        Movement trueno = new Movement("Trueno", PokemonType.ELECTRICO, 110, 70, 10, "Especial", "paralizado", 30);
        Movement atizadorX = new Movement("Atizador-X", PokemonType.ELECTRICO, 80, 100, 15, "Físico");
        Movement onda_trueno = new Movement("Onda Trueno", PokemonType.ELECTRICO, 0, 90, 20, "Especial", "paralizado", 100);
        Movement cola_hierro = new Movement("Cola Hierro", PokemonType.ACERO, 100, 75, 15, "Físico");
    
        defaultMovementsMap.put(lanzallamas.getName(), lanzallamas);
        defaultMovementsMap.put(golpeAereo.getName(), golpeAereo);
        defaultMovementsMap.put(giroFuego.getName(), giroFuego);
        defaultMovementsMap.put(cuchillada.getName(), cuchillada);
        defaultMovementsMap.put(hidrobomba.getName(), hidrobomba);
        defaultMovementsMap.put(rayo_hielo.getName(), rayo_hielo);
        defaultMovementsMap.put(cabezazo.getName(), cabezazo);
        defaultMovementsMap.put(hidropulso.getName(), hidropulso);
        defaultMovementsMap.put(rayoSolar.getName(), rayoSolar);
        defaultMovementsMap.put(bombaLodo.getName(), bombaLodo);
        defaultMovementsMap.put(polvoVeneno.getName(), polvoVeneno);
        defaultMovementsMap.put(drenadoras.getName(), drenadoras);
        defaultMovementsMap.put(trueno.getName(), trueno);
        defaultMovementsMap.put(atizadorX.getName(), atizadorX);
        defaultMovementsMap.put(onda_trueno.getName(), onda_trueno);
        defaultMovementsMap.put(cola_hierro.getName(), cola_hierro);
    }
    
    public void coin() throws POOBkemonException {
        if (trainers == null || trainers.isEmpty()) {
            throw new POOBkemonException(POOBkemonException.NO_TRAINERS_FOUND);
        }
        
        List<Trainer> trainersList = new ArrayList<>(trainers);
        Random random = new Random();
        int resultado = random.nextInt(trainersList.size());
        Trainer trainerSeleccionado = trainersList.get(resultado);
        
        try {
            entrenadorTurno = trainerSeleccionado.getNombre();
            JOptionPane.showMessageDialog(null,
                    "El entrenador que empieza es: " + entrenadorTurno,
                    "Lanzamiento de Moneda",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al seleccionar el entrenador: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Añade un entrenador al conjunto de entrenadores
     * @param trainer Entrenador a añadir
     * @return true si fue añadido, false si ya existía
     */
    public boolean addTrainer(Trainer trainer) {
        return trainers.add(trainer);
    }
    
    /**
     * Obtiene la colección de Pokémon predefinidos
     * @return Colección de Pokémon
     */
    public Collection<Pokemon> getPokedex() {
        return pokedex.values();
    }
    
    /**
     * Obtiene un Pokémon por su nombre
     * @param name Nombre del Pokémon
     * @return El Pokémon si existe, null si no
     */
    public Pokemon getPokemonByName(String name) {
        return pokedex.get(name);
    }
    
    /**
     * Obtiene la colección de movimientos predefinidos
     * @return Colección de movimientos
     */
    public Collection<Movement> getDefaultMovements() {
        return defaultMovementsMap.values();
    }
    
    /**
     * Obtiene un movimiento por su nombre
     * @param name Nombre del movimiento
     * @return El movimiento si existe, null si no
     */
    public Movement getMovementByName(String name) {
        return defaultMovementsMap.get(name);
    }
    
    /**
     * Obtiene la colección de objetos predefinidos
     * @return Colección de objetos
     */
    public Collection<Item> getDefaultItems() {
        return defaultItemsMap.values();
    }
    
    /**
     * Obtiene un ítem por su nombre
     * @param name Nombre del ítem
     * @return El ítem si existe, null si no
     */
    public Item getItemByName(String name) {
        return defaultItemsMap.get(name);
    }
    
    /**
     * Obtiene la colección de entrenadores
     * @return Colección de entrenadores
     */
    public Collection<Trainer> getTrainers() {
        return new ArrayList<>(trainers);
    }
}
