import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;
/**
 * Write a description of class Particles here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Particles
{
    // instance variables - replace the example below with your own
    private Circle particle;
    private ArrayList<Circle> particles = new ArrayList<>();
    private String color;
    private int xPosition;
    private int yPosition;
    private int vx;
    private int vy;
    Rectangle board;
    
    public Particles(String color, int xPosition, int yPosition,int vx, int vy){
        particle = new Circle(color, xPosition, yPosition);
        particle.makeVisible();
        this.vx = vx;
        this.vy = vy;
    }
    
    public int getY(){
        return this.yPosition;
    }
    
    public void setPositionParticle(int px, int py){
        particle.setPosition(px,py);
    }
    
    public void makeVisibleParticle(){
        particle.makeVisible();
    }
    public void makeInvisibleParticle(){
        particle.makeInvisible();
    }
    
    public String getColor(){
        return particle.getColor();
    }
    /**
     * 
     */
    public int getX(){
        return this.xPosition;
    }
}
