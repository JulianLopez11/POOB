package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class POOBkemonGUI extends JFrame {

    private POOBkemon pooBkemon;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HomeScreenPanel homePanel;
    private PlayScreenPanel playPanel;
    private PokedexPanel pokedexPanel;
    private ModePvsPPanel modePvsPPanel;
    private Fights fightsPanel;
    private TrainerSelectionPanel selectionPanel;

    public POOBkemonGUI() {
        pooBkemon = new POOBkemon();
        prepareElements();
        prepareActions();
    }

    public void prepareElements() {
        setTitle("POOBkemon");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel);

        homePanel = new HomeScreenPanel();
        contentPanel.add(homePanel, "INICIO");

        playPanel = new PlayScreenPanel();
        contentPanel.add(playPanel, "JUGAR");

        modePvsPPanel = new ModePvsPPanel();
        contentPanel.add(modePvsPPanel, "MODE PvsP");

        fightsPanel = new Fights();
        fightsPanel.setLayout(null);
        contentPanel.add(fightsPanel, "BATALLA");

        pokedexPanel = new PokedexPanel(java.util.List.of(
                "/resources/raichuFront.png",
                "/resources/charizardFront.png",
                "/resources/blastoiseFront.png",
                "/resources/venusaurFront.png"
        ));
        contentPanel.add(pokedexPanel, "POKEDEX");

        selectionPanel = new TrainerSelectionPanel(pooBkemon);
        contentPanel.add(selectionPanel, "SELECCION");
    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        homePanel.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        homePanel.getPlayButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });

        playPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "INICIO");
            }
        });

        playPanel.getPokedexButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "POKEDEX");
            }
        });

        playPanel.getPVsPButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "MODE PvsP");
            }
        });

        playPanel.getMVsMButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "BATALLA");
            }
        });

        modePvsPPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });

        modePvsPPanel.getSurvivalMode().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "BATALLA");
            }
        });

        modePvsPPanel.getNormalMode().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "SELECCION");
            }
        });

        pokedexPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });

        fightsPanel.getPokemonButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decideTurn();
            }
        });

        fightsPanel.getRunButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                run();
            }
        });


    }

    private void decideTurn() {
        Object[] options = {"Jugador", "Oponente"};
        int result = JOptionPane.showOptionDialog(
                fightsPanel,
                "¿Quién elige?",
                "Decidir Turno",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        // Si el jugador elige primero
        if (result == JOptionPane.YES_OPTION) {
            handlePlayerSelection();
        }
        // Si el oponente elige primero
        else if (result == JOptionPane.NO_OPTION) {
            handleOpponentSelection();
        }
    }

    private void handlePlayerSelection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("Elige un Pokémon (Jugador):");
        JComboBox<String> pokemonBox = new JComboBox<>();
        pokemonBox.addItem("Raichu");
        pokemonBox.addItem("Charizard");
        pokemonBox.addItem("Venusaur");
        pokemonBox.addItem("Blastoise");

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));

        java.util.Map<String, String> playerPokemonImages = java.util.Map.of(
                "Raichu", "/resources/raichuBack.png",
                "Charizard", "/resources/charizardBack.png",
                "Venusaur", "/resources/venusaurBack.png",
                "Blastoise", "/resources/blastoiseBack.png"
        );

        pokemonBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedPokemon = (String) event.getItem();
                    String imagePath = playerPokemonImages.get(selectedPokemon);
                    if (imagePath != null) {
                        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                        imageLabel.setIcon(icon);
                    }
                }
            }
        });

        String defaultPokemon = (String) pokemonBox.getSelectedItem();
        String defaultImagePath = playerPokemonImages.get(defaultPokemon);
        if (defaultImagePath != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(defaultImagePath));
            imageLabel.setIcon(icon);
        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(pokemonBox, BorderLayout.CENTER);
        panel.add(imageLabel, BorderLayout.SOUTH);

        Object[] options = {"Aceptar", "Volver"};

        int result = JOptionPane.showOptionDialog(
                fightsPanel,
                panel,
                "Seleccionar Pokémon del Jugador",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (result == JOptionPane.YES_OPTION) {
            String selectedPokemon = (String) pokemonBox.getSelectedItem();
            String selectedImagePath = playerPokemonImages.get(selectedPokemon);

            if (selectedImagePath != null) {
                fightsPanel.setPlayerPokemonImage(selectedImagePath);
                fightsPanel.setPlayerInfo(selectedPokemon, 100); // Nivel por defecto
            }

            JOptionPane.showMessageDialog(fightsPanel, "Jugador Elige a: " + selectedPokemon);
        }
    }

    private void handleOpponentSelection() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JLabel label = new JLabel("Elige un Pokémon (Oponente):");
        JComboBox<String> pokemonBox = new JComboBox<>();
        pokemonBox.addItem("Raichu");
        pokemonBox.addItem("Charizard");
        pokemonBox.addItem("Venusaur");
        pokemonBox.addItem("Blastoise");

        JLabel imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setPreferredSize(new Dimension(200, 200));

        java.util.Map<String, String> opponentPokemonImages = java.util.Map.of(
                "Raichu", "/resources/raichuFront.png",
                "Charizard", "/resources/charizardFront.png",
                "Venusaur", "/resources/venusaurFront.png",
                "Blastoise", "/resources/blastoiseFront.png"
        );

        pokemonBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent event) {
                if (event.getStateChange() == ItemEvent.SELECTED) {
                    String selectedPokemon = (String) event.getItem();
                    String imagePath = opponentPokemonImages.get(selectedPokemon);
                    if (imagePath != null) {
                        ImageIcon icon = new ImageIcon(getClass().getResource(imagePath));
                        imageLabel.setIcon(icon);
                    }
                }
            }
        });

        String defaultPokemon = (String) pokemonBox.getSelectedItem();
        String defaultImagePath = opponentPokemonImages.get(defaultPokemon);
        if (defaultImagePath != null) {
            ImageIcon icon = new ImageIcon(getClass().getResource(defaultImagePath));
            imageLabel.setIcon(icon);
        }

        panel.add(label, BorderLayout.NORTH);
        panel.add(pokemonBox, BorderLayout.CENTER);
        panel.add(imageLabel, BorderLayout.SOUTH);

        Object[] options = {"Aceptar", "Volver"};

        int result = JOptionPane.showOptionDialog(
                fightsPanel,
                panel,
                "Seleccionar Pokémon del Oponente",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                options,
                options[0]
        );

        if (result == JOptionPane.YES_OPTION) {
            String selectedPokemon = (String) pokemonBox.getSelectedItem();
            String selectedImagePath = opponentPokemonImages.get(selectedPokemon);

            if (selectedImagePath != null) {
                fightsPanel.setOpponentPokemonImage(selectedImagePath);
                fightsPanel.setOpponentInfo(selectedPokemon, 100); // Nivel por defecto
            }

            JOptionPane.showMessageDialog(fightsPanel, "El oponente eligió a: " + selectedPokemon);
        }
    }

    private void run(){
        Trainer current = pooBkemon.getCurrentTrainer();
        String message = pooBkemon.leaveGame(current);
        JOptionPane.showMessageDialog(this, message, "Fin del Combate", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(contentPanel, "INICIO");
    }

    private void closeWindow() {
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(POOBkemonGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    private void exit() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(POOBkemonGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        POOBkemonGUI pooBkemonGUI = new POOBkemonGUI();
        pooBkemonGUI.pack();
        pooBkemonGUI.setVisible(true);
    }
}