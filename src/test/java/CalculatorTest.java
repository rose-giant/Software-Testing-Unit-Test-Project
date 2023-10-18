import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    @Test
    void twoPlusTwoShouldEqualFour() {
        var calculator = new Calculator();
        assertEquals(4, calculator.add(2, 2));
//        assertTrue(calculator.add(2,2) == 4);
//        assertNull(calculator.add(2,2));
    }

    @Test
    void threePlusSevenShouldEqualTen() {
        var calculator = new Calculator();
        assertEquals(10, calculator.add(3, 7));
    }
}