package cz.pokrocile.banka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Testy nastavení účtu a ověření denního limitu.
 */
class BankovniUcetNastaveniTest {

    private BankovniUcetNastaveni nastaveni;

    @BeforeEach
    void setUp() {
        nastaveni = new BankovniUcetNastaveni(1000);
    }

    @Test
    void getDenniLimit() {
        assertEquals(1000, nastaveni.getDenniLimit());
    }

    @Test
    void zapornyLimit() {
        assertThrows(IllegalArgumentException.class, () -> new BankovniUcetNastaveni(-1));
    }

    @Test
    void bezHistorie() {
        assertTrue(nastaveni.verifyDenniLimit(500, new int[0]));
        assertTrue(nastaveni.verifyDenniLimit(1000, new int[0]));
        assertFalse(nastaveni.verifyDenniLimit(1001, new int[0]));
    }

    @Test
    void nullHistorieJakoPrazdna() {
        assertTrue(nastaveni.verifyDenniLimit(1000, null));
        assertFalse(nastaveni.verifyDenniLimit(1001, null));
    }

    @Test
    void sHistorii() {
        int[] dnes = {300, 200};
        assertTrue(nastaveni.verifyDenniLimit(500, dnes));   // přesně na limit
        assertFalse(nastaveni.verifyDenniLimit(501, dnes));  // o 1 přes
    }

    @Test
    void historieUzVycerpana() {
        assertFalse(nastaveni.verifyDenniLimit(1, new int[]{1000}));
    }

    @Test
    void velkeCastkyNepretecou() {
        assertFalse(nastaveni.verifyDenniLimit(Integer.MAX_VALUE, new int[]{Integer.MAX_VALUE}));
    }

    @Test
    void nulovyLimit() {
        BankovniUcetNastaveni nula = new BankovniUcetNastaveni(0);
        assertFalse(nula.verifyDenniLimit(1, new int[0]));
    }
}
