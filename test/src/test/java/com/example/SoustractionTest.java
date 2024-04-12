package com.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SoustractionTest {

    @Test
    public void testCalculer() {
        Soustraction soustraction = new Soustraction();
        double param1 = 5.0;
        double param2 = 3.0;
        double expectedResult = 2.0;
        double result = soustraction.calculer(param1, param2);
        assertEquals(expectedResult, result, 0.0001);
    }
}
