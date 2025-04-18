
/**
 * Class Demon 
 * 
 * @author (Julian Camilo Lopez Barrero && Juan Sebastian Puentes Julio) 
 * @version (07/03/2025)
 */
public class Demon
{
    private int x;
    private int y;
    private int size = 15;
    private Rectangle demon;
    
    /**
     * Constructor of Demon.
     */
    public Demon() {
        demon = new Rectangle();
        x = getX();
        y = getY();
        demon.changeSize(20, 20);   
        demon.changeColor("red");
    }
    
    /**
     * Verifys for right particles the access for left chamber.
     * @param p Particles.
     * @return boolean.
     */
    public boolean isInRight(Particles p){
        return Math.abs(p.getX() - getX()) <= size + 10 && 
                Math.abs(p.getY() - getY()) <= size  
                && p.getColor().equals("blue");
    }
    
    /**
     * Verifys for left particles the access for right chamber.
     * @param p Particles.
     * @return boolean.
     */
    public boolean isInLeft(Particles p) {
        return (Math.abs(p.getX()- getX()) <= size &&
                Math.abs(p.getY() - getY()) <= size &&  
                !p.getColor().equals("blue"));
    }
    
    /**
     * Demons Height.
     * @return int.
     */
    public int getDemonHeight(){
        return demon.getHeight();
    }
    
    /**
     * Sets demons y position.
     * @param y.
     */
    public void setYPosition(int y){
        this.y = y;
    }
    
    /**
     * Sets demons x position.
     * @param x.
     */
    public void setXPosition(int x){
        this.x = x;
    }
    
    /**
     * Demons Width.
     * @return int.
     */
    public int getDemonWidth(){
        return demon.getWidth();
    }
    
    /**
     * Move demon vertical
     * @param distance
     */
    public void moveVerticalDemon(int distance) {
        demon.moveVertical(distance);
    }
    
    /**
     * Move the demon vertical
     * @param distance
     */
    public void moveHorizontalDemon(int distance) {
        demon.moveHorizontal(distance);
    }
    
    /**
     * Get the X position
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Get the Y position
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Make Visible the demon
     */
    public void makeVisibleDemon(){
        demon.makeVisible();
    }
    
    /**
     * Make InVisible the demon
     */
    public void makeInvisibleDemon(){
        demon.makeInvisible();
    }
}
