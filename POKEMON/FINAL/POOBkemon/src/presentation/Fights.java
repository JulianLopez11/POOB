package src.presentation;

import javax.swing.*;
import java.awt.*;

public class Fights extends JPanel {
    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;
    private JButton fightButton;
    private JButton bagButton;
    private JButton pokemonButton;
    private JButton runButton;

    // Health and info variables
    private int playerHealth = 100;
    private int opponentHealth = 100;
    private String playerName = "Machamp";
    private String opponentName = "Snorlax";
    private int playerLevel = 100;
    private int opponentLevel = 100;

    // Constantes de posicionamiento relativas
    private final double PLAYER_X_RATIO = 0.167;
    private final double PLAYER_Y_RATIO = 0.6;
    private final double PLAYER_SIZE_RATIO = 0.2;

    private final double OPPONENT_X_RATIO = 0.65;
    private final double OPPONENT_Y_RATIO = 0.15;
    private final double OPPONENT_SIZE_RATIO = 0.25;

    private final double TEXT_BOX_HEIGHT_RATIO = 0.083;
    private final double OPTIONS_BOX_WIDTH_RATIO = 0.5;

    public Fights() {
        setLayout(null);

        try {
            background = new ImageIcon(getClass().getResource("/resources/batalla.png"));
            playerPokemon = new ImageIcon(getClass().getResource("/resources/machampBack.png"));
            opponentPokemon = new ImageIcon(getClass().getResource("/resources/snorlaxFront.png"));
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }

        fightButton = createOptionButton("FIGHT");
        bagButton = createOptionButton("BAG");
        pokemonButton = createOptionButton("POKÉMON");
        runButton = createOptionButton("RUN");

        add(fightButton);
        add(bagButton);
        add(pokemonButton);
        add(runButton);
    }

    private JButton createOptionButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(50, 50, 50));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));

        // Efecto al pasar el cursor
        button.setRolloverEnabled(true);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setBackground(new Color(70, 70, 70));

        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();

        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, panelWidth, panelHeight, this);
        }

        int playerPokemonSize = (int) (panelWidth * PLAYER_SIZE_RATIO);
        int playerX = (int) (panelWidth * PLAYER_X_RATIO);
        int playerY = (int) (panelHeight * PLAYER_Y_RATIO);

        int opponentPokemonSize = (int) (panelWidth * OPPONENT_SIZE_RATIO);
        int opponentX = (int) (panelWidth * OPPONENT_X_RATIO);
        int opponentY = (int) (panelHeight * OPPONENT_Y_RATIO);

        if (playerPokemon != null) {
            g.drawImage(playerPokemon.getImage(), playerX, playerY, playerPokemonSize, playerPokemonSize, this);
        }

        if (opponentPokemon != null) {
            g.drawImage(opponentPokemon.getImage(), opponentX, opponentY, opponentPokemonSize, opponentPokemonSize, this);
        }

        // Draw health bars and names
        drawHealthBarWithInfo(g, playerX, playerY - 20, playerPokemonSize, playerHealth, playerName, playerLevel, Color.GREEN);
        drawHealthBarWithInfo(g, opponentX, opponentY - 20, opponentPokemonSize, opponentHealth, opponentName, opponentLevel, Color.RED);

        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60); // altura mínima de la barra
        int textBoxY = panelHeight - textBoxHeight;

        // Fondo de la barra de texto
        g.setColor(new Color(0, 128, 128));
        g.fillRoundRect(0, textBoxY, panelWidth, textBoxHeight, 15, 15); // Bordes redondeados

        // Sombras
        g.setColor(new Color(0, 0, 0, 50)); // Sombra más suave
        g.fillRoundRect(5, textBoxY + 5, panelWidth - 10, textBoxHeight - 10, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, Math.max(12, panelWidth / 80)));
        g.drawString("What will The ..  do?", 10, textBoxY + (textBoxHeight / 2) + 5);

        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = textBoxY;

        // Caja de opciones con borde redondeado
        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(optionsBoxX, optionsBoxY, optionsBoxWidth, optionsBoxHeight, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, Math.max(12, panelWidth / 80)));
        g.drawString("Options", optionsBoxX + 10, optionsBoxY + 20);

        repositionButtons();
    }

    private void drawHealthBarWithInfo(Graphics g, int x, int y, int width, int health, String name, int level, Color color) {
        int barWidth = (int) (width * 0.8); // Width of the health bar
        int barHeight = 10; // Height of the health bar
        int filledWidth = (int) (barWidth * (health / 100.0));

        // Draw name and level
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(name + " Lv" + level, x, y - 10);

        // Draw background
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Draw health
        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);

        // Draw border
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

    private void repositionButtons() {
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60); // altura mínima

        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = panelHeight - textBoxHeight;

        int buttonWidth = optionsBoxWidth / 2 - 10;
        int buttonHeight = optionsBoxHeight / 2 - 5;
        int buttonMarginX = 5;
        int buttonMarginY = 5;

        int dynamicFontSize = Math.max(12, panelWidth / 100);
        Font dynamicFont = new Font("Arial", Font.BOLD, dynamicFontSize);

        fightButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        bagButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        pokemonButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);
        runButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);

        fightButton.setFont(dynamicFont);
        bagButton.setFont(dynamicFont);
        pokemonButton.setFont(dynamicFont);
        runButton.setFont(dynamicFont);
    }

    public JButton getFightButton() {
        return fightButton;
    }

    public JButton getBagButton() {
        return bagButton;
    }

    public JButton getPokemonButton() {
        return pokemonButton;
    }

    public JButton getRunButton() {
        return runButton;
    }

    // Methods to update health
    public void setPlayerHealth(int health) {
        this.playerHealth = Math.max(0, Math.min(health, 100));
        repaint();
    }

    public void setOpponentHealth(int health) {
        this.opponentHealth = Math.max(0, Math.min(health, 100));
        repaint();
    }

    // Methods to update names and levels
    public void setPlayerInfo(String name, int level) {
        this.playerName = name;
        this.playerLevel = level;
        repaint();
    }

    public void setOpponentInfo(String name, int level) {
        this.opponentName = name;
        this.opponentLevel = level;
        repaint();
    }

    // New methods to update Pokémon sprites
    public void setPlayerPokemonImage(String imagePath) {
        this.playerPokemon = new ImageIcon(getClass().getResource(imagePath));
        repaint();
    }

    public void setOpponentPokemonImage(String imagePath) {
        this.opponentPokemon = new ImageIcon(getClass().getResource(imagePath));
        repaint();
    }

    public ImageIcon getPlayerPokemon() {
        return playerPokemon;
    }
}