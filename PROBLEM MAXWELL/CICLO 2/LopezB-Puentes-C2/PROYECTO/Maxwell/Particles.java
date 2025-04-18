import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * Class Particles 
 * 
 * @author (Julian Camilo Lopez Barrero && Juan Sebastian Puentes Julio) 
 * @version (07/03/2025)
 */
public class Particles
{
    private Circle particle;
    private String color;
    private int xPosition;
    private int yPosition;
    private int vx;
    private int vy;
   
    /**
     * Creates new particles with an specific position and velocity
     * @param the color of the particles
     * @param xPosition the x Position
     * @param yPosition the y position
     * @param vx the velocity in x
     * @param vy the velocity in x
     */
    public Particles(String color, int xPosition, int yPosition,int vx, int vy){
        this.color = color;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.vx = vx;
        this.vy = vy;
        particle = new Circle();
        particle.changeColor(color);
        particle.setPosition(xPosition, yPosition);
        particle.changeSize(10);
    }
    
    /**
     * Method to  move the particles.
     */
    public void move(){
        particle.makeInvisible();
        particle.moveHorizontal(vx);
        particle.moveVertical(vy);
        xPosition += vx;
        yPosition += vy;
        particle.makeVisible();
    }
    
    /**
     * Checks particles collision.
     * @param isInLeftSide true if is in left side of the board.
     * @param minXLeftBoard min x.
     * @param maxXLeftBoard max x of leftboard.
     * @param minXRightBoard min x of rightboard.
     * @param maxXRightBoard max x of rightbaord.
     * @param minY minimun y.
     * @param maxY maximum y.
     */
    public void checkCollision( boolean isInLeftSide,int minXLeftBoard, int maxXLeftBoard,int minXRightBoard,int maxXRightBoard,int minY,int maxY){

        if(isInLeftSide){
                if (getX() <= minXLeftBoard) {
                    setVx(Math.abs(vx));
                }else if (getX() >= maxXLeftBoard) {
                    setVx(-Math.abs(vx));
                } 
        }else{
                if (getX() <= minXRightBoard) {
                    setVx(Math.abs(vx));
                } else if (getX() >= maxXRightBoard) {
                    setVx(-Math.abs(vx));
                } 
        }
        
        if (getY() <= minY) {
            setVy(Math.abs(vy));
        }else if (getY() >= maxY) {
            setVy(-Math.abs(vy));
        }
    }
    
    /**
     * Set velocity in x
     * @param velocity in x 
     */
    public void setVx(int vx){
        this.vx = vx;
    }
    
    /**
     * Set velocity in y
     * @param velocity in x 
     */
    public void setVy(int vy){
        this.vy = vy;
    }
    
    /**
     * Get velocity in x
     */
    public int getVx() {
        return this.vx;
    }
    
    /**
     * Get velocity in y
     */
    public int getVy() {
        return this.vy;
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
     * Set the position of the particle
     * @param position in x
     * @param position in y
     */
    public void setPositionParticle(int px, int py){
        particle.setPosition(px,py);
    }
    
    /**
     * Set the position of the particle in x
     * @param position in x
     */
    public void setXPositionParticle(int px){
        this.xPosition = px;
        particle.setPosition(px,this.yPosition);
    }
    
    /**
     * Set the position of the particle in y
     * @param position in y
     */
    public void setYPositionParticle(int py){
        this.yPosition = py;
    }
    
    /**
     * Make Visible the particle
     */
    public void makeVisibleParticle(){
        particle.makeVisible();
    }
    
    /**
     * Make Invisible the particle
     */
    public void makeInvisibleParticle(){
        particle.makeInvisible();
    }
    
    /**
     * Get the color of the particle
     */
    public String getColor() {
        return this.color;
    }
    
    /**
     * Sets particle color.
     * @param color Particles color.
     */
    public void setColor(String color){
        this.color = color;
    }
    
}
