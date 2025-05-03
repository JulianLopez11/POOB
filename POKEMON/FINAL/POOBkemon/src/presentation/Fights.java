package src.presentation;

import javax.swing.*;
import java.awt.*;

public class Fights extends JPanel {
    private ImageIcon background;
    private ImageIcon playerPokemon;
    private ImageIcon opponentPokemon;

    private int playerHp = 100; // HP del jugador
    private int opponentHp = 100; // HP del oponente

    public Fights() {
        // Cargar imágenes
        try {
            background = new ImageIcon(getClass().getResource("/resources/batalla.PNG"));
            playerPokemon = new ImageIcon(getClass().getResource("/resources/golduckBack.png")); // Pokémon del jugador
            opponentPokemon = new ImageIcon(getClass().getResource("/resources/gyarados.png")); // Pokémon del oponente
        } catch (NullPointerException e) {
            System.err.println("Error: No se pudieron cargar las imágenes. Verifica las rutas.");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Obtener dimensiones del panel
        int panelWidth = getWidth();
        int panelHeight = getHeight();

        // Dibujar fondo
        if (background != null) {
            g.drawImage(background.getImage(), 0, 0, panelWidth, panelHeight, this);
        }

        // Dimensiones y posiciones de los círculos en el fondo
        int circleWidth = panelWidth / 4; // Ancho del círculo
        int circleHeight = panelHeight / 12; // Alto del círculo

        // Posición del círculo del jugador (parte inferior izquierda)
        int playerCircleX = panelWidth / 6;
        int playerCircleY = panelHeight - (circleHeight * 3);

        // Posición del círculo del oponente (parte superior derecha)
        int opponentCircleX = panelWidth - (circleWidth + panelWidth / 6);
        int opponentCircleY = panelHeight / 6;

        // Calcular el tamaño de los Pokémon (basado en el tamaño del círculo)
        int pokemonSize = Math.min(circleWidth, circleHeight) * 2;

        // Posición del sprite del jugador (centrado en el círculo)
        int playerX = playerCircleX + (circleWidth / 2) - (pokemonSize / 2);
        int playerY = playerCircleY - (pokemonSize / 2);

        // Posición del sprite del oponente (centrado en el círculo)
        int opponentX = opponentCircleX + (circleWidth / 2) - (pokemonSize / 2);
        int opponentY = opponentCircleY - (pokemonSize / 2);

        // Dibujar Pokémon del jugador
        if (playerPokemon != null) {
            g.drawImage(playerPokemon.getImage(), playerX, playerY, pokemonSize, pokemonSize, this);
        }

        // Dibujar Pokémon oponente
        if (opponentPokemon != null) {
            g.drawImage(opponentPokemon.getImage(), opponentX, opponentY, pokemonSize, pokemonSize, this);
        }

        // Dibujar interfaz de batalla (opcional: barras de vida, nombres, etc.)
        drawBattleInterface(g, playerX, playerY, opponentX, opponentY, pokemonSize, panelWidth, panelHeight);
    }

    private void drawBattleInterface(Graphics g, int playerX, int playerY, int opponentX, int opponentY, int pokemonSize, int panelWidth, int panelHeight) {
        // Aquí puedes agregar barras de vida, nombres, etc., si es necesario
    }
}