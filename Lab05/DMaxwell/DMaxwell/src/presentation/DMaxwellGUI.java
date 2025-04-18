import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DMaxwellGUI extends JFrame {

    public DMaxwellGUI() {
        prepareActions();
        prepareElements();
    }

    public void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        //Boton Salir Ventana OYENTE
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0);  
                }
            }
        });
    }

    public void prepareElements() {
        setTitle("Maxwell Discreto");
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width * 1 / 4, height * 1 / 4);
        setLocationRelativeTo(null);
        prepareMenuElements();
        prepareMainPanel();
    }
    public void prepareMenuElements() {
        JMenuBar classicMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        JMenuItem newItem = new JMenuItem("Nuevo");
        JMenuItem openItem = new JMenuItem("Abrir");
        JMenuItem saveItem = new JMenuItem("Salvar");
        JMenuItem exitItem = new JMenuItem("Salir");
        fileMenu.add(newItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        classicMenu.add(fileMenu);
        setJMenuBar(classicMenu);
        //Abrir Oyente
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showOpenDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Abrir archivo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Salvar Oyente
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                if (fileChooser.showSaveDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Salvar archivo",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Salir Oyente
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0); 
                }
            }
        });
    }
    public void prepareMenuActions(){
        /*exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0); 
                }
            }
        }); */
    }
    public void prepareMainPanel() {
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    
        JLabel title = new JLabel("Maxwell's Demon");
        title.setFont(new Font("Serif", Font.BOLD, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Botones
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); 

        //Jugar
        JButton playButton = new JButton("Jugar");
        //Oyente Boton Jugar
        playButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame gameWindow = new JFrame("Juego - Maxwell's Demon");
                gameWindow.setSize(DMaxwellGUI.this.getSize());  
                gameWindow.setLocationRelativeTo(DMaxwellGUI.this);  
                gameWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  
                gameWindow.setVisible(true);  
            }
        });

        //Salir
        JButton exitButton = new JButton("Salir");
        //Oyente Boton Salir
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, 
                JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0);
                }
            }
        });
        buttonPanel.add(playButton);
        buttonPanel.add(exitButton);
        mainPanel.add(title);
        mainPanel.add(Box.createVerticalStrut(40));
        mainPanel.add(buttonPanel);
        add(mainPanel);
    }
    
    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.setVisible(true);
    }
}
