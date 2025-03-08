import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import javax.swing.JOptionPane;

/**
 * MaxwellContainer.
 * 
 * @author (Juan Sebastian Puentes Julio y Julian Camilo Lopez Barrero) 
 * @version (4/03/2025)
 */
public class MaxwellContainer{
    
    private Rectangle leftBoard = new Rectangle();
    private Rectangle rightBoard = new Rectangle();
    private Particles particle;
    private Hole hole;
    private ArrayList<Rectangle> demons = new ArrayList<>();
    private ArrayList<Circle> particlesAvailables = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private boolean lastOperation = false;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    
    /**
     * Constructor de MaxwellContainer.
     * @param height
     * @param weight
     */
    public MaxwellContainer(int h, int w){
        //1 PLAYER
        leftBoard.changeColor("white");
        leftBoard.changeSize(h,w);
        //2 PLAYER
        rightBoard.changeColor("white");
        rightBoard.changeSize(h,w);
        rightBoard.moveHorizontal(w+20);
        
    }
    
    
    /**
     * Constructor de MaxwellContainer con múltiples parámetros.
     * @param h Altura del contenedor.
     * @param w Ancho del contenedor.
     * @param d Número de demonios.
     * @param b Número de barreras.
     * @param r Número de partículas rojas.
     * @param particles Lista de posiciones de partículas.
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, ArrayList<ArrayList<Integer>> particles) {
        //Board
        this(h, w);
        
        for (int i = 0; i < d; i++) {
            addDemon(15 + (i * 20)); 
        }
        
    
        for (ArrayList<Integer> particleData : particles) {
            return;
        }
    }


    /**
     * Method to add a demon in an specific Vertical Position
     * @param vertical Distance
     * @return void
     */
    public void addDemon(int d) {
    
        if (15 <= d && d <= leftBoard.getHeight() - 20) {
            for (Rectangle existingDemon : demons) {
                if (Math.abs(existingDemon.getY() - d) < 20) { 
                    JOptionPane.showMessageDialog(null, "There is already a Demon in that position");
                    lastOperation = false;
                    return;
                }
            }
            Rectangle demon = new Rectangle();
            demon.moveHorizontal(leftBoard.getWidth());
            demon.makeVisible();
            demon.changeColor("red");
            demon.changeSize(20, 20);
            demon.moveVertical(d-15);
            demons.add(demon);
            lastOperation = true;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Range");
            lastOperation = false;
        }
    } 
    
    /**
     * Method to del a demon in an specific Vertical Position
     * @param vertical Distance
     * @return void
     */
    public void delDemon(int d) {
        for (int i = 0; i < demons.size(); i++) {
            if (demons.get(i).getY() == d) {
                demons.get(i).makeInvisible();
                demons.remove(i);
                lastOperation = true;
                i--; //Making sure that in the arrayList dont have repeted indexes.
            } 
            else {
            lastOperation = false;
            }
        }
    
        if (!lastOperation) {
        JOptionPane.showMessageDialog(null, "No Demon found at that position");
        }
    }
    
    public void addParticle(String color, boolean isRed, int px, int py, int vx, int vy){
    
        if (isRed){
            leftBoard.addParticle(color,px,py,vx,vy);
        }
        else{
            rightBoard.addParticle(color,px,py,vx,vy);
        }
        
    }
    
    public void delParticle(String color){
        ArrayList<Particles> storedParticles_left = leftBoard.getParticles();
        ArrayList<Particles> storedParticles_right = rightBoard.getParticles();
        storedParticles_left.removeAll(storedParticles_right);
        for(int i = 0 ; i < storedParticles_left.size(); i++){
            if(storedParticles_left.get(i).getColor() == color){ 
                storedParticles_left.get(i).makeInvisibleParticle();
                storedParticles_left.remove(i);
            }
        }
    }
    
    public void addHole(int px, int py, int particles){
        int countEatingParticles = 0;
        if (15 <= py && py <= leftBoard.getHeight() - 20 &&  70 <= px && px <= leftBoard.getWidth() - 20 ||
            rightBoard.getX() <= px && px <= rightBoard.getX() + rightBoard.getWidth()){ // No pass Board Size
            Hole hole = new Hole(px,py,30,30);
            holes.add(hole);
            lastOperation = true;
            for (Circle p : particlesAvailables) {
                if (p.getX() == px && p.getY() == py) {
                    countEatingParticles++;
                    delParticle(p.getColor());
                    if (countEatingParticles > particles ) {
                    return;
                    }
                }      
            }
        }
    }
    
    public void start(int ticks){
        
    }
    
    public boolean isGoal(){
    
        return true;
         
    }
    
    public int[][] particles(){
        return null;
    }
    
    public int[][] holes(){
        return null;
    }
    
    public void makeVisible(){
        leftBoard.makeVisible();
        rightBoard.makeVisible();
        for(Rectangle demon: demons){
            demon.makeVisible();   
        }
        
        for(Hole hole: holes){
            hole.makeVisibleHole();
        }
        
        for(Circle particle : particlesAvailables){
            particle.makeVisible();
        }
    }
    
    public void makeinVisible(){
        leftBoard.makeInvisible();
        rightBoard.makeInvisible();
        for(Rectangle demon: demons){
            demon.makeInvisible();   
        }
        
        for(Hole hole: holes){
            hole.makeInvisibleHole();
        }
        
        for(Circle particle : particlesAvailables){
            particle.makeInvisible();
        }
        
    }
    
    public void finish(){
        
    }
    
    /**
     * Verify if the last operation is valid or not
     * @return Boolean
     */
    public boolean ok(){
        return lastOperation;
    }
    
}
