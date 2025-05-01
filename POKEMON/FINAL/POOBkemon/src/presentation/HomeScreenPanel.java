package src.presentation;

import javax.swing.*;
import java.awt.*;

public class HomeScreenPanel extends JPanel {
    private JLabel titulo;
    private JButton playButton;
    private JButton exitButton;
    private Image backgroundImage;

    public HomeScreenPanel() {
        prepareElements();
    }

    private void prepareElements() {
        setLayout(new BorderLayout());
        playButton = new JButton("Jugar");
        exitButton = new JButton("Salir");
        playButton.setBackground(Color.WHITE);
        exitButton.setBackground(Color.WHITE);
        playButton.setBackground(new Color(255, 255, 255, 200));
        exitButton.setBackground(new Color(255, 255, 255, 200));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,10,10));
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);
        buttonPanel.setOpaque(false);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ "pokemonInicio"+".PNG"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
