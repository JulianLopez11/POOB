package domain;
import java.util.*;
public class DMaxwell {
    
    private final int posDemonDefault = 225;
    private final int[] paredDefault = {20,61,102,143,184,225,266,307,348,389,430};
    private final int[] blueDefault = {43,52,139,254,291,343,67,201,228,310};
    private final int[] redDefault = {48,55,126,336,79,112,193,277,326,360};
    private final int[] defaultHoles = {116,129,175,288,356,364};
    private int[] blues;
    private int[] reds;
    private int[] holes;
    private int[] wall;
    private int demon ;
    


    public DMaxwell(){
        blues = blueDefault.clone();
        reds = redDefault.clone();
        holes = defaultHoles.clone();
        wall = paredDefault.clone();
        demon = posDemonDefault;
    }
    public DMaxwell(int height, int width, int numBlue, int numRed, int numHoles) {
        int totalCols = (2 * width) + 1;
        int totalCells = height * totalCols;
        Random rand = new Random();
        Set<Integer> used = new HashSet<>();
        
        // generar muro en el centro
        wall = new int[totalCols];
        int centerRow = height / 2;
        for (int i = 0; i < wall.length; i++) {
            wall[i] = centerRow * totalCols + i;
            used.add(wall[i]);
        }
    
        // posición del demonio al centro de la pared
        demon = centerRow * totalCols + width;
    
        // asegurar que el demonio esté libre
        used.add(demon);
    
        // generar azules
        blues = new int[numBlue];
        for (int i = 0; i < numBlue; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            blues[i] = pos;
        }
    
        // generar rojas
        reds = new int[numRed];
        for (int i = 0; i < numRed; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            reds[i] = pos;
        }
    
        // generar agujeros
        holes = new int[numHoles];
        for (int i = 0; i < numHoles; i++) {
            int pos;
            do {
                pos = rand.nextInt(totalCells);
            } while (!used.add(pos));
            holes[i] = pos;
        }
    }
    
    

    public int[] consult(){
        int[] particles = new int[5];
        return particles;
    }

    public int[][] container(){
        return new int[][] { blues, reds, holes, wall};
    }


}