package cz.pokrocile.nastroje;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Testy komparační funkce {@link ToolCisla#compare_int(int, int)}.
 */
class ToolCislaTest {

    @Test
    @DisplayName("a < b vrací -1")
    void mensi() {
        assertEquals(-1, ToolCisla.compare_int(1, 2));
        assertEquals(-1, ToolCisla.compare_int(-5, 3));
        assertEquals(-1, ToolCisla.compare_int(-10, -9));
    }

    @Test
    @DisplayName("a > b vrací +1")
    void vetsi() {
        assertEquals(1, ToolCisla.compare_int(2, 1));
        assertEquals(1, ToolCisla.compare_int(3, -5));
        assertEquals(1, ToolCisla.compare_int(-9, -10));
    }

    @Test
    @DisplayName("a == b vrací 0")
    void rovno() {
        assertEquals(0, ToolCisla.compare_int(0, 0));
        assertEquals(0, ToolCisla.compare_int(42, 42));
        assertEquals(0, ToolCisla.compare_int(-7, -7));
    }

    @Test
    @DisplayName("Krajní hodnoty nepřetečou (nepoužívá se a - b)")
    void krajniHodnoty() {
        assertEquals(-1, ToolCisla.compare_int(Integer.MIN_VALUE, Integer.MAX_VALUE));
        assertEquals(1, ToolCisla.compare_int(Integer.MAX_VALUE, Integer.MIN_VALUE));
        assertEquals(-1, ToolCisla.compare_int(Integer.MIN_VALUE, 1));
        assertEquals(0, ToolCisla.compare_int(Integer.MIN_VALUE, Integer.MIN_VALUE));
    }
}
