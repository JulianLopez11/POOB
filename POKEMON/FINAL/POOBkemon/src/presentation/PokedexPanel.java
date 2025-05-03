package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;
import java.util.List;

public class PokedexPanel extends JPanel {
    private JButton backButton;
    private JButton leftButton;
    private JButton rightButton;
    private JLabel pokemonImageLabel;
    private List<String> pokemonImages;
    private int currentIndex;

    public PokedexPanel(List<String> pokemonImages) {
        this.pokemonImages = pokemonImages;
        this.currentIndex = 0;
        prepareElements();

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                updatePokemonImage();
            }

            @Override
            public void componentResized(ComponentEvent e) {
                updatePokemonImage();
            }
        });
    }

    private void prepareElements() {
        setLayout(new BorderLayout());
        setOpaque(false);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 3));
        bottomPanel.setOpaque(false);

        backButton = new JButton("Volver");
        bottomPanel.add(backButton);

        leftButton = new JButton("←");
        leftButton.addActionListener(e -> showPreviousPokemon());
        bottomPanel.add(leftButton);

        rightButton = new JButton("→");
        rightButton.addActionListener(e -> showNextPokemon());
        bottomPanel.add(rightButton);

        add(bottomPanel, BorderLayout.SOUTH);

        // Panel central para imagen
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 40, 30));
        centerPanel.setOpaque(false);

        pokemonImageLabel = new JLabel();
        centerPanel.add(pokemonImageLabel);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void updatePokemonImage() {
        if (pokemonImages.isEmpty()) {
            pokemonImageLabel.setIcon(null);
            return;
        }

        String imagePath = pokemonImages.get(currentIndex);
        URL imageURL = getClass().getResource(imagePath);
        if (imageURL == null) {
            System.err.println("Imagen no encontrada: " + imagePath);
            pokemonImageLabel.setIcon(null);
            return;
        }

        ImageIcon pokemonImage = new ImageIcon(imageURL);
        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int width = panelWidth > 0 ? panelWidth / 2 : 200;
        int height = panelHeight > 0 ? panelHeight / 2 : 200;

        Image scaledImage = pokemonImage.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        pokemonImageLabel.setIcon(new ImageIcon(scaledImage));
    }

    private void showPreviousPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex - 1 + pokemonImages.size()) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    private void showNextPokemon() {
        if (!pokemonImages.isEmpty()) {
            currentIndex = (currentIndex + 1) % pokemonImages.size();
            updatePokemonImage();
        }
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/pokedexFinal.GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
