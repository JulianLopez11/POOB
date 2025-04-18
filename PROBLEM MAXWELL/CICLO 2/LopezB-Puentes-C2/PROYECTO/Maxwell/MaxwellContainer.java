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
    private Rectangle backgroundBoard = new Rectangle();
    private Rectangle leftBoard = new Rectangle();
    private Rectangle rightBoard = new Rectangle();
    private Particles particle;
    private Hole hole;
    private ArrayList<Demon> demons = new ArrayList<>();
    private ArrayList<Particles> particlesAvailables = new ArrayList<>();
    private ArrayList<Hole> holes = new ArrayList<>();
    private boolean lastOperation = false;
    private int height;
    private int width;
    private int xPosition;
    private int yPosition;
    private int size = 15;
    private int minXLeftBoard = 70;
    private int minY = 15;
    
    /**
     * Constructor de MaxwellContainer.
     * @param height
     * @param weight
     */
    public MaxwellContainer(int h, int w){
        //BackGround
        backgroundBoard.changeColor("black");
        backgroundBoard.changeSize(h + 20, w * 2 + 40);
        backgroundBoard.moveHorizontal(-10);
        backgroundBoard.moveVertical(-10);
        //LeftBoard
        leftBoard.changeColor("white");
        leftBoard.changeSize(h,w);
        //RightBoard
        rightBoard.changeColor("white");
        rightBoard.changeSize(h,w);
        rightBoard.moveHorizontal(w+20);
    }
    
    /**
     * Constructor of MaxwellContainer with multiple parameters
     * @param h height of the container
     * @param w width of the container.
     * @param d number of demons
     * @param b number of blue balls
     * @param r number of red balls
     * @param particles array of particles
     * Our board min X and Y is 70 15.
     */
    public MaxwellContainer(int h, int w, int d, int b, int r, int[][] particles) {
        if(h<0) lastOperation = false;
        backgroundBoard.changeColor("black");
        backgroundBoard.changeSize(h + 20, w * 2 + 40);
        backgroundBoard.moveHorizontal(-10);
        backgroundBoard.moveVertical(-10);
        leftBoard.changeColor("white");
        leftBoard.changeSize(h,w);
        rightBoard.changeColor("white");
        rightBoard.changeSize(h,w);
        rightBoard.moveHorizontal(w+20);
        lastOperation = true;
        width = w;
        height = h;
        addDemon(d);
        int total = b+r;
        for (int i = 0; i < particles.length; i++) {
            if (total <= r) {
                addParticle("blue", false, particles[i][0], particles[i][1], particles[i][2], particles[i][3]);
                continue;
            }
            addParticle("red", true, particles[i][0], particles[i][1], particles[i][2], particles[i][3]);
            total--;
        }
    }

    /**
     * Method to add a demon in an specific Vertical Position.
     * @param d Vertical Distance
     */
    public void addDemon(int d) {
        if (15 <= d && d <= leftBoard.getHeight() - 20) {
            for (Demon existingDemon : demons) {
                if (Math.abs(existingDemon.getY() - d) < 20) { 
                    JOptionPane.showMessageDialog(null, "There is already a Demon in that position");
                    lastOperation = false;
                    return;
                }
            }
            Demon demon = new Demon();
            demon.moveHorizontalDemon(leftBoard.getWidth());
            demon.setXPosition(leftBoard.getWidth()+70);
            demon.moveVerticalDemon(d-15);
            demon.setYPosition(d);
            demons.add(demon);
            lastOperation = true;
        } 
        else {
            JOptionPane.showMessageDialog(null, "Invalid Range");
            lastOperation = false;
        }
    } 
    
    /**
     * Method to del a demon in an specific Vertical Position
     * @param d Vertical Distance
     */
    public void delDemon(int d) {
        for (int i = 0; i < demons.size(); i++) {
            if (demons.get(i).getY() == d) {
                demons.get(i).makeInvisibleDemon();
                demons.remove(i);
                lastOperation = true;
                i--; //Indices No repetidos
            } else{
                lastOperation = false;
            }
        }
        
    }
    
    /**
     * Method to add particles 
     * The Velocity of the particles cannot be > 20
     * @param color of the particle
     * @param isRed If the color is red or not.
     * @param px position horizontal of the particle.
     * @param py position vertical of the particle.
     * @param vx velocity horizontal of the particle.
     * @param vy velocity vertical of the particle.
     */
    public void addParticle(String color, boolean isRed, int px, int py, int vx, int vy) {
        if (verifyRangeBoard(px, py)) {
            JOptionPane.showMessageDialog(null, "Invalid Range.");
            lastOperation = false;
            return;
        }
        
        if(isRed && px > 70 && px < leftBoard.getX() + leftBoard.getWidth()){
            Particles particle = new Particles(color, px, py, vx, vy);
            particlesAvailables.add(particle);
            lastOperation = true;
        }else if(!isRed && px > rightBoard.getX() && px < rightBoard.getX() + rightBoard.getWidth()){
            Particles particle = new Particles(color, px, py, vx, vy);
            particlesAvailables.add(particle);
            lastOperation = true;
        } 
    }
    
    /**
     * Auxiliar method for verifying if particles is inside board.
     * @param x X position of the particle.     
     * @param y Y position of the particle.
     */
    private boolean verifyRangeBoard(int x, int y){
        return (x < 70 || x > backgroundBoard.getWidth() || y < 15 || y > leftBoard.getHeight());
    }
    
    /**
     * Moves particles and verifys holes and demons rules.
     */
    public void moveParticles() {
        int maxXLeftBoard = leftBoard.getX() + leftBoard.getWidth() - size;
        int minXRightBoard = rightBoard.getX();
        int maxXRightBoard = rightBoard.getX() + rightBoard.getWidth() - size; 
        int maxY = leftBoard.getY() + leftBoard.getHeight() - size;
        ArrayList<Particles> toRemove = new ArrayList<>();
        for (Particles p : particlesAvailables) {
            boolean leftSide = isInLeftSide(p); 
            p.move();
            p.checkCollision(leftSide,minXLeftBoard, maxXLeftBoard, minXRightBoard, maxXRightBoard, minY, maxY);
            for (Demon d : demons) {
                if(isInLeftSide(p)){
                    if(d.isInLeft(p)){
                        p.setXPositionParticle(minXRightBoard); 
                    }
                }else{
                    if (d.isInRight(p)) {
                        p.setXPositionParticle(maxXLeftBoard);  
                    }
                }
            }
            
            for (Hole h : holes) {
                if (isInTheSamePosition(p, h) && h.canEat()) {
                    h.eatParticle();
                    toRemove.add(p);
                }else if(!h.canEat()){
                    h.makeInvisibleHole();
                }
            }
            
        }
        particlesAvailables.removeAll(toRemove);
        for (Particles p : toRemove) {
            p.makeInvisibleParticle();
        }
    }  
    
    /**
     * Verifys if particle is in the left board.
     * @param Particle.
     */
    private boolean isInLeftSide(Particles p){
        return p.getX() < leftBoard.getX() + leftBoard.getWidth();
    }
    
    /**
     * Auxiliar method to Verify if a particle is in the same position with a hole.
     * @param Particle
     * @param Hole
     */
    private boolean isInTheSamePosition(Particles p, Hole h) {
        if (p == null || h == null) {
            return false;
        }
        return (p.getX() + size > h.getX() &&  p.getX() < h.getX() + h.getWidth() && p.getY() + size > h.getY() &&  
            p.getY() < h.getY() + h.getHeight());
    }
    
    /**
     * Method to delete a particle of an specific color
     * @param color Color of the particle.
     */
    public void delParticle(String color) {
        for (int i = 0; i < particlesAvailables.size(); i++) {
            Particles p = particlesAvailables.get(i);
            if (p.getColor().equals(color)) {
                p.makeInvisibleParticle();  
                particlesAvailables.remove(i);
                lastOperation = true;
                return;  
            }else{
                JOptionPane.showMessageDialog(null, "Not Particle Found.");
                lastOperation = false;
            }
        }
        
    }

    /**
     * Method to add a hole with his position and nunber of particles to being eaten.
     * @param px position horizontal of the hole
     * @param py position vertical of the hole
     * @param particle number of particles to be eating
     */
    public void addHole(int px, int py, int particles){
        if (isInBoard(px,py)){ 
            Hole hole = new Hole(px,py,20,20,particles);
            holes.add(hole);
            lastOperation = true;
        }else{
            lastOperation = false;            
        }
    }
    
    /**
     * Verifys if hole is inside boards limit.
     * @param px X position of the hole.
     * @param px Y position of the hole.
     */
    private boolean isInBoard(int px,int py){
        return (15 <= py && py <= leftBoard.getHeight() - 20 &&  70 <= px && px <= leftBoard.getWidth() - 20 || 
                rightBoard.getX() <= px && px <= rightBoard.getX() + rightBoard.getWidth());
    }
    
    /**
     * Method to start the game
     * @param ticks number of ticks
     * @return void
     */
    public void start(int ticks) {
        for (int i = 0; i < ticks; i++) {
            if(isGoal()){
                JOptionPane.showMessageDialog(null, "The Game Has Ended.");
                finish();
                return;
            }
            moveParticles();
        }
    }
    
    /**
     * Method to verify if all of red an blue particles crossed demon.
     * @return boolean.
     */
    public boolean isGoal(){
        for(Particles particle: particlesAvailables){
            if(particle.getColor().equals("red") && particle.getX() > rightBoard.getX() && 
                particle.getX() < rightBoard.getX() + rightBoard.getWidth()){
                continue;
            }else if(particle.getColor().equals("blue") && particle.getX() < leftBoard.getX() + leftBoard.getWidth() 
                    && particle.getX() > minXLeftBoard){
                continue;
            }else{
                return false;
            }
        }
        return true;
    }
    /**
     * Method to see the position x and y of the particles
     * @return int[][] particles.
     */
    public int[][] particles() {
        int[][] positions = new int[particlesAvailables.size()][2];
        for (int i = 0; i < particlesAvailables.size(); i++) {
            Particles p = particlesAvailables.get(i);
            positions[i][0] = p.getX();
            positions[i][1] = p.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }

    /**
     * Auxiliar method for organising particles positions and velocitys, also for holes.
     * @param positions.
     */
    private void sortMatrixParticles(int[][] positions){
        for (int i = 0; i < positions.length - 1; i++) {
            for (int j = 0; j < positions.length - 1 - i; j++) {
                if (positions[j][0] > positions[j + 1][0]) {
                    int[] aux = positions[j];
                    positions[j] = positions[j + 1];
                    positions[j + 1] = aux;
                }
            }
        }
    }
    
    /**
     * Method to see the position x and y of the holes
     * @return positions.
     */
    public int[][] holes() {
        int[][] positions = new int[holes.size()][2];
        for (int i = 0; i < holes.size(); i++) {
            Hole h = holes.get(i);
            positions[i][0] = h.getX();
            positions[i][1] = h.getY();
        }
        sortMatrixParticles(positions);
        return positions;
    }
    
    /**
     * Method to see the position of the Demons
     * @return array of the demons
     */
    public int[] demons(){
        int[] dataDemons = new int[demons.size()];
        for(int i = 0; i < demons.size(); i++){
            dataDemons[i] = demons.get(i).getX();
        }
        return dataDemons;
    }
    
    /***
     * Method to Consult The Status Of The Holes
     */
    public void consultHoles(){
        for(Hole h : holes){
            System.out.println("Holes Status:\nPos X: " + h.getX() + "\nPos Y: " + h.getY() 
            +"\nEaten Particles: " + h.getEatenParticles());
        } 
    }
    
    /***
     * Method to Consult The Status Of The Particles
     */
    public void consultParticles(){
        for(Particles p : particlesAvailables){
            System.out.println("Particle Status:\nPos X: " + p.getX()+ " " +"Velocity X: " + p.getVx() + 
                                "\nPos Y: " + p.getY() + " " +"Velocity Y: "+ p.getVy());
        }
    }
    
    /**
     * Method to make visible all the game 
     * 
     */
    public void makeVisible(){
        makeVisibleBoards();
        for(Demon demon: demons){
            demon.makeVisibleDemon();   
        }
        
        for(Hole hole: holes){
            hole.makeVisibleHole();
        }
        
        for(Particles particle : particlesAvailables){
            particle.makeVisibleParticle();
        }
    }
    
    /**
     * Method to make Invisible all the game .
     * 
     */
    public void makeinVisible(){
        makeInVisibleBoards();
        for(Demon demon: demons){
            demon.makeInvisibleDemon();   
        }
        
        for(Hole hole: holes){
            hole.makeInvisibleHole();
        }
        
        for(Particles particle : particlesAvailables){
            particle.makeInvisibleParticle();
        }
    }
    
    /**
     * Method to do visible leftBoard, rightBoard and backgroundBoard.
     */
    private void makeVisibleBoards(){
        backgroundBoard.makeVisible();
        leftBoard.makeVisible();
        rightBoard.makeVisible();
    }
    
    /**
     * Method to do Invisible leftBoard, rightBoard and backgroundBoard.
     */
    private void makeInVisibleBoards(){
        backgroundBoard.makeInvisible();
        leftBoard.makeInvisible();
        rightBoard.makeInvisible();
    }
    
    /**
     * Method to end the game.
     */
    public void finish(){
        makeinVisible();
        particlesAvailables.clear();
        holes.clear();
        demons.clear();
        Canvas.getCanvas().close();
    }
    
    /**
     * Verify if the last operation is valid or not.
     * @return Boolean
     */
    public boolean ok(){
        return lastOperation;
    }
    
}


