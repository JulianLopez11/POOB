import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class DMaxwellGUI extends JFrame {

    private JMenuItem restartItem;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;

    public DMaxwellGUI() {
        prepareElements();
        prepareActions();
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
        prepareActionMenu();
    }

    public void prepareElements() {
        setTitle("Maxwell Discreto");
        int height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width * 1 / 4, height * 1 / 4);
        setLocationRelativeTo(null);
        
        prepareElementsBoard();
        prepareElementsMenu();
    }

    private void prepareElementsBoard(){
        setLayout(new BorderLayout());
        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new FlowLayout());
        JButton up = new JButton("UP");
        JButton down = new JButton("DOWN");
        JButton right = new JButton("LEFT");
        JButton left = new JButton("RIGHT");
        JButton start = new JButton("START");
        maxPanel.add(start);
        maxPanel.add(up);
        maxPanel.add(down);
        maxPanel.add(left);
        maxPanel.add(right);
        add(maxPanel,BorderLayout.SOUTH);    
    }

    private void prepareElementsMenu() {
        JMenuBar classicMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        restartItem = new JMenuItem("Reiniciar");
        openItem = new JMenuItem("Abrir");
        saveItem = new JMenuItem("Salvar");
        exitItem = new JMenuItem("Salir");
        fileMenu.add(restartItem);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        classicMenu.add(fileMenu);
        setJMenuBar(classicMenu);
    }

    private void prepareActionMenu(){
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

    private void refresh(){
        
}

    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.setVisible(true);
    }
}