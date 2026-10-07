package cz.pokrocile.banka;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy třídy {@link Vklad}.
 */
class VkladTest {

    private final LocalDateTime datum = LocalDateTime.of(2026, 1, 1, 12, 0);

    @Test
    void gettery() {
        Vklad z = new Vklad(datum, 10);
        assertEquals(datum, z.getDatum());
        assertEquals(10, z.getKolik());
    }

    @Test
    void nekladnaCastka() {
        assertThrows(IllegalArgumentException.class, () -> new Vklad(datum, 0));
        assertThrows(IllegalArgumentException.class, () -> new Vklad(datum, -1));
    }

    @Test
    void chybiDatum() {
        assertThrows(NullPointerException.class, () -> new Vklad(null, 10));
    }
}
