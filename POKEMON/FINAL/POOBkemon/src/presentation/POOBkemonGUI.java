package src.presentation;

import src.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class POOBkemonGUI extends JFrame{

    private POOBkemon pooBkemon;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private HomeScreenPanel homePanel;
    private PlayScreenPanel playPanel;
    private PokedexPanel pokedexPanel;
    private ModePvsPPanel modePvsPPanel;
    private Fights fightsPanel;

    public POOBkemonGUI(){
        pooBkemon = new POOBkemon("normal");
        prepareElements();
        prepareActions();
    }

    public void prepareElements() {
        setTitle("POOBkemon");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        add(contentPanel);

        homePanel = new HomeScreenPanel();
        contentPanel.add(homePanel, "INICIO");

        playPanel = new PlayScreenPanel();
        contentPanel.add(playPanel, "JUGAR");

        modePvsPPanel = new ModePvsPPanel();
        contentPanel.add(modePvsPPanel, "MODE PvsP");

        fightsPanel = new Fights();
        contentPanel.add(fightsPanel, "BATALLA");

        pokedexPanel = new PokedexPanel(java.util.List.of(
                "/resources/raichuFront.png",
                "/resources/charizardFront.png",
                "/resources/blastoiseFront.png",
                "/resources/venusaurFront.png"
        ));
        contentPanel.add(pokedexPanel, "POKEDEX");

    }

    public void prepareActions() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });

        homePanel.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        homePanel.getPlayButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });

        playPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "INICIO");
            }
        });

        playPanel.getPokedexButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "POKEDEX");
            }
        });

        playPanel.getPVsPButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "MODE PvsP");
            }
        });

        //Batalla
        playPanel.getMVsMButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {cardLayout.show(contentPanel, "BATALLA");}
        });

        modePvsPPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });

        //Batalla
        modePvsPPanel.getSurvivalMode().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {cardLayout.show(contentPanel, "BATALLA");}
        });

        modePvsPPanel.getNormalMode().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "POKEMON SELECTION");
            }
        });

        pokedexPanel.getBackButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(contentPanel, "JUGAR");
            }
        });



    }

    public void closeWindow(){
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(POOBkemonGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public void exit(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?",
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(POOBkemonGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        POOBkemonGUI pooBkemonGUI = new POOBkemonGUI();
        pooBkemonGUI.pack();
        pooBkemonGUI.setVisible(true);
    }
}