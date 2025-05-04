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

    private final JComboBox<String> pokemonComboBox; // Lista desplegable de Pokémon
    private final JComboBox<String> movementComboBox; // Lista desplegable de movimientos
    private final DefaultListModel<String> assignedMovementsListModel; // Modelo para movimientos asignados
    private final JList<String> assignedMovementsList; // Lista de movimientos asignados
    private final JTextArea pokemonInfoArea; // Área para mostrar información del Pokémon seleccionado
    private final List<String> playerPokemons; // Lista de Pokémon seleccionados para el jugador
    private final List<String> opponentPokemons; // Lista de Pokémon seleccionados para el oponente
    private final Map<String, Pokemon> pokedex; // Mapa de Pokémon disponibles
    private final Map<String, Movement> defaultMovementsMap; // Mapa de movimientos predeterminados
    private Pokemon selectedPokemon; // Pokémon actualmente seleccionado
    private boolean isPlayerTurn = true; // Controla si es el turno del jugador
    private final CardLayout cardLayout; // CardLayout para cambiar entre paneles
    private final JPanel contentPanel; // Panel principal que contiene todos los subpaneles
    private final FightsPanel fightsPanel; // Panel de batalla
    private final int MIN_POKEMON = 1; // Mínimo número de Pokémon
    private final int MAX_POKEMON = 6; // Máximo número de Pokémon
    private JButton confirmPokemonsButton; // Botón para confirmar todos los Pokémon seleccionados

    public PokemonSelectionPanel(Map<String, Pokemon> pokedex, Map<String, Movement> defaultMovementsMap, CardLayout cardLayout, JPanel contentPanel, FightsPanel fightsPanel) {
        this.pokedex = pokedex;
        this.defaultMovementsMap = defaultMovementsMap;
        this.playerPokemons = new ArrayList<>();
        this.opponentPokemons = new ArrayList<>();
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.fightsPanel = fightsPanel;

        setLayout(new BorderLayout());

        // Componentes para seleccionar Pokémon
        pokemonComboBox = new JComboBox<>();
        JPanel pokemonPanel = new JPanel(new FlowLayout());
        pokemonPanel.add(new JLabel("Selecciona un Pokémon:"));
        pokemonPanel.add(pokemonComboBox);

        // Área para mostrar información del Pokémon
        pokemonInfoArea = new JTextArea(10, 30);
        pokemonInfoArea.setEditable(false);
        pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Información del Pokémon"));

        // Componentes para asignar movimientos
        movementComboBox = new JComboBox<>();
        assignedMovementsListModel = new DefaultListModel<>();
        assignedMovementsList = new JList<>(assignedMovementsListModel);

        JPanel movementsPanel = new JPanel(new GridLayout(1, 2));
        movementsPanel.setBorder(BorderFactory.createTitledBorder("Movimientos del Pokémon"));
        movementsPanel.add(new JScrollPane(assignedMovementsList));
        movementsPanel.add(movementComboBox);

        // Botones para añadir/quitar movimientos
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addMovementButton = new JButton("Añadir Movimiento");
        JButton removeMovementButton = new JButton("Quitar Movimiento");
        JButton confirmPokemonButton = new JButton("Confirmar Pokémon");
        confirmPokemonsButton = new JButton("Confirmar Selección"); // Botón para confirmar todos los Pokémon
        JButton startBattleButton = new JButton("Iniciar Batalla"); // Botón para iniciar la batalla
        buttonPanel.add(addMovementButton);
        buttonPanel.add(removeMovementButton);
        buttonPanel.add(confirmPokemonButton);
        buttonPanel.add(confirmPokemonsButton); // Añadir el botón de confirmar selección
        buttonPanel.add(startBattleButton);

        // Añadir componentes al panel principal
        add(pokemonPanel, BorderLayout.NORTH);
        add(pokemonInfoArea, BorderLayout.WEST); // Área de información a la izquierda
        add(movementsPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        pokemonComboBox.addActionListener(e -> onPokemonSelected());
        addMovementButton.addActionListener(e -> addMovement());
        removeMovementButton.addActionListener(e -> removeMovement());
        confirmPokemonButton.addActionListener(e -> confirmPokemon());
        confirmPokemonsButton.addActionListener(e -> confirmAllPokemons());
        startBattleButton.addActionListener(e -> startBattle()); // Listener para el botón de iniciar batalla

        // Inicializar la lista de Pokémon en el ComboBox
        updatePokemonComboBox();
        updateTurnIndicator(); // Actualizar las instrucciones del turno inicial
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
        // Hacer que todos los movimientos estén disponibles para cualquier Pokémon
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

            // Verificar si el movimiento ya está asignado al Pokémon
            boolean alreadyAssigned = selectedPokemon.getMovements().stream()
                    .anyMatch(existingMovement -> existingMovement.getName().equals(movementName));

            if (alreadyAssigned) {
                JOptionPane.showMessageDialog(this,
                        "El movimiento '" + movementName + "' ya está asignado a este Pokémon.",
                        "Movimiento Duplicado", JOptionPane.WARNING_MESSAGE);
                return; // Detener si el movimiento ya está asignado
            }

            // Intentar añadir el movimiento
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

            // Eliminar el movimiento del Pokémon
            selectedPokemon.getMovements().removeIf(movement -> movement.getName().equals(movementName));

            // Eliminar el movimiento de la lista visible
            assignedMovementsListModel.removeElement(movementName);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(this,
                    "El movimiento '" + movementName + "' ha sido eliminado del Pokémon.",
                    "Movimiento Eliminado", JOptionPane.INFORMATION_MESSAGE);

            // Verificar si ya no tiene movimientos
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
            // Validar que el Pokémon tenga al menos un movimiento asignado
            if (selectedPokemon.getMovements() == null || selectedPokemon.getMovements().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "No puedes confirmar un Pokémon sin movimientos asignados.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return; // Detener si no tiene movimientos
            }

            // Mostrar en consola los movimientos asignados (para depuración)
            System.out.println("Movimientos del Pokémon antes de confirmar: " + selectedPokemon.getMovements().size());

            // Añadir el Pokémon al equipo correspondiente según el turno
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

            // Limpiar los movimientos del Pokémon confirmado
            selectedPokemon.getMovements().clear();
            updateAssignedMovementsList();

            // Limpiar la selección del Pokémon actual para evitar errores
            selectedPokemon = null;

            // Actualizar el indicador de turno para el siguiente jugador
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
            isPlayerTurn = false; // Cambiar al turno del oponente
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
        opponentPokemons.clear(); // Preparar la lista para el oponente
        updateTurnIndicator();
        updatePokemonComboBox(); // Reiniciar el ComboBox para el oponente
        selectedPokemon = null; // Reiniciar la selección actual
        updateAssignedMovementsList();
        pokemonInfoArea.setText(""); // Limpiar el área de información
    }

    private void updateTurnIndicator() {
        if (isPlayerTurn) {
            pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Turno del Jugador"));
        } else {
            pokemonInfoArea.setBorder(BorderFactory.createTitledBorder("Turno del Oponente"));
        }
    }

    private void startBattle() {
        if (playerPokemons.size() < MIN_POKEMON || opponentPokemons.size() < MIN_POKEMON) {
            JOptionPane.showMessageDialog(this,
                    "Ambos jugadores deben seleccionar al menos " + MIN_POKEMON + " Pokémon antes de iniciar la batalla.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Configurar los equipos en el FightsPanel
        fightsPanel.setPlayerTeam(playerPokemons);
        fightsPanel.setOpponentTeam(opponentPokemons);

        // Mostrar mensaje de inicio de batalla
        JOptionPane.showMessageDialog(contentPanel,
                "¡La batalla ha comenzado! Prepárate para luchar.",
                "Inicio de Batalla", JOptionPane.INFORMATION_MESSAGE);

        // Cambiar al panel de batalla
        cardLayout.show(contentPanel, "BATALLA");
    }

    public List<String> getPlayerPokemons() {
        return playerPokemons;
    }

    public List<String> getOpponentPokemons() {
        return opponentPokemons;
    }
}