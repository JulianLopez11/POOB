import java.awt.*;
import java.util.*;
/**
 * Write a description of class Pit here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Pit{
    
    private Rectangle rectangle1;
    private Rectangle rectangle2;
    private Rectangle rectangleSeeds;
    private int posX;
    private int posY;
    private ArrayList<Rectangle> Seeds = new ArrayList<>();
    
    
    public Pit(){
    rectangle1 = new Rectangle();
    rectangle1.changeColor("green");
    rectangle1.setPositionX(80);
    rectangle1.setPositionY(100);
    rectangle1.changeSize(220,220);
    rectangle2 = new Rectangle();
    rectangle2.changeColor("blue");
    rectangle2.setPositionX(110);
    rectangle2.setPositionY(130);
    rectangle2.changeSize(160,160);
    posX = 110;
    posY = 130;
  
    }

    public void seeds(){
        return Seeds.size();
    }
    
    public void putSeeds(int seeds){
        
        int aux2 = 0;
        int aux3 = 0;
        
        for(int i = 0 ; i< seeds; i++){
            
            Rectangle aux = new Rectangle();
            aux.changeColor("yellow");
            aux.makeVisible();
            Seeds.add(aux);
            if(posX + aux2 > 258){
                aux2 = 0;
                aux3 += 35;
    
            }
            aux.setPositionX(posX + aux2);
            aux.setPositionY(posY + aux3);
            aux.changeSize(18,18);
            aux2 += 28.5;
        }
        
    }
    
    public void removeSeeds(int seeds){
        
    }

    public void makeVisible(){
        rectangle1.makeVisible();
        rectangle2.makeVisible();
        for(Rectangle k : Seeds ){
            k.makeVisible();
        }
    }
    
    public void makeInvisible(){
        rectangle1.makeInvisible();
        rectangle2.makeInvisible();
    }
    
     public void changeColors(String background,String seeds){
        rectangle1.changeColor(background);
        rectangle2.changeColor(seeds);
        
           
    }
    public void moveTo(int x, int y){
        
    }
    

}
