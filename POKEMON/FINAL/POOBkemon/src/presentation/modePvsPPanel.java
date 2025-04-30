package src.presentation;

import javax.swing.*;
import java.awt.*;

public class modePvsPPanel extends JPanel {
    private JButton normalMode;
    private JButton survivalMode;
    private JButton backButton;
    private JLabel titulo;

    public modePvsPPanel() {
        prepareElements();
    }

    private void prepareElements() {
        setLayout(new BorderLayout());

        titulo = new JLabel("¡Selecciona el Modo!");
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 50));
        titulo.setForeground(Color.WHITE);
        titulo.setOpaque(false);
        add(titulo, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(3,1));
        buttonPanel.setOpaque(false);
        normalMode = new JButton("Normal");
        survivalMode = new JButton("Survival");
        backButton = new JButton("Volver");
        normalMode.setBackground(new Color(255, 255, 255,200));
        survivalMode.setBackground(new Color(255, 255, 255,200));
        backButton.setBackground(new Color(255, 255, 255,200));

        buttonPanel.add(normalMode);
        buttonPanel.add(survivalMode);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);


    }

    public JButton getNormalMode() {
        return normalMode;
    }

    public JButton getSurvivalMode() {
        return survivalMode;
    }

    public  JButton getBackButton() {
        return backButton;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon back = new ImageIcon(getClass().getResource("/resources/"+ "modePvsP"+".GIF"));
        g.drawImage(back.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}