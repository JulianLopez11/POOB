package src.presentation;

import src.domain.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Panel para la selección de pokemones, movimientos e ítems para cada entrenador
 * @author Julian López y Sebastian Puentes
 * @version 1.0
 */
public class TrainerSelectionPanel extends JPanel {
    private POOBkemon poobkemon;
    private JPanel trainerPanel;
    private JPanel pokemonSelectionPanel;
    private JPanel movementsSelectionPanel;
    private JPanel itemsSelectionPanel;
    
    private JTextField trainerNameField;
    private JComboBox<String> trainerTypeComboBox;
    private JButton confirmTrainerButton;
    
    private JComboBox<String> pokemonComboBox;
    private JButton addPokemonButton;
    private JList<String> selectedPokemonList;
    private DefaultListModel<String> pokemonListModel;
    
    private JComboBox<String> movementComboBox;
    private JButton addMovementButton;
    private JList<String> selectedMovementsList;
    private DefaultListModel<String> movementsListModel;
    
    private JComboBox<String> itemComboBox;
    private JButton addItemButton;
    private JList<String> selectedItemsList;
    private DefaultListModel<String> itemsListModel;
    
    private ArrayList<Trainer> trainers;
    private Trainer currentTrainer;
    private Pokemon selectedPokemon;
    
    private JButton finishSelectionButton;
    private JLabel instructionLabel;

    /**
     * Constructor del panel de selección de entrenadores
     * @param poobkemon Instancia del juego
     */
    public TrainerSelectionPanel(POOBkemon poobkemon) {
        this.poobkemon = poobkemon;
        this.trainers = new ArrayList<>();
        
        setLayout(new BorderLayout());
        
        // Panel de instrucciones
        JPanel instructionsPanel = new JPanel();
        instructionLabel = new JLabel("Configuración de entrenador 1");
        instructionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        instructionsPanel.add(instructionLabel);
        add(instructionsPanel, BorderLayout.NORTH);
        
        // Panel principal
        JPanel mainPanel = new JPanel(new GridLayout(1, 2));
        
        // Panel izquierdo: Entrenador y Pokémon
        JPanel leftPanel = new JPanel(new GridLayout(2, 1));
        
        // Panel de entrenador
        trainerPanel = createTrainerPanel();
        leftPanel.add(trainerPanel);
        
        // Panel de selección de Pokémon
        pokemonSelectionPanel = createPokemonSelectionPanel();
        leftPanel.add(pokemonSelectionPanel);
        
        // Panel derecho: Movimientos e Items
        JPanel rightPanel = new JPanel(new GridLayout(2, 1));
        
        // Panel de selección de Movimientos
        movementsSelectionPanel = createMovementsSelectionPanel();
        rightPanel.add(movementsSelectionPanel);
        
        // Panel de selección de Items
        itemsSelectionPanel = createItemsSelectionPanel();
        rightPanel.add(itemsSelectionPanel);
        
        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Panel de botón de finalizar
        JPanel finishPanel = new JPanel();
        finishSelectionButton = new JButton("Finalizar selección");
        finishSelectionButton.setEnabled(false);
        finishSelectionButton.addActionListener(e -> finishSelection());
        finishPanel.add(finishSelectionButton);
        
        add(finishPanel, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    /**
     * Crea el panel para la información del entrenador
     * @return Panel configurado
     */
    private JPanel createTrainerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
                "Información del entrenador", TitledBorder.CENTER, TitledBorder.TOP));
        
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.add(new JLabel("Nombre:"));
        trainerNameField = new JTextField(15);
        namePanel.add(trainerNameField);
        
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        typePanel.add(new JLabel("Tipo:"));
        String[] trainerTypes = {"Normal", "Atacante", "Defensivo", "Cambiante", "Experto"};
        trainerTypeComboBox = new JComboBox<>(trainerTypes);
        typePanel.add(trainerTypeComboBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        confirmTrainerButton = new JButton("Confirmar entrenador");
        confirmTrainerButton.addActionListener(e -> confirmTrainer());
        buttonPanel.add(confirmTrainerButton);
        
        panel.add(namePanel);
        panel.add(typePanel);
        panel.add(buttonPanel);
        
        return panel;
    }
    
