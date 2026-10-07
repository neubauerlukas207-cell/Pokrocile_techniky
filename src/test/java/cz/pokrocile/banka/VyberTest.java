package cz.pokrocile.banka;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy třídy {@link Vyber}.
 */
class VyberTest {

    private final LocalDateTime datum = LocalDateTime.of(2026, 1, 1, 12, 0);

    @Test
    void gettery() {
        Vyber z = new Vyber(datum, 20);
        assertEquals(datum, z.getDatum());
        assertEquals(20, z.getKolik());
    }

    @Test
    void nekladnaCastka() {
        assertThrows(IllegalArgumentException.class, () -> new Vyber(datum, 0));
        assertThrows(IllegalArgumentException.class, () -> new Vyber(datum, -1));
    }

    @Test
    void chybiDatum() {
        assertThrows(NullPointerException.class, () -> new Vyber(null, 20));
    }
}
