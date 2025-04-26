package presentation;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class Maxwell extends JPanel {

    private JPanel Panel;
    public static Color color1 = Color.RED;
    public static Color color2 = Color.BLUE;
    private static final Color HOLE = Color.GRAY;

    private int height;
    private int width;

    
    private  int[] holes;
    private  int[] redParticles;
    private  int[] blueParticles;
    private  int[] midWall;

    public Maxwell(int newH, int newW,int [][] data ){
        this(newH,newW);
        setData(data);
        paintComponents();
    }

    public Maxwell(int newH, int newW){
        height = newH;
        width = newW;
        setLayout(new GridLayout(1,1));
        setSize(getWidth(), getHeight());
        setBorder(BorderFactory.createLineBorder(Color.BLACK,4));
        prepareElements();
    }

    public Maxwell(int[][] data){
        this(11,20);
        setData(data);
        paintComponents();
    }

    private void setData(int[][] data){
        blueParticles = data[0];
        redParticles = data[1];
        holes = data[2];
        midWall = data[3];
    }

    private void prepareElements(){
        Panel = new JPanel(new GridLayout(height, (2*width)+1));
        for (int i = 0; i < height * ((2*width)+1); i++) {
            JPanel celda = new JPanel();
            Panel.add(celda);
        }
        Panel.setBorder(getBorder());
        add(Panel);
    }

    public void paintComponents(){
        paintMidWall();
        for (int num:blueParticles){
            Panel.getComponent(num).setBackground(color2);
        }
        for(int num:redParticles){
            Panel.getComponent(num).setBackground(color1);
        }
        for(int num: holes){
            Panel.getComponent(num).setBackground(HOLE);
        }
        
    }

    public void rePaintComponents() {
        for (int i = 0; i < Panel.getComponentCount(); i++) {
            Panel.getComponent(i).setBackground(Color.WHITE);
        }

        paintMidWall();
    
        for (int num : blueParticles) {
            Panel.getComponent(num).setBackground(color2);
        }

        for (int num : redParticles) {
            Panel.getComponent(num).setBackground(color1);
        }

        for (int num : holes) {
            Panel.getComponent(num).setBackground(HOLE);
        }
    }
    

    private void paintMidWall() {
        int totalCells = height * (2 * width + 1);
        int centerIndex = (height / 2) * (2 * width + 1) + width;
        for (int i : midWall) {
            if (i >= 0 && i < totalCells) {
                if (i == centerIndex) {
                    Panel.getComponent(i).setBackground(Color.GRAY); 
                } else {
                    Panel.getComponent(i).setBackground(Color.BLACK); 
                }
            }
        }
    }
    
    private void setBackground() {
        Component[] componentesDer = Panel.getComponents();
        for (int i = 0; i < componentesDer.length; i++) {
            componentesDer[i].setBackground(Color.WHITE);
        }
    }
    public void refresh(int[][] info){
        setBackground();
        setData(info);
        paintMidWall();
        paintComponents();
        Panel.revalidate();
        Panel.repaint();
        revalidate();
        repaint();

    }

    public void resetColors(){
        color1 = Color.RED;
        color2 = Color.BLUE;
    }
}
