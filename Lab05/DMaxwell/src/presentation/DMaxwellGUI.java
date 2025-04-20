package presentation;
import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;


public class DMaxwellGUI extends JFrame {
    private JButton up;
    private JButton down;
    private JButton right;
    private JButton left;
    private JButton start;
    private DMaxwell maxwell;
    private Particle[][] board;
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
        //Oyente Boton Subir
        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveNorth();
            }
        });
        //Oyente Boton Bajar
        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveSouth();
            }
        });
        //Oyente Boton Izquierda
        left.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveWest();
            }
        });
        //Oyente Boton Derecha
        right.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                maxwell.moveEast();
            }
        });
        //Oyente Boton Iniciar
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //maxwell.container();
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

        prepareElementsBoard();
        prepareElementsMenu();
    }

    private void prepareElementsBoard(){
        int rows = 10; 
        int cols = 10; 
        board = new Particle[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((i + j) % 2 == 0) {
                    board[i][j] = new Particle(i, j, Color.RED); 
                } else {
                    board[i][j] = new Particle(i, j, Color.BLUE); 
                }
            }
        }

    JPanel boardPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawBoard(g);
        }
    };

    boardPanel.setPreferredSize(new Dimension(500,500)); 
    add(boardPanel, BorderLayout.CENTER); 
    }

    private void drawBoard(Graphics g) {
        int squareSize = 12; 
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Particle p = board[i][j];
                g.setColor(p.getColor());
                g.fillRect(p.getX() * squareSize, p.getY() * squareSize, squareSize, squareSize);
            }
        }
    }   
    
    private void prepareElementsMenu() {
        JMenuBar classicMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("Archivo");
        openItem = new JMenuItem("Abrir");
        saveItem = new JMenuItem("Salvar");
        exitItem = new JMenuItem("Salir");
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
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
                JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", 
                JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
                JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
                dialog.setVisible(true);
                if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
                    System.exit(0); 
                }
            }
        });
    }

    private void refresh(){
        repaint();
    }

    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.setVisible(true);
    }
}
