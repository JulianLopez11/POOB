import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Class Hole
 * 
 * @author (Julian Camilo Lopez Barrero && Juan Sebastian Puentes Julio) 
 * @version (a version number or a date)
 */
public class Hole
{
    private Rectangle hole;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private int maxParticles;
    private int eatenParticles;
    
    /**
     * Constructor for objects of class Hole
     */
    public Hole(int xPosition, int yPosition, int height, int width,int maxParticles) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.width = width;
        this.height = height;
        this.maxParticles = maxParticles;
        eatenParticles = 0;
        hole = new Rectangle();  
        hole.changeSize(height, width);  
        hole.setPosition(xPosition,yPosition);  
        hole.changeColor("black");  
    }
    
    /**
     * Make invisible the hole
     */
    public void makeInvisibleHole(){
        hole.makeInvisible();
    }
    
    /**
     * Make invisible the hole
     */
    public void makeVisibleHole(){
        hole.makeVisible();
    }
    
    /**
    * Get position in x
    */
    public int getX(){
        return this.xPosition;
    }
    
    /**
     * Get position in y
     */
    public int getY(){
        return this.yPosition;
    }
    
    /**
     * Get Hole width.
     */
    public int getWidth(){
        return this.width;
    }

    /**
     * Get Hole Height.
     */
    public int getHeight(){
        return this.height;
    }
    
    /**
     * Get Max Amount of particles that can be eaten.
     */
    public int getAmountParticles(){
        return maxParticles;
    }
    
    /**
     * Verifys if holes can eat more particles.
     */
    public boolean canEat() {
        return eatenParticles < maxParticles;
    }

    /**
     * Increases eatenParticles variable if hole can eat.
     */
    public void eatParticle() {
        if (canEat()) {
            eatenParticles++;
        }
    }
    
    public int getEatenParticles(){
        return eatenParticles;
    }
}
    
