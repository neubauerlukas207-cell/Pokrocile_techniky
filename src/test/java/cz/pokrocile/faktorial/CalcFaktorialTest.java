package cz.pokrocile.faktorial;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy obou implementací faktoriálu.
 */
class CalcFaktorialTest {

    // ---------------- calc1: chyba návratovou hodnotou ----------------

    @ParameterizedTest(name = "calc1({0}) = {1}")
    @CsvSource({"0,1", "1,1", "2,2", "3,6", "4,24", "5,120", "10,3628800", "12,479001600"})
    void calc1PlatnyVstup(int vstup, int ocekavano) {
        assertEquals(ocekavano, CalcFaktorial.calc1(vstup));
    }

    @ParameterizedTest(name = "calc1({0}) = -1")
    @ValueSource(ints = {-1, -2, -100, Integer.MIN_VALUE, 13, 100, Integer.MAX_VALUE})
    void calc1NeplatnyVstup(int vstup) {
        assertEquals(CalcFaktorial.CHYBA, CalcFaktorial.calc1(vstup));
        assertEquals(-1, CalcFaktorial.calc1(vstup));
    }

    @Test
    @DisplayName("calc1: n! = n * (n-1)!")
    void calc1Rekurence() {
        for (int n = 1; n <= CalcFaktorial.MAX_VSTUP; n++) {
            assertEquals(n * CalcFaktorial.calc1(n - 1), CalcFaktorial.calc1(n));
        }
    }

    // ---------------- calc2: chyba výjimkou ----------------

    @ParameterizedTest(name = "calc2({0}) = {1}")
    @CsvSource({"0,1", "1,1", "2,2", "3,6", "4,24", "5,120", "10,3628800", "12,479001600"})
    void calc2PlatnyVstup(int vstup, int ocekavano) throws FaktorialException {
        assertEquals(ocekavano, CalcFaktorial.calc2(vstup));
    }

    @ParameterizedTest(name = "calc2({0}) vyhodí FaktorialException")
    @ValueSource(ints = {-1, -2, -100, Integer.MIN_VALUE, 13, 100, Integer.MAX_VALUE})
    void calc2NeplatnyVstup(int vstup) {
        FaktorialException ex = assertThrows(FaktorialException.class, () -> CalcFaktorial.calc2(vstup));
        assertEquals(vstup, ex.getVstup());
    }

    @Test
    @DisplayName("calc1 a calc2 dávají pro platné vstupy stejné výsledky")
    void obeImplementaceShodne() throws FaktorialException {
        for (int n = 0; n <= CalcFaktorial.MAX_VSTUP; n++) {
            assertEquals(CalcFaktorial.calc1(n), CalcFaktorial.calc2(n));
        }
    }
}
