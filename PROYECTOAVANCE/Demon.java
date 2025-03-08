
/**
 * Write a description of class Demon here.
 * 
 * @author (Juan Sebastian Puentes , Julian Camilo Lopez) 
 * @version (05/03/2025)
 */
public class Demon
{
    private int x;
    private int y;
    private Rectangle demon;
    private MaxwellContainer rectangle;

    /**
     * Constructor for objects of class Demon
     */
    public Demon(){
        
        demon.changeColor("red");
        demon.changeSize(20,20);
        demon.makeVisible();

    }
    
    public int getY() {
        return y;
    }
    
    public void makeVisibleDemon(){
        demon.makeVisible();
    }
    
    public void makeInvisibleDemon(){
        demon.makeInvisible();
    }



}
