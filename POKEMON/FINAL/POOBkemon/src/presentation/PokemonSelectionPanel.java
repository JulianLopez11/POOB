package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Panel para seleccionar los Pokémon y asignar movimientos por turnos.
 */
public class PokemonSelectionPanel extends JPanel {

    private final JComboBox<String> pokemonComboBox;
    private final JComboBox<String> movementComboBox;
    private final DefaultListModel<String> assignedMovementsListModel;
    private final JList<String> assignedMovementsList;
    private final JTextArea pokemonInfoArea;
    private final List<String> playerPokemons;
    private final List<String> opponentPokemons;
    private final Map<String, Pokemon> pokedex;
    private final Map<String, Movement> defaultMovementsMap;
    private Pokemon selectedPokemon;
    private boolean isPlayerTurn = true;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final FightsPanel fightsPanel;
    private final int MIN_POKEMON = 1;
    private final int MAX_POKEMON = 6;
    private JButton confirmPokemonsButton;
    private JButton startBattleButton;

    public PokemonSelectionPanel(Map<String, Pokemon> pokedex, Map<String, Movement> defaultMovementsMap, CardLayout cardLayout, JPanel contentPanel, FightsPanel fightsPanel) {
        this.pokedex = pokedex;
        this.defaultMovementsMap = defaultMovementsMap;
        this.playerPokemons = new ArrayList<>();
        this.opponentPokemons = new ArrayList<>();
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.fightsPanel = fightsPanel;

        setLayout(new BorderLayout());

        pokemonComboBox = new JComboBox<>();
        JPanel pokemonPanel = new JPanel(new FlowLayout());
        pokemonPanel.add(new JLabel("Selecciona un Pokémon:"));
        pokemonPanel.add(pokemonComboBox);

        pokemonInfoArea = new JTextArea(10, 30);
        pokemonInfoArea.setEditable(false);
        pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Información del Pokémon"));


        movementComboBox = new JComboBox<>();
        assignedMovementsListModel = new DefaultListModel<>();
        assignedMovementsList = new JList<>(assignedMovementsListModel);

        JPanel movementsPanel = new JPanel(new GridLayout(1, 2));
        movementsPanel.setBorder(BorderFactory.createTitledBorder("Movimientos del Pokémon"));
        movementsPanel.add(new JScrollPane(assignedMovementsList));
        movementsPanel.add(movementComboBox);


        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addMovementButton = new JButton("Añadir Movimiento");
        JButton removeMovementButton = new JButton("Quitar Movimiento");
        JButton confirmPokemonButton = new JButton("Confirmar Pokémon");
        confirmPokemonsButton = new JButton("Confirmar Selección");
        startBattleButton = new JButton("Iniciar Batalla");
        buttonPanel.add(addMovementButton);
        buttonPanel.add(removeMovementButton);
        buttonPanel.add(confirmPokemonButton);
        buttonPanel.add(confirmPokemonsButton);
        buttonPanel.add(startBattleButton);
        add(pokemonPanel, BorderLayout.NORTH);
        add(pokemonInfoArea, BorderLayout.WEST);
        add(movementsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        // Listeners
        pokemonComboBox.addActionListener(e -> onPokemonSelected());
        addMovementButton.addActionListener(e -> addMovement());
        removeMovementButton.addActionListener(e -> removeMovement());
        confirmPokemonButton.addActionListener(e -> confirmPokemon());
        confirmPokemonsButton.addActionListener(e -> confirmAllPokemons());
        startBattleButton.addActionListener(e -> startBattle());

        updatePokemonComboBox();
        updateTurnIndicator();
    }

    private void updatePokemonComboBox() {
        pokemonComboBox.removeAllItems();
        for (String pokemonName : pokedex.keySet()) {
            pokemonComboBox.addItem(pokemonName);
        }
    }

    private void onPokemonSelected() {
        String selectedPokemonName = (String) pokemonComboBox.getSelectedItem();
        if (selectedPokemonName != null) {
            selectedPokemon = pokedex.get(selectedPokemonName);
            updatePokemonInfoArea();
            updateMovementComboBox();
            updateAssignedMovementsList();
        }
    }

    private void updatePokemonInfoArea() {
        if (selectedPokemon != null) {
            StringBuilder info = new StringBuilder();
            info.append("Nombre: ").append(selectedPokemon.getName()).append("\n");
            info.append("Tipo Principal: ").append(selectedPokemon.getPrincipalType()).append("\n");
            if (selectedPokemon.getSecondaryType() != null) {
                info.append("Tipo Secundario: ").append(selectedPokemon.getSecondaryType()).append("\n");
            }
            info.append("Movimientos:\n");
            for (Movement movement : selectedPokemon.getMovements()) {
                info.append(" - ").append(movement.getName()).append("\n");
            }
            pokemonInfoArea.setText(info.toString());
        } else {
            pokemonInfoArea.setText("Selecciona un Pokémon para ver su información.");
        }
    }

    private void updateMovementComboBox() {
        movementComboBox.removeAllItems();
        for (String movementName : defaultMovementsMap.keySet()) {
            movementComboBox.addItem(movementName);
        }
    }

    private void updateAssignedMovementsList() {
        assignedMovementsListModel.clear();
        if (selectedPokemon != null) {
            for (Movement movement : selectedPokemon.getMovements()) {
                assignedMovementsListModel.addElement(movement.getName());
            }
        }
    }

    private void addMovement() {
        if (selectedPokemon != null && movementComboBox.getSelectedItem() != null) {
            String movementName = (String) movementComboBox.getSelectedItem();
            Movement movement = defaultMovementsMap.get(movementName);
            boolean alreadyAssigned = selectedPokemon.getMovements().stream()
                    .anyMatch(existingMovement -> existingMovement.getName().equals(movementName));

            if (alreadyAssigned) {
                JOptionPane.showMessageDialog(this,
                        "El movimiento '" + movementName + "' ya está asignado a este Pokémon.",
                        "Movimiento Duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (selectedPokemon.addMovement(movement)) {
                updateAssignedMovementsList();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Un Pokémon no puede tener más de 4 movimientos.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un movimiento para añadir.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removeMovement() {
        if (selectedPokemon != null && assignedMovementsList.getSelectedValue() != null) {
            String movementName = assignedMovementsList.getSelectedValue();
            selectedPokemon.getMovements().removeIf(movement -> movement.getName().equals(movementName));
            assignedMovementsListModel.removeElement(movementName);
            JOptionPane.showMessageDialog(this,
                    "El movimiento '" + movementName + "' ha sido eliminado del Pokémon.",
                    "Movimiento Eliminado", JOptionPane.INFORMATION_MESSAGE);
            if (selectedPokemon.getMovements().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Este Pokémon ya no tiene movimientos asignados. No se puede confirmar hasta asignar al menos uno.",
                        "Sin movimientos", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Por favor selecciona un movimiento para eliminar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmPokemon() {
        if (selectedPokemon != null) {
            if (selectedPokemon.getMovements() == null || selectedPokemon.getMovements().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No puedes confirmar un Pokémon sin movimientos asignados.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            System.out.println("Movimientos del Pokémon antes de confirmar: " + selectedPokemon.getMovements().size());
            if (isPlayerTurn) {
                playerPokemons.add(selectedPokemon.getName());
                JOptionPane.showMessageDialog(this,
                        "Pokémon asignado al jugador: " + selectedPokemon.getName(),
                        "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            } else {
                opponentPokemons.add(selectedPokemon.getName());
                JOptionPane.showMessageDialog(this,
                        "Pokémon asignado al oponente: " + selectedPokemon.getName(),
                        "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            }
            selectedPokemon.getMovements().clear();
            updateAssignedMovementsList();
            selectedPokemon = null;
            updateTurnIndicator();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecciona un Pokémon para confirmar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void confirmAllPokemons() {
        if (isPlayerTurn && playerPokemons.size() >= MIN_POKEMON && playerPokemons.size() <= MAX_POKEMON) {
            JOptionPane.showMessageDialog(this,
                    "Turno del oponente. El jugador ha terminado su selección.",
                    "Cambio de Turno", JOptionPane.INFORMATION_MESSAGE);
            isPlayerTurn = false;
            resetForOpponentSelection();
        } else if (!isPlayerTurn && opponentPokemons.size() >= MIN_POKEMON && opponentPokemons.size() <= MAX_POKEMON) {
            JOptionPane.showMessageDialog(this,
                    "Ambos jugadores han terminado de seleccionar sus Pokémon. ¡Presiona 'Iniciar Batalla' para continuar!",
                    "Selección Completa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "Debes seleccionar entre " + MIN_POKEMON + " y " + MAX_POKEMON + " Pokémon antes de confirmar.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetForOpponentSelection() {
        opponentPokemons.clear();
        updateTurnIndicator();
        updatePokemonComboBox();
        selectedPokemon = null;
        updateAssignedMovementsList();
        pokemonInfoArea.setText("");
    }

    private void updateTurnIndicator() {
        if (isPlayerTurn) {
            pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Turno del Jugador"));
        } else {
            pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Turno del Oponente"));
        }
    }

    public void startBattle() {
        if (playerPokemons.size() < MIN_POKEMON || opponentPokemons.size() < MIN_POKEMON) {
            JOptionPane.showMessageDialog(this,
                    "Ambos jugadores deben seleccionar al menos " + MIN_POKEMON + " Pokémon antes de iniciar la batalla.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        fightsPanel.setPlayerTeam(playerPokemons);
        fightsPanel.setOpponentTeam(opponentPokemons);
        JOptionPane.showMessageDialog(contentPanel,
                "¡La batalla ha comenzado! Prepárate para luchar.",
                "Inicio de Batalla", JOptionPane.INFORMATION_MESSAGE);
        cardLayout.show(contentPanel, "BATALLA");
    }

    public JButton getStartButton(){
        return startBattleButton;
    }


    public List<String> getPlayerPokemons() {
        return playerPokemons;
    }

    public List<String> getOpponentPokemons() {
        return opponentPokemons;
    }
}
