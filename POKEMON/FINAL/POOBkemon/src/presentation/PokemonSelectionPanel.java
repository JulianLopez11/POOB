package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel para seleccionar los Pokémon para la formación de equipo.
 */
public class PokemonSelectionPanel extends JPanel {

    private final List<String> availablePokemons;
    private final List<String> selectedPokemons;
    private final JButton confirmButton;
    private final JList<String> availableList;
    private final JList<String> selectedList;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final FightsPanel fightsPanel;

    private boolean isPlayerSelecting;
    private List<String> playerTeam;
    private List<String> opponentTeam;

    public PokemonSelectionPanel(CardLayout cardLayout, JPanel contentPanel, FightsPanel fightsPanel) {
        this.cardLayout = cardLayout;
        this.contentPanel = contentPanel;
        this.fightsPanel = fightsPanel;

        setLayout(new BorderLayout());

        availablePokemons = new ArrayList<>(List.of(
                "Raichu", "Charizard", "Venusaur", "Blastoise",
                "Delibird", "Donphan", "Dragonite", "Gardevoir",
                "Gengar", "Machamp", "Metagross", "Snorlax",
                "Togetic", "Tyranitar"
        ));

        selectedPokemons = new ArrayList<>();
        playerTeam = new ArrayList<>();
        opponentTeam = new ArrayList<>();
        isPlayerSelecting = true;

        JLabel titleLabel = new JLabel("Selecciona tu equipo Pokémon (Jugador)", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(1, 2, 10, 10));

        availableList = new JList<>(new DefaultListModel<>());
        selectedList = new JList<>(new DefaultListModel<>());

        fillAvailableList();

        availableList.setBorder(BorderFactory.createTitledBorder("Pokémon Disponibles"));
        selectedList.setBorder(BorderFactory.createTitledBorder("Tu Equipo (Max 6)"));

        centerPanel.add(new JScrollPane(availableList));
        centerPanel.add(new JScrollPane(selectedList));

        add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton addButton = new JButton("Añadir →");
        JButton removeButton = new JButton("← Quitar");
        confirmButton = new JButton("Confirmar");

        addButton.addActionListener(e -> addPokemonToTeam());
        removeButton.addActionListener(e -> removePokemonFromTeam());
        confirmButton.addActionListener(e -> confirmSelection(titleLabel));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(confirmButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void fillAvailableList() {
        DefaultListModel<String> model = (DefaultListModel<String>) availableList.getModel();
        model.clear();
        availablePokemons.forEach(model::addElement);
    }

    private void addPokemonToTeam() {
        String selected = availableList.getSelectedValue();
        if (selected != null && selectedPokemons.size() < 6) {
            availablePokemons.remove(selected);
            selectedPokemons.add(selected);
            refreshLists();
        }
    }

    private void removePokemonFromTeam() {
        String selected = selectedList.getSelectedValue();
        if (selected != null) {
            selectedPokemons.remove(selected);
            availablePokemons.add(selected);
            refreshLists();
        }
    }

    private void refreshLists() {
        fillAvailableList();
        DefaultListModel<String> selectedModel = (DefaultListModel<String>) selectedList.getModel();
        selectedModel.clear();
        selectedPokemons.forEach(selectedModel::addElement);
    }

    private void confirmSelection(JLabel titleLabel) {
        if (selectedPokemons.size() <= 6) {
            if (isPlayerSelecting) {
                playerTeam = new ArrayList<>(selectedPokemons);
                fightsPanel.setPlayerTeam(playerTeam);

                fightsPanel.setPlayerPokemonImage("/resources/" + playerTeam.get(0).toLowerCase() + "Back.png");
                fightsPanel.setPlayerInfo(playerTeam.get(0), 100);

                isPlayerSelecting = false;
                selectedPokemons.clear();
                availablePokemons.addAll(playerTeam);
                refreshLists();
                titleLabel.setText("Selecciona tu equipo Pokémon (Oponente)");

            } else {
                opponentTeam = new ArrayList<>(selectedPokemons);
                fightsPanel.setOpponentTeam(opponentTeam);

                fightsPanel.setOpponentPokemonImage("/resources/" + opponentTeam.get(0).toLowerCase() + "Front.png");
                fightsPanel.setOpponentInfo(opponentTeam.get(0), 100);

                cardLayout.show(contentPanel, "BATALLA");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Debes seleccionar exactamente 6 Pokémon.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public List<String> getSelectedPokemons() {
        return new ArrayList<>(selectedPokemons);
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }
}