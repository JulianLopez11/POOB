package src.presentation;

import javax.swing.*;
import java.awt.*;

public class Fights extends JPanel {
    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;

    public Fights() {
        try {
            background = new ImageIcon(getClass().getResource("/resources/batalla.png"));
            playerPokemon = new ImageIcon(getClass().getResource("/resources/raichuBack.png"));
            opponentPokemon = new ImageIcon(getClass().getResource("/resources/charizardFront.png"));
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dimensiones del panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Dibujar fondo
        g.drawImage(background.getImage(), 0, 0, panelWidth, panelHeight, this);

        // Tamaños Pokémon
        int playerPokemonSize = panelWidth / 5;
        int opponentPokemonSize = panelWidth / 4;

        // Coordenadas del Pokémon del jugador
        int playerX = panelWidth / 6;
        int playerY = panelHeight - (panelHeight / 3);
        // Coordenadas del Pokémon oponente
        int opponentX = panelWidth - (panelWidth / 3);
        int opponentY = panelHeight / 6; //

        // Dibujar Pokémon del jugador
        if (playerPokemon != null) {
            g.drawImage(playerPokemon.getImage(), playerX, playerY, playerPokemonSize, playerPokemonSize, this);
        }

        // Dibujar Pokémon oponente
        if (opponentPokemon != null) {
            g.drawImage(opponentPokemon.getImage(), opponentX, opponentY, opponentPokemonSize, opponentPokemonSize, this);
        }
    }
}