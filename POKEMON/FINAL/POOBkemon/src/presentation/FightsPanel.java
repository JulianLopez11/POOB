package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Panel para gestionar y mostrar la batalla de Pokémon.
 */
public class FightsPanel extends JPanel {
    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;
    private JButton fightButton;
    private JButton bagButton;
    private JButton pokemonButton;
    private JButton runButton;
    private JButton pauseButton;
    private ArrayList<JButton> buttons = new ArrayList<>();// Botón de pausa
    // Estado de pausa

    // Equipos de jugadores y oponentes
    private List<String> playerTeam;
    private List<String> opponentTeam;
    // Índices de Pokémon actuales
    private int currentPlayerPokemonIndex = 0;

    private int currentOpponentPokemonIndex = 0;
    // Bandera para permitir el cambio de Pokémon
    private boolean canChangePlayerPokemon = true;
    private boolean canChangeOpponentPokemon = true;

    // Salud e información de los Pokémon
    private int playerHealth = 100;
    private int opponentHealth = 100;
    private String playerName = "";
    private String opponentName = "";
    private int playerLevel = 100;
    private int opponentLevel = 100;

    // Constantes de posicionamiento
    private final double PLAYER_X_RATIO = 0.167;
    private final double PLAYER_Y_RATIO = 0.6;
    private final double PLAYER_SIZE_RATIO = 0.2;

    private final double OPPONENT_X_RATIO = 0.65;
    private final double OPPONENT_Y_RATIO = 0.15;
    private final double OPPONENT_SIZE_RATIO = 0.25;

    private final double TEXT_BOX_HEIGHT_RATIO = 0.083;
    private final double OPTIONS_BOX_WIDTH_RATIO = 0.5;

    /**
     * Constructor del panel de batalla.
     * Inicializa los componentes y configura el diseño.
     */
    public FightsPanel() {
        setLayout(null);

        try {
            background = new ImageIcon(getClass().getResource("/resources/batalla.png"));
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }

        fightButton = createOptionButton("FIGHT");
        bagButton = createOptionButton("BAG");
        pokemonButton = createOptionButton("POKÉMON");
        runButton = createOptionButton("RUN");
        pauseButton = createOptionButton("PAUSE");


        add(fightButton);
        add(bagButton);
        add(pokemonButton);
        add(runButton);
        add(pauseButton);
        buttons.add(fightButton);
        buttons.add(bagButton);
        buttons.add(pokemonButton);
        buttons.add(runButton);
        buttons.add(pauseButton);
        setButtons();
    }

