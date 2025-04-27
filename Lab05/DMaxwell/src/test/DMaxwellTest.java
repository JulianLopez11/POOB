package Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*; 

import domain.*;

public class DMaxwellTest {
    @Test
    public void testContainerDefaultValues() {
        DMaxwell dMaxwell = new DMaxwell();
        int[][] container = dMaxwell.container();

        assertNotNull(container);
        assertEquals(4, container.length); 
        assertEquals(10, container[0].length); 
        assertEquals(10, container[1].length); 
        assertEquals(6, container[2].length); 
        assertEquals(11, container[3].length);
    }

    @Test
    public void testConsultDefaultValues() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] consult = dMaxwell.consult();

        assertNotNull(consult);
        assertEquals(5, consult.length); 
        for (int value : consult) {
            assertEquals(0, value); 
        }
    }

    @Test
    public void testVerifyHole() {
        DMaxwell dMaxwell = new DMaxwell();
        int[] holes = dMaxwell.container()[2];
        assertFalse(dMaxwell.verifyHole(holes[0]));
        assertTrue(dMaxwell.verifyHole(0)); 
    }
}
