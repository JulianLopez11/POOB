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
    private int[] perras;

    public Maxwell(int newH, int newW,int [][] info ){
        this(newH,newW);
        setVariables(info);
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

    public Maxwell(int[][] info){
        this(11,20);
        setVariables(info);
        paintComponents();
    }

    private void setVariables(int[][] info){
        blueParticles = info[0];
        redParticles = info[1];
        holes = info[2];
        midWall = info[3];
    }

    private void prepareElements(){
        Panel = new JPanel(new GridLayout(height, (2*width)+1));
        for (int i = 0; i < height * ((2*width)+1); i++) {
            JPanel celd = new JPanel();
            Panel.add(celd);
        }
        Panel.setBorder(getBorder());
        add(Panel);
    }

    public void paintComponents(){
        paintCenter();
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

    public void rePainComponents(){

        for (int num:blueParticles){
            Panel.getComponent(num).setBackground(color2);
        }
        for(int num:redParticles){
            Panel.getComponent(num).setBackground(color1);
        }

    }

    private void paintCenter() {
    int centerIndex = (height / 2) * (2 * width + 1) + width;
    for (int i : midWall) {
        if (i == centerIndex) {
            Panel.getComponent(i).setBackground(Color.GRAY); 
        } else {
            Panel.getComponent(i).setBackground(Color.BLACK); 
        }
    }
    }
    
}
