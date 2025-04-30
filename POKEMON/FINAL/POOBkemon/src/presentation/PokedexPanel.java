package src.presentation;

import javax.swing.*;
import java.awt.*;

public class PokedexPanel extends JPanel {
    private JButton backButton;
    //private JLabel titulo;

    public PokedexPanel() {
        prepareElements();
    }

    private void prepareElements() {
        setLayout(new BorderLayout());
        setOpaque(false);

//        titulo = new JLabel("¡Pokemones!");
//        titulo.setHorizontalAlignment(SwingConstants.CENTER);
//        titulo.setFont(new Font("Arial", Font.BOLD, 24));
//        add(titulo, BorderLayout.NORTH);

        backButton = new JButton("Volver");
        backButton.setBackground(new Color(255, 255, 255,200));
        add(backButton, BorderLayout.SOUTH);
    }

    public JButton getBackButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ "pokedex2"+".GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
