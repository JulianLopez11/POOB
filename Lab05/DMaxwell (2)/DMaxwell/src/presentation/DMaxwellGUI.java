package presentation;
import domain.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class DMaxwellGUI extends JFrame {
    private Maxwell board;
    private DMaxwell maxwell;
    private int height;
    private int width;
    private JFrame attributesFrame;
    private JPanel buttonsPanelAtributtes;
    private JButton upButton;
    private JButton downButton;
    private JButton rightButton;
    private JButton leftButton;
    private JButton acceptButton;
    private JButton exitButton;
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem newItem;
    private JMenuItem changeColorRedItem;
    private JMenuItem changeColorBluesItem;
    private JMenuItem resetItem;

    public DMaxwellGUI() {
        maxwell = new DMaxwell();
        prepareElements();
        prepareActions();
    }

    private void prepareActions() {
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
        /*/Oyente Boton Subir
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
        */
        prepareActionMenu();

        
    }

    private void prepareElements() {
        setTitle("Maxwell Discreto");
        height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width * 1 / 4, height * 1 / 4);
        setLocationRelativeTo(null);

        prepareElementsBoard();
        prepareElementsMenu();
    }

    private void prepareElementsBoard(){
        setLayout(new GridLayout(2,1));
        board = new Maxwell(maxwell.container());
        add(board);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER,20,20));

        JPanel maxPanel = new JPanel(new BorderLayout());
        upButton = new JButton("↑");
        downButton = new JButton("↓");
        rightButton = new JButton("→");
        leftButton = new JButton("←");

        maxPanel.add(upButton,BorderLayout.NORTH);
        maxPanel.add(downButton,BorderLayout.SOUTH);
        maxPanel.add(leftButton,BorderLayout.WEST);
        maxPanel.add(rightButton,BorderLayout.EAST);
        south.add(maxPanel);
        
        add(south);
        
    }

    private void prepareElementsMenu() {
        JMenuBar classicMenu = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenu opciones = new JMenu("Options");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        newItem = new JMenuItem("New");

        changeColorRedItem = new JMenuItem("Change Color Red Particles");
        changeColorBluesItem = new JMenuItem("Change Color Blue Particles");
        resetItem = new JMenuItem("Reset");
        //Opciones
        opciones.add(changeColorRedItem);
        opciones.addSeparator();
        opciones.add(changeColorBluesItem);
        opciones.addSeparator();
        opciones.add(resetItem);
        //MenuAñadir
        fileMenu.add(newItem);
        fileMenu.addSeparator();
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);
        //Menu Clasico Añadir Menu
        classicMenu.add(fileMenu);
        classicMenu.add(opciones);
        setJMenuBar(classicMenu);
    }

    private void prepareActionMenu(){

        //Nuevo Oyente
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //Ventana
                attributesFrame = new JFrame("Ingresar Atributos Nuevo Tablero");
                attributesFrame.setSize(width * 1 / 4, height * 1 / 4);
                attributesFrame.setLocationRelativeTo(null);
                attributesFrame.setLayout(new BorderLayout());
                //Inputs Medidas
                JPanel inputs = new JPanel(new GridLayout(5, 2, 10, 10));
                inputs.add(new JLabel("Height:"));
                JTextField heightField = new JTextField();
                inputs.add(heightField);
                inputs.add(new JLabel("Width:"));
                JTextField widthField = new JTextField();
                inputs.add(widthField);
                inputs.add(new JLabel("Blues:"));
                JTextField blueField = new JTextField();
                inputs.add(blueField);
                inputs.add(new JLabel("Reds:"));
                JTextField redField = new JTextField();
                inputs.add(redField);
                inputs.add(new JLabel("Holes:"));
                JTextField holesField= new JTextField();
                inputs.add(holesField);
                attributesFrame.add(inputs,BorderLayout.CENTER);
                //Botones
                buttonsPanelAtributtes = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
                acceptButton = new JButton("Accept");
                acceptButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int newHeight = Integer.parseInt(heightField.getText());
                        int newWidth = Integer.parseInt(widthField.getText());
                        int newBlue = Integer.parseInt(blueField.getText());
                        int newRed = Integer.parseInt(redField.getText());
                        int newHole = Integer.parseInt(holesField.getText());
                
                        DMaxwell dMaxwell = new DMaxwell(newHeight, newWidth, newBlue, newRed, newHole);
                        remove(board); 
                        Maxwell boardX = new Maxwell(dMaxwell.container()); 
                        add(boardX, 0); 
                        attributesFrame.dispose();
                        revalidate(); 
                        repaint();
                    }
                });
            
                exitButton = new JButton("Exit");
                //Salir Boton
                exitButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        attributesFrame.dispose();
                    }
                });
                buttonsPanelAtributtes.add(acceptButton);
                buttonsPanelAtributtes.add(exitButton);  
                attributesFrame.add(buttonsPanelAtributtes, BorderLayout.SOUTH);
                attributesFrame.setVisible(true);
            }
        });

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
        //Cambiar Color Rojas Oyente
        changeColorRedItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color updateColor = JColorChooser.showDialog(DMaxwellGUI.this, "Selecciona Color Particulas Rojas", Maxwell.color1);
                if (updateColor != null) {
                    Maxwell.color1 = updateColor;
                    board.rePainComponents();
                    refresh();
                }
            }
        });
        //Cambiar Color Azules Oyente
        changeColorBluesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Color updateColor = JColorChooser.showDialog(DMaxwellGUI.this, "Selecciona Color Particulas Azules", Maxwell.color2);
                if (updateColor != null) {
                    Maxwell.color2 = updateColor;
                    board.rePainComponents(); 
                    refresh();
                }
            }
        });
        //Reiniciar Oyente BONO
        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DMaxwell newDMaxwell = new DMaxwell();
                remove(board);
                board = new Maxwell(newDMaxwell.container());
                add(board,0);
                revalidate();
                refresh();

            }
        });
    }

    private void refresh(){
        this.repaint();
    }
    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.pack();
        gui.setVisible(true);
    }
}
