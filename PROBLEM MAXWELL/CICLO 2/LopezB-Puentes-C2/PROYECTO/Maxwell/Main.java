
/**
 * Write a description of class Main here.
 * 
 * @author (Julian Lopez , Sebastian Puentes) 
 * @version (1,0)
 */
public class Main {
    public static void main(String[] args) {
        MaxwellContainer container = new MaxwellContainer(300, 400, 15, 4, 4, new int[][]{
            {300, 110, 15, 15}, 
            {160, 250, 15, 15}, 
            {220, 220, 15, 15},
            {550, 15, 15, 15},
            {550,150,15,15},
            {600,180,15,15},
            
            
        });
        container.addDemon(280);
        container.makeVisible();
        container.start(1000);
        container.isGoal();
        container.consultHoles();
        container.consultParticles();
        
    }
}
 