    /**
     * Crea el panel para la selección de Pokémon
     * @return Panel configurado
     */
    private JPanel createPokemonSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
                "Selección de Pokémon", TitledBorder.CENTER, TitledBorder.TOP));
        
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Pokémon:"));
        pokemonComboBox = new JComboBox<>();
        updatePokemonComboBox();
        selectionPanel.add(pokemonComboBox);
        
        addPokemonButton = new JButton("Añadir al equipo");
        addPokemonButton.setEnabled(false);
        addPokemonButton.addActionListener(e -> addPokemonToTeam());
        selectionPanel.add(addPokemonButton);
        
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Equipo Pokémon:"), BorderLayout.NORTH);
        
        pokemonListModel = new DefaultListModel<>();
        selectedPokemonList = new JList<>(pokemonListModel);
        selectedPokemonList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectedPokemonList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selected = selectedPokemonList.getSelectedValue();
                if (selected != null && currentTrainer != null) {
                    for (Pokemon p : currentTrainer.getTeam()) {
                        if (p.getName().equals(selected)) {
                            selectedPokemon = p;
                            updateMovementComboBox();
                            addMovementButton.setEnabled(true);
                            break;
                        }
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(selectedPokemonList);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(selectionPanel);
        panel.add(listPanel);
        
        return panel;
    }
    
    /**
     * Crea el panel para la selección de movimientos
     * @return Panel configurado
     */
    private JPanel createMovementsSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
                "Selección de Movimientos", TitledBorder.CENTER, TitledBorder.TOP));
        
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Movimiento:"));
        movementComboBox = new JComboBox<>();
        selectionPanel.add(movementComboBox);
        
        addMovementButton = new JButton("Añadir movimiento");
        addMovementButton.setEnabled(false);
        addMovementButton.addActionListener(e -> addMovementToPokemon());
        selectionPanel.add(addMovementButton);
        
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Movimientos asignados:"), BorderLayout.NORTH);
        
        movementsListModel = new DefaultListModel<>();
        selectedMovementsList = new JList<>(movementsListModel);
        JScrollPane scrollPane = new JScrollPane(selectedMovementsList);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(selectionPanel);
        panel.add(listPanel);
        
        return panel;
    }
    
    /**
     * Crea el panel para la selección de items
     * @return Panel configurado
     */
    private JPanel createItemsSelectionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), 
                "Selección de Items", TitledBorder.CENTER, TitledBorder.TOP));
        
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.add(new JLabel("Item:"));
        itemComboBox = new JComboBox<>();
        updateItemComboBox();
        selectionPanel.add(itemComboBox);
        
        addItemButton = new JButton("Añadir item");
        addItemButton.setEnabled(false);
        addItemButton.addActionListener(e -> addItemToTrainer());
        selectionPanel.add(addItemButton);
        
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(new JLabel("Items en inventario:"), BorderLayout.NORTH);
        
        itemsListModel = new DefaultListModel<>();
        selectedItemsList = new JList<>(itemsListModel);
        JScrollPane scrollPane = new JScrollPane(selectedItemsList);
        scrollPane.setPreferredSize(new Dimension(300, 100));
        listPanel.add(scrollPane, BorderLayout.CENTER);
        
        panel.add(selectionPanel);
        panel.add(listPanel);
        
        return panel;
    }
    
    /**
     * Actualiza el ComboBox de Pokémon con la lista de la Pokédex
     */
    private void updatePokemonComboBox() {
        pokemonComboBox.removeAllItems();
        for (Map.Entry<String, Pokemon> entry : poobkemon.getPokedex().entrySet()) {
            pokemonComboBox.addItem(entry.getKey());
        }
    }
    
    /**
     * Actualiza el ComboBox de movimientos con los disponibles
     */
    private void updateMovementComboBox() {
        movementComboBox.removeAllItems();
        movementsListModel.clear();
        
        if (selectedPokemon != null) {
            // Mostrar los movimientos actuales del Pokémon
            for (Movement m : selectedPokemon.getMovements()) {
                movementsListModel.addElement(m.getName());
            }
            
            // Añadir los movimientos disponibles que sean compatibles con el tipo del Pokémon
            for (Map.Entry<String, Movement> entry : poobkemon.getDefaultMovementsMap().entrySet()) {
                Movement movement = entry.getValue();
                // Verificar compatibilidad de tipo o si es de tipo Normal (todos pueden aprenderlo)
                if (selectedPokemon.getPrincipalType().equals(movement.getType()) ||
                    movement.getType() == PokemonType.NORMAL) {
                    movementComboBox.addItem(entry.getKey());
                }
            }
        }
    }
    
    /**
     * Actualiza el ComboBox de ítems con los disponibles
     */
    private void updateItemComboBox() {
        itemComboBox.removeAllItems();
        for (Map.Entry<String, Item> entry : poobkemon.getDefaultItemsMap().entrySet()) {
            itemComboBox.addItem(entry.getKey());
        }
    }
    
    /**
     * Confirma la creación del entrenador actual
     */
    private void confirmTrainer() {
        String name = trainerNameField.getText();
        if (name == null || name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre del entrenador no puede estar vacío", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Crear entrenador según el tipo seleccionado
        String type = (String) trainerTypeComboBox.getSelectedItem();
        switch (type) {
            case "Normal":
                currentTrainer = new Trainer(name);
                break;
            case "Atacante":
                currentTrainer = new AttackingTrainer(name);
                break;
            case "Defensivo":
                currentTrainer = new DefensiveTrainer(name);
                break;
            case "Cambiante":
                currentTrainer = new ChangingTrainer(name);
                break;
            case "Experto":
                currentTrainer = new ExpertTrainer(name);
                break;
        }
        
        // Habilitar controles
        addPokemonButton.setEnabled(true);
        addItemButton.setEnabled(true);
        confirmTrainerButton.setEnabled(false);
        trainerNameField.setEnabled(false);
        trainerTypeComboBox.setEnabled(false);
        
        // Limpiar listas
        pokemonListModel.clear();
        movementsListModel.clear();
        itemsListModel.clear();
    }
    
    /**
     * Añade un Pokémon al equipo del entrenador
     */
    private void addPokemonToTeam() {
        if (currentTrainer == null) {
            JOptionPane.showMessageDialog(this, "Debe confirmar el entrenador primero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String pokemonName = (String) pokemonComboBox.getSelectedItem();
        if (pokemonName != null) {
            Pokemon pokemon = poobkemon.getPokedex().get(pokemonName);
            
            // Verificar si ya se tienen 6 pokémon en el equipo
            if (currentTrainer.getTeam().size() >= 6) {
                JOptionPane.showMessageDialog(this, "El equipo ya tiene el máximo de 6 Pokémon", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si el pokémon ya está en el equipo
            for (Pokemon p : currentTrainer.getTeam()) {
                if (p.getName().equals(pokemonName)) {
                    JOptionPane.showMessageDialog(this, "Este Pokémon ya está en el equipo", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            // Crear una copia del pokémon para no modificar el original en la pokédex
            Pokemon newPokemon = new Pokemon(pokemon.getName(), pokemon.getPrincipalType());
            if (pokemon.getSecondaryType() != null) {
                newPokemon = new Pokemon(pokemon.getName(), pokemon.getPrincipalType(), pokemon.getSecondaryType());
            }
            
            currentTrainer.addPokemon(newPokemon);
            pokemonListModel.addElement(newPokemon.getName());
            
            // Si es el primer pokémon, seleccionarlo automáticamente
            if (currentTrainer.getTeam().size() == 1) {
                selectedPokemon = newPokemon;
                selectedPokemonList.setSelectedIndex(0);
                updateMovementComboBox();
            }
            
            // Habilitar el botón de finalizar si se cumple el mínimo de pokémon (al menos 1)
            if (currentTrainer.getTeam().size() > 0) {
                finishSelectionButton.setEnabled(true);
            }
        }
    }
    
    /**
     * Añade un movimiento al Pokémon seleccionado
     */
    private void addMovementToPokemon() {
        if (selectedPokemon == null) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un Pokémon primero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String movementName = (String) movementComboBox.getSelectedItem();
        if (movementName != null) {
            Movement movement = poobkemon.getDefaultMovementsMap().get(movementName);
            
            // Verificar si ya tiene 4 movimientos
            if (selectedPokemon.getMovements().size() >= 4) {
                JOptionPane.showMessageDialog(this, "Este Pokémon ya tiene el máximo de 4 movimientos", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Verificar si ya tiene ese movimiento
            for (Movement m : selectedPokemon.getMovements()) {
                if (m.getName().equals(movementName)) {
                    JOptionPane.showMessageDialog(this, "Este Pokémon ya tiene este movimiento", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            
            poobkemon.addSelectedMovementToPokemon(movement, selectedPokemon);
            movementsListModel.addElement(movement.getName());
        }
    }
    
    /**
     * Añade un ítem al inventario del entrenador
     */
    private void addItemToTrainer() {
        if (currentTrainer == null) {
            JOptionPane.showMessageDialog(this, "Debe confirmar el entrenador primero", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String itemName = (String) itemComboBox.getSelectedItem();
        if (itemName != null) {
            Item item = poobkemon.getDefaultItemsMap().get(itemName);
            currentTrainer.addItem(item);
            itemsListModel.addElement(item.getName());
        }
    }
    
    /**
     * Finaliza la selección del entrenador actual
     */
    private void finishSelection() {
        // Verificar que tenga al menos un Pokémon
        if (currentTrainer.getTeam().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El entrenador debe tener al menos un Pokémon", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Verificar que cada Pokémon tenga al menos un movimiento
        for (Pokemon p : currentTrainer.getTeam()) {
            if (p.getMovements().isEmpty()) {
                JOptionPane.showMessageDialog(this, 
                        "El Pokémon " + p.getName() + " debe tener al menos un movimiento", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
        
        // Añadir entrenador a la lista
        trainers.add(currentTrainer);
        
        // Si ya hay dos entrenadores, pasar a la pantalla de batalla
        if (trainers.size() == 2) {
            // Establecer los entrenadores en el juego
            poobkemon.setTrainers(trainers);
            
            // Notificar a los escuchadores que se completó la selección
            firePropertyChange("selectionComplete", false, true);
        } else {
            // Preparar para el segundo entrenador
            resetForSecondTrainer();
        }
    }
    
    /**
     * Reinicia el panel para configurar el segundo entrenador
     */
    private void resetForSecondTrainer() {
        currentTrainer = null;
        selectedPokemon = null;
        
        // Habilitar los controles del entrenador
        trainerNameField.setEnabled(true);
        trainerNameField.setText("");
        trainerTypeComboBox.setEnabled(true);
        confirmTrainerButton.setEnabled(true);
        
        // Deshabilitar los otros botones
        addPokemonButton.setEnabled(false);
        addMovementButton.setEnabled(false);
        addItemButton.setEnabled(false);
        finishSelectionButton.setEnabled(false);
        
        // Limpiar las listas
        pokemonListModel.clear();
        movementsListModel.clear();
        itemsListModel.clear();
        
        // Actualizar etiqueta de instrucciones
        instructionLabel.setText("Configuración de entrenador 2");
    }
    
    /**
     * Obtiene los entrenadores configurados
     * @return Lista de entrenadores
     */
    public ArrayList<Trainer> getTrainers() {
        return trainers;
    }
}