    /**
     * Configura el color y el cursor de los botones.
     */
    private void setButtons() {
        for (JButton button : buttons) {
            button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
    }

    /**
     * Crea un botón de opción con un texto específico.
     *
     * @param text El texto que se mostrará en el botón.
     * @return El botón creado.
     */
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

    /**
     * Configura el tamaño y la posición de los botones de opciones.
     */
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

        // Dibujar barras de salud y nombres
        drawHealthBarWithInfo(g, playerX, playerY - 20, playerPokemonSize, playerHealth, playerName, playerLevel, Color.GREEN);
        drawHealthBarWithInfo(g, opponentX, opponentY - 20, opponentPokemonSize, opponentHealth, opponentName, opponentLevel, Color.RED);

        int rawTextBoxHeight = (int) (panelHeight * TEXT_BOX_HEIGHT_RATIO);
        int textBoxHeight = Math.max(rawTextBoxHeight, 60); // altura mínima de la barra
        int textBoxY = panelHeight - textBoxHeight;

        // Fondo de la barra de texto
        g.setColor(new Color(0, 128, 128));
        g.fillRoundRect(0, textBoxY, panelWidth, textBoxHeight, 15, 15); // Bordes redondeados

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, Math.max(12, panelWidth / 80)));
        g.drawString("What will " + playerName + " do?", 10, textBoxY + (textBoxHeight / 2) + 5);

        int optionsBoxWidth = (int) (panelWidth * OPTIONS_BOX_WIDTH_RATIO);
        int optionsBoxHeight = textBoxHeight;
        int optionsBoxX = panelWidth - optionsBoxWidth;
        int optionsBoxY = textBoxY;

        // Caja de opciones con borde redondeado
        g.setColor(new Color(50, 50, 50));
        g.fillRoundRect(optionsBoxX, optionsBoxY, optionsBoxWidth, optionsBoxHeight, 15, 15);

        repositionButtons();
    }

    /**
     * Dibuja una barra de salud con información adicional.
     *
     * @param g      El objeto Graphics para dibujar.
     * @param x      La posición x de la barra.
     * @param y      La posición y de la barra.
     * @param width  El ancho de la barra.
     * @param health La salud actual del Pokémon.
     * @param name   El nombre del Pokémon.
     * @param level  El nivel del Pokémon.
     * @param color  El color de la barra de salud.
     */
    private void drawHealthBarWithInfo(Graphics g, int x, int y, int width, int health, String name, int level, Color color) {
        int barWidth = (int) (width * 0.8); // Ancho de la barra de salud
        int barHeight = 10; // Altura de la barra de salud
        int filledWidth = (int) (barWidth * (health / 100.0));

        // Dibujar nombre y nivel
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(name + " Lv" + level, x, y - 10);

        // Fondo de la barra
        g.setColor(Color.GRAY);
        g.fillRect(x, y, barWidth, barHeight);

        // Salud
        g.setColor(color);
        g.fillRect(x, y, filledWidth, barHeight);

        // Borde de la barra
        g.setColor(Color.BLACK);
        g.drawRect(x, y, barWidth, barHeight);
    }

    /**
     * Reposiciona los botones de opciones en la interfaz.
     */
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

        fightButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        bagButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonMarginY, buttonWidth, buttonHeight);
        pokemonButton.setBounds(optionsBoxX + buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);
        runButton.setBounds(optionsBoxX + buttonWidth + 2 * buttonMarginX, optionsBoxY + buttonHeight + 2 * buttonMarginY, buttonWidth, buttonHeight);

        // Posiciona el botón de pausa en la esquina superior izquierda
        int pauseButtonWidth = 100; // Ancho del botón de pausa
        int pauseButtonHeight = 40; // Altura del botón de pausa
        int pauseButtonX = 10; // Margen desde el borde izquierdo
        int pauseButtonY = 10; // Margen desde el borde superior

        pauseButton.setBounds(pauseButtonX, pauseButtonY, pauseButtonWidth, pauseButtonHeight);
    }

    /**
     * Obtiene el botón de "PELEA".
     *
     * @return El botón de pelea.
     */
    public JButton getFightButton() {
        return fightButton;
    }

    /**
     * Obtiene el botón de "BOLSA".
     *
     * @return El botón de bolsa.
     */
    public JButton getBagButton() {
        return bagButton;
    }

    /**
     * Obtiene el botón de "POKÉMON".
     *
     * @return El botón de Pokémon.
     */
    public JButton getPokemonButton() {
        return pokemonButton;
    }

    /**
     * Obtiene el botón de "CORRER".
     *
     * @return El botón de correr.
     */
    public JButton getRunButton() {
        return runButton;
    }

    /**
     * Configura los Pokémon seleccionados del jugador en la batalla.
     *
     * @param playerTeam Lista de nombres de los Pokémon seleccionados por el jugador.
     */
    public void setPlayerTeam(List<String> playerTeam) {
        this.playerTeam = playerTeam;
        currentPlayerPokemonIndex = 0; // El primer Pokémon será el inicial
        setPlayerPokemonImage("/resources/" + playerTeam.get(0).toLowerCase() + "Back.png");
        setPlayerInfo(playerTeam.get(0), 100); // Información inicial
    }

    /**
     * Configura los Pokémon seleccionados del oponente en la batalla.
     *
     * @param opponentTeam Lista de nombres de los Pokémon seleccionados por el oponente.
     */
    public void setOpponentTeam(List<String> opponentTeam) {
        this.opponentTeam = opponentTeam;
        currentOpponentPokemonIndex = 0; // El primer Pokémon será el inicial
        setOpponentPokemonImage("/resources/" + opponentTeam.get(0).toLowerCase() + "Front.png");
        setOpponentInfo(opponentTeam.get(0), 100); // Información inicial
    }

    /**
     * Cambia el Pokémon del jugador.
     */
    public void changePlayerPokemon() {
        if (playerTeam == null || playerTeam.isEmpty() || !canChangePlayerPokemon) {
            JOptionPane.showMessageDialog(this, "No puedes cambiar el Pokémon del jugador en este momento.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String selectedPokemon = (String) JOptionPane.showInputDialog(
                this,
                "Elige tu próximo Pokémon:",
                "Cambio de Pokémon (Jugador)",
                JOptionPane.PLAIN_MESSAGE,
                null,
                playerTeam.toArray(),
                playerTeam.get(currentPlayerPokemonIndex)
        );

        if (selectedPokemon != null) {
            currentPlayerPokemonIndex = playerTeam.indexOf(selectedPokemon);
            setPlayerPokemonImage("/resources/" + selectedPokemon.toLowerCase() + "Back.png");
            setPlayerInfo(selectedPokemon, 100); // Actualiza la información del Pokémon
        }
    }

    /**
     * Cambia el Pokémon del oponente.
     */
    public void changeOpponentPokemon() {
        if (opponentTeam == null || opponentTeam.isEmpty() || !canChangeOpponentPokemon) {
            JOptionPane.showMessageDialog(this, "No puedes cambiar el Pokémon del oponente en este momento.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String selectedPokemon = (String) JOptionPane.showInputDialog(
                this,
                "Elige el próximo Pokémon:",
                "Cambio de Pokémon (Oponente)",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opponentTeam.toArray(),
                opponentTeam.get(currentOpponentPokemonIndex)
        );

        if (selectedPokemon != null) {
            currentOpponentPokemonIndex = opponentTeam.indexOf(selectedPokemon);
            setOpponentPokemonImage("/resources/" + selectedPokemon.toLowerCase() + "Front.png");
            setOpponentInfo(selectedPokemon, 100); // Actualiza la información del Pokémon
        }
    }

    /**
     * Establece la información del Pokémon del jugador.
     *
     * @param name  El nombre del Pokémon.
     * @param level El nivel del Pokémon.
     */
    public void setPlayerInfo(String name, int level) {
        this.playerName = name;
        this.playerLevel = level;
        repaint();
    }

    public JButton getPauseButton(){
        return pauseButton;
    }

    /**
     * Establece la información del Pokémon del oponente.
     *
     * @param name  El nombre del Pokémon.
     * @param level El nivel del Pokémon.
     */
    public void setOpponentInfo(String name, int level) {
        this.opponentName = name;
        this.opponentLevel = level;
        repaint();
    }

    /**
     * Establece la imagen del Pokémon del jugador.
     *
     * @param imagePath La ruta de la imagen del Pokémon.
     */
    public void setPlayerPokemonImage(String imagePath) {
        this.playerPokemon = new ImageIcon(getClass().getResource(imagePath));
        repaint();
    }

    /**
     * Establece la imagen del Pokémon del oponente.
     *
     * @param imagePath La ruta de la imagen del Pokémon.
     */
    public void setOpponentPokemonImage(String imagePath) {
        this.opponentPokemon = new ImageIcon(getClass().getResource(imagePath));
        repaint();
    }
}