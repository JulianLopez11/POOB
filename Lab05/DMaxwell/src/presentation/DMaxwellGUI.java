package presentation;
import domain.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;

public class DMaxwellGUI extends JFrame {
    private Maxwell board;
    private DMaxwell maxwell;
    private int height;
    private int width;
  
    private JButton upButton;
    private JButton downButton;
    private JButton rightButton;
    private JButton leftButton;
   
    private JMenuItem openItem;
    private JMenuItem saveItem;
    private JMenuItem exitItem;
    private JMenuItem newItem;
    private JMenuItem changeColorRedItem;
    private JMenuItem changeColorBluesItem;
    private JMenuItem resetItem;
    /*
     * Constructor
     */
    public DMaxwellGUI() {
        maxwell = new DMaxwell();
        prepareElements();
        prepareActions();
    }
    /*
     * Prepare All the general actions
     */
    private void prepareActions() {
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Boton Salir Ventana OYENTE
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                closeWindow();
            }
        });
        //Oyente Boton Subir
        upButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movement('u');
            }
        });
        //Oyente Boton Bajar
        downButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movement('d');
            }
        });
        //Oyente Boton Izquierda
        leftButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movement('l');
            }
        });
        //Oyente Boton Derecha
        rightButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                movement('r');
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e){
                if(e.getKeyChar() == 'w') movement('u');
                if (e.getKeyChar() == 's') movement('d');
                if (e.getKeyChar() == 'd') movement('r');
                if (e.getKeyChar() == 'a') movement('l');
            }
        });
        
        prepareActionMenu();

        
    }
    /*
     * Prepare the main elements
     */
    private void prepareElements() {
        setTitle("Maxwell Discreto");
        height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
        setSize(width * 1 / 4, height * 1 / 4);
        setLocationRelativeTo(null);
        prepareElementsBoard();
        prepareElementsMenu();
    }
    /*
     * Prepare The elements of the board
     */
    private void prepareElementsBoard(){
        setLayout(new GridLayout(3,1));
        board = new Maxwell(maxwell.container());
        add(board);
        JPanel textPanel = new JPanel();
        textPanel.setBackground(Color.BLACK);
        JLabel label = new JLabel("Particulas 🔴"+ maxwell.container()[0][1] + " -- " +
	                               "Particulas 🔵: " + maxwell.container()[0][0] + " -- " +
	                               "Huecos:" + maxwell.container()[0][2] + " -- " +
	                               "Particulas en huecos:" + maxwell.container()[0][3] + " -- " );
	    label.setFont(new Font("Arial", Font.BOLD, 16));
	    label.setForeground(Color.WHITE);
        textPanel.add(label);
        add(textPanel);


        JPanel south = new JPanel(new FlowLayout(FlowLayout.CENTER));

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
    /*
     * Prepare the elements of the menu
     */
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
    /**
     * New Data of new Container
     */
    private void newDataMaxwell(){
        JPanel dataField = new JPanel();
        JLabel height = new JLabel("Height: ");
        JLabel width = new JLabel("Width: ");
        JLabel reds = new JLabel("Reds: ");
        JLabel blues = new JLabel("Blues: ");
        JLabel holes = new JLabel("Holes: ");
        JTextField heightField = new JTextField();
        JTextField widthField = new JTextField();
        JTextField redField = new JTextField();
        JTextField blueField = new JTextField();
        JTextField holesField = new JTextField();
        dataField.setLayout(new GridLayout(5,2));
        dataField.add(height);
        dataField.add(heightField);
        dataField.add(width);
        dataField.add(widthField);
        dataField.add(reds);
        dataField.add(redField);
        dataField.add(blues);
        dataField.add(blueField);
        dataField.add(holes);
        dataField.add(holesField);
        int option = JOptionPane.showConfirmDialog(null,dataField,
            "Data Field",JOptionPane.OK_CANCEL_OPTION);
        if(option == 0){}
        try{
            int h = Integer.parseInt(heightField.getText());
            int w = Integer.parseInt(widthField.getText());
            int r = Integer.parseInt(redField.getText());
            int b = Integer.parseInt(blueField.getText());
            int o = Integer.parseInt(holesField.getText());
            maxwell = new DMaxwell(h,2*w,r,b,o);
            remove(board);
            board = new Maxwell(h,w,maxwell.container());
            add(board,0);
            refresh();
            SwingUtilities.updateComponentTreeUI(this);
        
        }catch(DMaxwellExceptions e){
            if (e.getMessage().equals(DMaxwellExceptions.ONLY_POSITIVE_DIMENTIONS)) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error De Tipo", JOptionPane.ERROR_MESSAGE);
            }
        }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(this,"Tipos Invalidos", "Error De Tipo", JOptionPane.ERROR_MESSAGE);
        }
    }
    /*
     * Prepare the actions of the menu
     */
    private void prepareActionMenu(){

        //Nuevo Oyente
        newItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newDataMaxwell(); 
            }
        });

        //Abrir Oyente
        openItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openItems();
            }
        });

        //Salvar Oyente
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveItems();
            }
        });

        //Salir Oyente
        exitItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });
        //Cambiar Color Rojas Oyente
        changeColorRedItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeColorReds();
            }
        });
        //Cambiar Color Azules Oyente
        changeColorBluesItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                changeColorBlues();
            }
        });
        //Reiniciar Oyente BONO
        resetItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resetToDefaultBoard();

            }
        });
    }
    /*
     *Method to close a window 
     */
    private void closeWindow(){
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", JOptionPane.QUESTION_MESSAGE, 
        JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0);  
        }
    }

    private void openItems(){
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showOpenDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Abrir archivo",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /*
     * Methos to do the showSaveDiaglog
     */
    private void saveItems(){
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(DMaxwellGUI.this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            JOptionPane.showMessageDialog(DMaxwellGUI.this,"Funcionando" + " " + selectedFile.getName(),"Salvar archivo",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }
    /*
     *Method to do the exit of a panel
     */
    private void exit(){
        JOptionPane optionPane = new JOptionPane("¿Estás seguro de que quieres salir?", 
        JOptionPane.QUESTION_MESSAGE, JOptionPane.YES_NO_OPTION);
        JDialog dialog = optionPane.createDialog(DMaxwellGUI.this, "Confirmar Salida");
        dialog.setVisible(true);
        if (optionPane.getValue().equals(JOptionPane.YES_OPTION)) {
            System.exit(0); 
        }
    }
    /*
     *Method to change the color of the red particles 
     */
    private void changeColorReds(){
        Color updateColor = JColorChooser.showDialog(DMaxwellGUI.this, "Selecciona Color Particulas Rojas", Maxwell.color1);
        if (updateColor != null) {
            Maxwell.color1 = updateColor;
            board.rePaintComponents();
            refresh();
        }
    }
    /*
     *Method to change the color of the blue particles 
     */
    private void changeColorBlues(){
        Color updateColor = JColorChooser.showDialog(DMaxwellGUI.this, "Selecciona Color Particulas Azules", Maxwell.color2);
        if (updateColor != null) {
            Maxwell.color2 = updateColor;
            board.rePaintComponents(); 
            refresh();
        }
    }
    /**
     * Method to reset the board to the default
     */
    private void resetToDefaultBoard(){
        maxwell = new DMaxwell();
        remove(board);
        board.resetColors();
        board = new Maxwell(maxwell.container());
        add(board,0);
        revalidate();
        refresh();
    }
    /**
     * Refresh the method
     */
    private void refresh(){
        board.refresh(maxwell.container());
        repaint();
    }

    /*
     *Movement Of the particles 
     * @param direction
     */
    private void movement(char direction) {
        maxwell.move(direction); 
        refresh();
    }
    
    


    public static void main(String[] args) {
        DMaxwellGUI gui = new DMaxwellGUI();
        gui.pack();
        gui.setVisible(true);
    }
}
