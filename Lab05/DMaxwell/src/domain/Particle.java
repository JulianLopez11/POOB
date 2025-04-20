package domain;
import java.awt.*;
public class Particle {
    int x, y; 
    Color color; 

    public Particle(int x, int y, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Color getColor(){
        return color;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
