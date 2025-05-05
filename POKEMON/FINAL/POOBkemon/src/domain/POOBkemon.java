package src.domain;
import src.presentation.POOBkemonGUI;

import javax.swing.Timer.*;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;

/**
 * Clase principal que gestiona el juego POOBkemon
 * @author Julian López y Sebastian Puentes
 * @version 1.0
 * @since 2025-05-04
 */
public class POOBkemon {
    private ArrayList<Trainer> trainers = new ArrayList<>();
    Trainer trainer = new Trainer("Jugador 1");
    Trainer trainer2 = new Trainer("Jugador 2");
    private TreeMap<String, Pokemon> pokedex = new TreeMap<>();
    private HashMap<String, Movement> defaultMovementsMap = new HashMap<>();
    private HashMap<String, Item> defaultItemsMap = new HashMap<>();
    private boolean gameInProgress;
    private boolean gamePaused;
    private boolean gameOver;
    private int currentTrainerIndex = 0;
    private static final int TURN_TIME_LIMIT = 20;

    public POOBkemon() {
        trainers.add(trainer);
        trainers.add(trainer2);
        defaultPokemons();
        defaultItems();
        defaultMovements();
    }

    public void defaultPokemons() {
        pokedex.clear();
        Pokemon charizard = new Pokemon("Charizard", PokemonType.FUEGO, PokemonType.VOLADOR);
        Pokemon blastoise = new Pokemon("Blastoise", PokemonType.AGUA);
        Pokemon venusaur = new Pokemon("Venusaur", PokemonType.PLANTA, PokemonType.VENENO);
        Pokemon raichu = new Pokemon("Raichu", PokemonType.ELECTRICO);
        Pokemon gengar = new Pokemon("Gengar", PokemonType.FANTASMA, PokemonType.VENENO);
        Pokemon snorlax = new Pokemon("Snorlax", PokemonType.NORMAL);
        Pokemon delibird = new Pokemon("Delibird", PokemonType.HIELO, PokemonType.NORMAL);
        Pokemon donphan = new Pokemon("Donphan", PokemonType.TIERRA);
        Pokemon dragonite = new Pokemon("Dragonite", PokemonType.DRAGON, PokemonType.VOLADOR);
        Pokemon gardevoir = new Pokemon("Gardevoir", PokemonType.PSIQUICO, PokemonType.HADA);
        Pokemon machamp = new Pokemon("Machamp", PokemonType.LUCHA);
        Pokemon metagross = new Pokemon("Metagross", PokemonType.PSIQUICO);
        Pokemon togetic = new Pokemon("Togetic", PokemonType.HADA, PokemonType.VOLADOR);
        Pokemon tyranitar = new Pokemon("Tyranitar", PokemonType.ROCA);

        pokedex.put(charizard.getName(), charizard);
        pokedex.put(blastoise.getName(), blastoise);
        pokedex.put(venusaur.getName(), venusaur);
        pokedex.put(raichu.getName(), raichu);
        pokedex.put(gengar.getName(), gengar);
        pokedex.put(snorlax.getName(), snorlax);
        pokedex.put(delibird.getName(), delibird);
        pokedex.put(donphan.getName(), donphan);
        pokedex.put(dragonite.getName(), dragonite);
        pokedex.put(gardevoir.getName(), gardevoir);
        pokedex.put(machamp.getName(), machamp);
        pokedex.put(metagross.getName(), metagross);
        pokedex.put(togetic.getName(), togetic);
        pokedex.put(tyranitar.getName(), tyranitar);



    }

    public void defaultItems() {
        defaultItemsMap.clear();
        Item psPotion = new NormalPotion();
        Item superPotion = new SuperPotion();
        Item hyperPotion = new HyperPotion();
        Item revive = new Revive();
        defaultItemsMap.put(psPotion.getName(), psPotion);
        defaultItemsMap.put(superPotion.getName(), superPotion);
        defaultItemsMap.put(hyperPotion.getName(), hyperPotion);
        defaultItemsMap.put(revive.getName(), revive);
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

    /**
     * Termina el juego actual cuando un entrenador decide huir,
     * declarando como ganador al entrenador que no huyó.
     * @param fleeingTrainer El entrenador que decidió huir del combate
     * @return String con el mensaje indicando el ganador
     */
    public String leaveGame(Trainer fleeingTrainer) {
        String winnerMessage = "";
        if (trainers != null && trainers.size() == 2) {
            Trainer winner = null;
            for (Trainer trainer : trainers) {
                if (!trainer.equals(fleeingTrainer)) {
                    winner = trainer;
                    break;
                }
            }
            if (winner != null) {
                winnerMessage = "¡" + winner.getNombre() + " ha ganado el combate porque " +
                        fleeingTrainer.getNombre() + " ha huido!";
            }
        } else {
            winnerMessage = "El combate ha terminado.";
        }
        gameInProgress = false;
        gamePaused = false;
        gameOver = true;
        trainers.clear();
        pokedex.clear();
        defaultMovementsMap.clear();
        defaultItemsMap.clear();
        return winnerMessage;
    }

    /**
     * Obtiene el entrenador que está jugando actualmente su turno.
     * @return El entrenador actualmente activo en el juego
     */
    public Trainer getCurrentTrainer() {
        if (trainers != null && !trainers.isEmpty() && currentTrainerIndex >= 0 && currentTrainerIndex < trainers.size()) {
            return trainers.get(currentTrainerIndex);
        }
        return null;
    }

    public TreeMap<String, Pokemon> getPokedex() {
        return pokedex;
    }

    public HashMap<String, Movement> getDefaultMovementsMap() {
        return defaultMovementsMap;
    }

    public HashMap<String, Item> getDefaultItemsMap() {
        return defaultItemsMap;
    }

    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }
}