import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Write a description of class Hole here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Hole
{
    private Rectangle hole;
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;

    
    /**
     * Constructor for objects of class Hole
     */
    public Hole(int xPosition, int yPosition, int height, int width) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        hole = new Rectangle();  
        hole.changeSize(height, width);  
        hole.setPosition(xPosition,yPosition);  
        hole.changeColor("black");
        hole.makeVisible();  
    }
    
    public void makeInvisibleHole(){
        hole.makeInvisible();
    }
    
    public void makeVisibleHole(){
        hole.makeVisible();
    }
}
    
