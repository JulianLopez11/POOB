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
    private final int[] wallDefault = {20,61,102,143,184,225,266,307,348,389,430};
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
        wall = wallDefault.clone();
        demon = posDemonDefault;
        h = 11;
        w = 11;

    }
    public DMaxwell(int height, int width, int numBlue, int numRed, int numHoles) throws DMaxwellExceptions {
        if(height < 0  || width<0||numBlue<0||numRed<0||numHoles<0) throw new DMaxwellExceptions(DMaxwellExceptions.ONLY_POSITIVE_DIMENTIONS);
        h = height;
        w = width+1;
        r = numRed;
        b = numBlue;
        o = numHoles;
        checkMidWall(h, w);
        createNewPositions();
    }
    
    public void move(char direction){
        int [] blues1 = blues.clone();
        int [] reds1 = reds.clone();

        for (int i = 0; i < blues1.length; i++) {
            int pos = positions(blues1[i], direction);
            if (verifyHole(pos)){
                blues1[i] = pos;
            }
            else {
                blues1[i] = -1;
            }
        }
        for (int i = 0; i < reds1.length; i++) {
            int pos = positions(reds1[i], direction);
            if (verifyHole(pos)) {
                reds1[i] = pos;
            }else {
                reds1[i] = -1;
            }
        }
        blues = removePositions(blues1);
        reds = removePositions(reds1);
    }
    
    private int positions(int num, char direccion) {
        int col = num % w;
        
        if (num < 0 || num >= h * w) {
            return num;
        }
        if (direccion == 'u') {
            
            for (int i : wall) {
                if (i == num - w) {
                    return num;
                }
            }

            if (num < w) {
                return num;
            }
            return num - w;
        }
    

        if (direccion == 'd') {

            for (int i : wall) {
                if (i == num + w) {
                    return num;
                }
            }
            
            if (num >= (h - 1) * w) {
                return num;
            }
            return num + w;
        }
    
        if (direccion == 'r') {
        
            for (int i : wall) {
                if (num + 1 == i && i != demon) {
                    return num;
                }
            }

            if ((num + 1) % w == 0) {
                return num;
            }
            return num + 1;
        }
    
        
        if (direccion == 'l') {
            
            for (int i : wall) {
                if (num - 1 == i && i != demon) {
                    return num;
                }
            }
            
            if (col == 0) {
                return num;
            }
            return num - 1;
        }
    
        return num;
    }
    
    private int[] removePositions(int[] array) {
        int count = 0;
        for (int i : array) {
            if (i != -1) {
                count++;
            }
        }
        int[] newArray = new int[count];
        int index = 0;
        for (int i : array) {
            if (i != -1) {
                newArray[index++] = i;
            }
        }
        return newArray;
    }
    

    public boolean verifyHole(int position) {
        for (int hole : holes) {
            if (hole == position) {
                return false;
            }
        }
        return true;
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

    public static ArrayList<Integer> arrayToArrayList(int[] array) {
        ArrayList<Integer> lista = new ArrayList<>();
        for (int num : array) {
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