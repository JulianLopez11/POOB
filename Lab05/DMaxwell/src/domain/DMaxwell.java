package domain;
import java.util.*;
public class DMaxwell {
    
    private int h;
    private int w;
    private int r;
    private int b;
    private int o;
    private int demon ;

    private final int posDemonDefault = 225;
    private final int[] paredDefault = {20,61,102,143,184,225,266,307,348,389,430};
    private final int[] blueDefault = {43,52,139,254,291,343,67,201,228,310};
    private final int[] redDefault = {48,55,126,336,79,112,193,277,326,360};
    private final int[] defaultHoles = {116,129,175,288,356,364};
    private int[] blues;
    private int[] reds;
    private int[] holes;
    private int[] wall;

    


    public DMaxwell(){
        blues = blueDefault.clone();
        reds = redDefault.clone();
        holes = defaultHoles.clone();
        wall = paredDefault.clone();
        demon = posDemonDefault;
    }
    public DMaxwell(int height, int width, int numBlue, int numRed, int numHoles) {
        h = height;
        w = width+1;
        r = numRed;
        b = numBlue;
        o = numHoles;
        checkMidWall(h, w);
        createNewPositions();
    }
    
    private void createNewPositions() {
        Random random = new Random();
        ArrayList<Integer> wall1 = arrayToArrayList(wall);
        ArrayList<Integer> disponibles = new ArrayList<>();

        for (int i = 0; i < h * w; i++) {
            if (!wall1.contains(i) && i != demon) {
                disponibles.add(i);
            }
        }
    
        Collections.shuffle(disponibles, random);

        blues = new int[b];
        reds = new int[r];
        holes = new int[o];
    
        int index = 0;
        for (int i = 0; i < b; i++) {
            blues[i] = disponibles.get(index++);
        }
        for (int i = 0; i < r; i++) {
            reds[i] = disponibles.get(index++);
        }
        for (int i = 0; i < o; i++) {
            holes[i] = disponibles.get(index++);
        }
    }
    

    private void checkMidWall(int h, int wi) {
        int widthBoard = (wi - 1) / 2;
        int salto = (2 * widthBoard) + 1;
        wall = new int[h];
        int inicio = widthBoard;
        for (int fila = 0; fila < h; fila++) {
            wall[fila] = inicio + fila * salto;
        }
        demon = wall[h / 2];
    }
    

    public static ArrayList<Integer> arrayToArrayList(int[] arreglo) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int num : arreglo) {
            lista.add(num);
        }
        return lista;
    }

    public int[] consult(){
        int[] particles = new int[5];
        return particles;
    }

    public int[][] container(){
        return new int[][] { blues, reds, holes, wall};
    }


}