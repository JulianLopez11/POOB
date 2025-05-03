package src.domain;
import javax.swing.*;
import java.util.*;

public class POOBkemon {
    private ArrayList<Trainer> trainers; // Lista de entrenadores
    private String entrenadorTurno;
    private ArrayList<Pokemon> pokedex;
    private ArrayList<Movement> defaultMovementsList;
    private ArrayList<Item> defaultItemsList;

    public POOBkemon(String modalidad) {
        trainers = new ArrayList<>();
        pokedex = new ArrayList<>();
        defaultMovementsList = new ArrayList<>();
        defaultItemsList = new ArrayList<>();
        
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
        
        pokedex.add(charizard);
        pokedex.add(blastoise);
        pokedex.add(venusaur);
        pokedex.add(raichu);
        pokedex.add(gengar);
        pokedex.add(snorlax);
    }

    public void defaultItems() {
        defaultItemsList.clear();
        Item PsPotion = new NormalPotion();
        Item SuperPotion = new SuperPotion();
        Item HyperPotion = new HyperPotion();
        
        defaultItemsList.add(PsPotion);
        defaultItemsList.add(SuperPotion);
        defaultItemsList.add(HyperPotion);
    }

    public void defaultMovements() {
        defaultMovementsList.clear();
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

        defaultMovementsList.add(golpeAereo);
        defaultMovementsList.add(giroFuego);
        defaultMovementsList.add(cuchillada);
        defaultMovementsList.add(hidrobomba);
        defaultMovementsList.add(rayo_hielo);
        defaultMovementsList.add(cabezazo);
        defaultMovementsList.add(hidropulso);
        defaultMovementsList.add(rayoSolar);
        defaultMovementsList.add(bombaLodo);
        defaultMovementsList.add(polvoVeneno);
        defaultMovementsList.add(drenadoras);
        defaultMovementsList.add(trueno);
        defaultMovementsList.add(atizadorX);
        defaultMovementsList.add(onda_trueno);
        defaultMovementsList.add(cola_hierro);
    }
    
    public void coin() throws POOBkemonException {
        if (trainers == null || trainers.isEmpty()) {
            throw new POOBkemonException(POOBkemonException.NO_TRAINERS_FOUND);
        }
        Random random = new Random();
        int resultado = random.nextInt(trainers.size());
        Trainer trainerSeleccionado = trainers.get(resultado);
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
     * Añade un entrenador a la lista de entrenadores
     * @param trainer Entrenador a añadir
     */
    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }
    
    /**
     * Obtiene la lista de Pokémon predefinidos
     * @return Lista de Pokémon
     */
    public ArrayList<Pokemon> getPokedex() {
        return new ArrayList<>(pokedex);
    }
    
    /**
     * Obtiene la lista de movimientos predefinidos
     * @return Lista de movimientos
     */
    public ArrayList<Movement> getDefaultMovementsList() {
        return new ArrayList<>(defaultMovementsList);
    }
    
    /**
     * Obtiene la lista de objetos predefinidos
     * @return Lista de objetos
     */
    public ArrayList<Item> getDefaultItemsList() {
        return new ArrayList<>(defaultItemsList);
    }
    
    /**
     * Obtiene la lista de entrenadores
     * @return Lista de entrenadores
     */
    public ArrayList<Trainer> getTrainers() {
        return new ArrayList<>(trainers);
    }
}
