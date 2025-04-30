package src.presentation;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class PlayScreenPanel extends JPanel {
    private JLabel titulo;
    private JButton backButton;
    private JButton PVsPButton;
    private JButton PVsMButton;
    private JButton MVsMButton;
    private JButton pokedexButton;
    private ArrayList<JButton> buttons = new ArrayList<>();

    public PlayScreenPanel() {
        prepareElements();
    }

    private void prepareElements() {
        setLayout(new BorderLayout());

        titulo = new JLabel("¡Selecciona la Modalidad!");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        add(titulo, BorderLayout.NORTH);

        JPanel modePanel = new JPanel(new GridLayout(4, 1, 0, 15));
        modePanel.setOpaque(false);
        modePanel.setPreferredSize(new Dimension(250, 200)); // Tamaño fijo para centrado visual

        PVsPButton = new JButton("Jugador Vs Jugador");
        PVsMButton = new JButton("Jugador Vs Máquina");
        MVsMButton = new JButton("Máquina Vs Máquina");

        modePanel.add(PVsPButton);
        modePanel.add(PVsMButton);
        modePanel.add(MVsMButton);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.add(modePanel);
        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        bottomPanel.setOpaque(false);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 50, 20, 50));

        pokedexButton = new JButton("Pokedex");
        backButton = new JButton("Volver");
        bottomPanel.add(pokedexButton);
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);

        buttons.add(backButton);
        buttons.add(PVsPButton);
        buttons.add(PVsMButton);
        buttons.add(MVsMButton);
        buttons.add(pokedexButton);
        setButtonsColor();
    }

    private void setButtonsColor() {
        for (JButton button : buttons) {
            button.setBackground(new Color(255, 255, 255, 200));
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
        }
    }

    public JButton getBackButton(){
        return backButton;
    }
    public JButton getPokedexButton() {
        return pokedexButton;
    }

    public JButton getMVsMButton() {
        return MVsMButton;
    }
    public JButton getPVsPButton() {
        return PVsPButton;
    }
    public JButton getPVsMButton() {
        return PVsMButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ "inicio2"+".GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
