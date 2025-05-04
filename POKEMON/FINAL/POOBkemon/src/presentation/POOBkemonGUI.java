package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class POOBkemonGUI extends JFrame {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HomeScreenPanel homePanel;
    private PlayScreenPanel playPanel;
    private PokedexPanel pokedexPanel;
    private ModePvsPPanel modePvsPPanel;
    private FightsPanel fightsPanel;
    private PokemonSelectionPanel pokemonSelectionPanel;
    private boolean isPaused = false;
    private POOBkemon pooBkemon; // Instancia de la clase POOBkemon

    private List<String> availablePlayerPokemons;  // Lista de Pokémon disponibles para el jugador
    private List<String> availableOpponentPokemons; // Lista de Pokémon disponibles para el oponente
    private Map<String, Pokemon> pokedex; // Mapa de Pokémon disponibles

    public POOBkemonGUI() {
        // Inicializar la instancia de POOBkemon
        pooBkemon = new POOBkemon();
        prepareElements();
        prepareActions();
    }

    public void prepareElements() {
        setTitle("POOBkemon");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Establecer CardLayout
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel);

        // Inicializar paneles
        homePanel = new HomeScreenPanel();
        contentPanel.add(homePanel, "INICIO");

        playPanel = new PlayScreenPanel();
        contentPanel.add(playPanel, "JUGAR");

        modePvsPPanel = new ModePvsPPanel();
        contentPanel.add(modePvsPPanel, "MODE PvsP");

        fightsPanel = new FightsPanel();
        fightsPanel.setLayout(null);
        contentPanel.add(fightsPanel, "BATALLA");

        // Crear el mapa de Pokémon disponibles (Pokedex)
        pokedex = createPokedex();

        // Crear el mapa de movimientos predeterminados
        Map<String, Movement> defaultMovementsMap = createDefaultMovements();

        // Inicializar el panel de selección de Pokémon
        pokemonSelectionPanel = new PokemonSelectionPanel(pokedex, defaultMovementsMap, cardLayout, contentPanel, fightsPanel);
        contentPanel.add(pokemonSelectionPanel, "POKEMON SELECTION");

        // Inicializar el PokedexPanel con imágenes
        List<String> pokedexImages = new ArrayList<>(pokedex.keySet()); // Usa los nombres de los Pokémon para las imágenes
        pokedexPanel = new PokedexPanel(pokedexImages);
        contentPanel.add(pokedexPanel, "POKEDEX");

        // Inicializar listas de Pokémon disponibles
        availablePlayerPokemons = new ArrayList<>();
        availableOpponentPokemons = new ArrayList<>();
    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        // Acciones de botones principales
        homePanel.getExitButton().addActionListener(e -> exit());
        homePanel.getPlayButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        playPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "INICIO"));
        playPanel.getPokedexButton().addActionListener(e -> cardLayout.show(contentPanel, "POKEDEX"));
        playPanel.getPVsPButton().addActionListener(e -> cardLayout.show(contentPanel, "MODE PvsP"));
        playPanel.getMVsMButton().addActionListener(e -> cardLayout.show(contentPanel, "BATALLA"));

        modePvsPPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));
        modePvsPPanel.getSurvivalMode().addActionListener(e -> cardLayout.show(contentPanel, "BATALLA"));
        modePvsPPanel.getNormalMode().addActionListener(e -> cardLayout.show(contentPanel, "POKEMON SELECTION"));

        pokedexPanel.getBackButton().addActionListener(e -> cardLayout.show(contentPanel, "JUGAR"));

        fightsPanel.getRunButton().addActionListener(e -> run());
        fightsPanel.getPauseButton().addActionListener(e -> pause());
        fightsPanel.getPokemonButton().addActionListener(e -> handlePokemonSelection());
        fightsPanel.getBagButton().addActionListener(e -> JOptionPane.showMessageDialog(this, "Acción de la mochila no implementada.", "Mochila", JOptionPane.INFORMATION_MESSAGE));
        fightsPanel.getFightButton().addActionListener(e -> JOptionPane.showMessageDialog(this, "Acción de pelear no implementada.", "Pelear", JOptionPane.INFORMATION_MESSAGE));
    }

    private TreeMap<String, Pokemon> createPokedex() {
        TreeMap<String, Pokemon> finalPokedex = new TreeMap<>();
        TreeMap<String, Pokemon> arrayPokedex = pooBkemon.getPokedex();
        for (Map.Entry<String, Pokemon> entry : arrayPokedex.entrySet()) {
            String key = entry.getKey();
            Pokemon value = entry.getValue();
            finalPokedex.put(key, value);
        }
        return finalPokedex;
    }

    private HashMap<String, Movement> createDefaultMovements() {
        HashMap<String, Movement> finalMovements = new HashMap<>();
        HashMap<String, Movement> arrayMovements = pooBkemon.getDefaultMovementsMap();
        for (Map.Entry<String, Movement> entry : arrayMovements.entrySet()) {
            String key = entry.getKey();
            Movement value = entry.getValue();
            finalMovements.put(key, value);
        }
        return finalMovements;
    }

    private HashMap<String, Item> createDefaultItems() {
        HashMap<String, Item> finalItems = new HashMap<>();
        HashMap<String, Item> arrayItems = pooBkemon.getDefaultItemsMap();
        for (Map.Entry<String, Item> entry : arrayItems.entrySet()) {
            String key = entry.getKey();
            Item value = entry.getValue();
            finalItems.put(key, value);
        }
        return finalItems;
    }
    public void handlePokemonSelection() {
        Object[] options = {"Jugador", "Oponente"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "¿Quién cambiará su Pokémon?",
                "Cambio de Pokémon",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == JOptionPane.YES_OPTION) {
            fightsPanel.changePlayerPokemon(); // Cambiar Pokémon del jugador
        } else if (choice == JOptionPane.NO_OPTION) {
            fightsPanel.changeOpponentPokemon(); // Cambiar Pokémon del oponente
        }
    }

    public void pause() {
        isPaused = !isPaused;

        fightsPanel.getFightButton().setEnabled(!isPaused);
        fightsPanel.getBagButton().setEnabled(!isPaused);
        fightsPanel.getPokemonButton().setEnabled(!isPaused);
        fightsPanel.getRunButton().setEnabled(!isPaused);

        if (isPaused) {
            fightsPanel.getPauseButton().setText("RESUME");
            JOptionPane.showMessageDialog(this, "La batalla está pausada.", "Pausa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            fightsPanel.getPauseButton().setText("PAUSE");
            JOptionPane.showMessageDialog(this, "La batalla se ha reanudado.", "Reanudar", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void run() {
        JOptionPane.showMessageDialog(this, "El jugador ha decidido huir.", "Fin del Combate", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(contentPanel, "INICIO");
    }

    public void closeWindow() {
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public void exit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        POOBkemonGUI pooBkemonGUI = new POOBkemonGUI();
        pooBkemonGUI.setSize(800, 600);  // Tamaño inicial
        pooBkemonGUI.setLocationRelativeTo(null); // Centrar en la pantalla
        pooBkemonGUI.setVisible(true);
    }
}