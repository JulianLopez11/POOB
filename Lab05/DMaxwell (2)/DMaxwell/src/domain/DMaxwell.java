package domain;
import java.util.*;
public class DMaxwell {
    
    private final int posDemonDefault = 225;
    private final int[] paredDefault = {20,61,102,143,184,225,266,307,348,389,430};
    private final int[] blueDefault = {43,52,139,254,291,343,67,201,228,310};
    private final int[] redDefault = {48,55,126,336,79,112,193,277,326,360};
    private final int[] defaultHoles = {116,129,175,288,356,364};
    private  int[] blues;
    private  int[] red;
    private  int[] holes;
    private int[] wall;
    private int demon ;
    


    public DMaxwell(){
        blues = blueDefault.clone();
        red = redDefault.clone();
        holes = defaultHoles.clone();
        wall = paredDefault.clone();
        demon = posDemonDefault;
    }
    public DMaxwell(int height, int width, int numBlue, int numRed, int numHoles) {
        int totalCells = height * ((2 * width) + 1);
        Random rand = new Random();
        Set<Integer> used = new HashSet<>();
    
        blues = new int[numBlue];
        red = new int[numRed];
        holes = new int[numHoles];
    
        // generar azules
        for (int i = 0; i < numBlue; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            blues[i] = pos;
        }
    
        // generar rojas
        for (int i = 0; i < numRed; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            red[i] = pos;
        }
    
        // generar agujeros
        for (int i = 0; i < numHoles; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            holes[i] = pos;
        }
    
        // generar muro en el centro
        wall = new int[(2 * width) + 1];
        int centerRow = height / 2;
        for (int i = 0; i < wall.length; i++) {
            wall[i] = centerRow * ((2 * width) + 1) + i;
            used.add(wall[i]);
        }
    
        // posición del demonio al centro (opcional)
        demon = centerRow * ((2 * width) + 1) + width;
    }
    

    public int[] consult(){
        int[] particles = new int[5];
        return particles;
    }

    public int[][] container(){
        return new int[][] { blues, red, holes, wall};
    }


}