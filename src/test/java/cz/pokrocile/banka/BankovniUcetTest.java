package cz.pokrocile.banka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy bankovního účtu – testuje se proti rozhraní {@link IBankovniUcet}.
 */
class BankovniUcetTest {

    private static final LocalDateTime START = LocalDateTime.of(2026, 10, 7, 10, 0);

    /** Simulovaný aktuální čas – umožňuje otestovat přechod na další den. */
    private LocalDateTime ted;
    private IBankovniUcet ucet;

    @BeforeEach
    void setUp() {
        ted = START;
        ucet = new BankovniUcet("123456789/0100", new BankovniUcetNastaveni(1000)) {
            @Override
            protected LocalDateTime ted() {
                return ted;
            }
        };
    }

    @Test
    void novyUcet() {
        assertEquals("123456789/0100", ucet.getCisloUctu());
        assertEquals(0, ucet.getAktualniStav());
        assertEquals(0, ucet.getHistorieVkladu().length);
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    void neplatnyKonstruktor() {
        BankovniUcetNastaveni n = new BankovniUcetNastaveni(1);
        assertThrows(NullPointerException.class, () -> new BankovniUcet(null, n));
        assertThrows(NullPointerException.class, () -> new BankovniUcet("1", null));
    }

    // ---------------- vklad ----------------

    @Test
    void vkladZvysiZustatekAZapiseHistorii() {
        ucet.vklad(500);
        ucet.vklad(250);
        assertEquals(750, ucet.getAktualniStav());

        Vklad[] h = ucet.getHistorieVkladu();
        assertEquals(2, h.length);
        assertEquals(500, h[0].getKolik());
        assertEquals(250, h[1].getKolik());
        assertEquals(START, h[0].getDatum());
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    void vkladNeplatnaCastka() {
        assertThrows(IllegalArgumentException.class, () -> ucet.vklad(0));
        assertThrows(IllegalArgumentException.class, () -> ucet.vklad(-100));
        assertEquals(0, ucet.getAktualniStav());
        assertEquals(0, ucet.getHistorieVkladu().length);
    }

    // ---------------- výběr ----------------

    @Test
    void vyberSnizZustatekAZapiseHistorii() throws Exception {
        ucet.vklad(2000);
        ucet.vyber(300);
        assertEquals(1700, ucet.getAktualniStav());

        Vyber[] h = ucet.getHistorieVyberu();
        assertEquals(1, h.length);
        assertEquals(300, h[0].getKolik());
        assertEquals(START, h[0].getDatum());
    }

    @Test
    @DisplayName("Lze vybrat přesně celý zůstatek")
    void vyberCelyZustatek() throws Exception {
        ucet.vklad(800);
        ucet.vyber(800);
        assertEquals(0, ucet.getAktualniStav());
    }

    @Test
    @DisplayName("Nedostatek prostředků – NedostatekProstredkuException, stav beze změny")
    void vyberNedostatek() {
        ucet.vklad(100);
        assertThrows(NedostatekProstredkuException.class, () -> ucet.vyber(101));
        assertEquals(100, ucet.getAktualniStav());
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Překročení denního limitu jedním výběrem")
    void vyberPresLimitNajednou() {
        ucet.vklad(5000);
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(1001));
        assertEquals(5000, ucet.getAktualniStav());
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Překročení denního limitu součtem více výběrů")
    void vyberPresLimitPostupne() throws Exception {
        ucet.vklad(5000);
        ucet.vyber(600);
        ucet.vyber(400); // přesně na limit
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(1));
        assertEquals(4000, ucet.getAktualniStav());
        assertEquals(2, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Druhý den se limit obnoví")
    void limitSeDalsiDenObnovi() throws Exception {
        ucet.vklad(5000);
        ucet.vyber(1000);
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(1));

        ted = ted.plusDays(1);
        ucet.vyber(1000);
        assertEquals(3000, ucet.getAktualniStav());
        assertEquals(2, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Typ chybového stavu je rozlišen třídou výjimky")
    void typChyby() {
        ucet.vklad(50);
        assertThrows(NedostatekProstredkuException.class, () -> ucet.vyber(100));

        ucet.vklad(5000);
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(2000));
    }

    @Test
    void vyberNeplatnaCastka() {
        ucet.vklad(100);
        assertThrows(IllegalArgumentException.class, () -> ucet.vyber(0));
        assertThrows(IllegalArgumentException.class, () -> ucet.vyber(-1));
        assertEquals(100, ucet.getAktualniStav());
    }

    // ---------------- historie ----------------

    @Test
    @DisplayName("Historie vrací kopii – zvenku ji nelze upravit")
    void historieJeKopie() throws Exception {
        ucet.vklad(1000);
        ucet.vyber(100);
        Vklad[] vklady = ucet.getHistorieVkladu();
        Vyber[] vybery = ucet.getHistorieVyberu();
        vklady[0] = null;
        vybery[0] = null;
        assertEquals(1000, ucet.getHistorieVkladu()[0].getKolik());
        assertEquals(100, ucet.getHistorieVyberu()[0].getKolik());
        assertNotSame(ucet.getHistorieVkladu(), ucet.getHistorieVkladu());
    }

    @Test
    void historiePoradi() throws Exception {
        ucet.vklad(1);
        ucet.vklad(2);
        ucet.vklad(3);
        int[] castky = java.util.Arrays.stream(ucet.getHistorieVkladu()).mapToInt(Vklad::getKolik).toArray();
        assertArrayEquals(new int[]{1, 2, 3}, castky);
    }

    // ---------------- Vklad / Vyber ----------------

    @Test
    void vkladZaznam() {
        LocalDateTime d = LocalDateTime.of(2026, 1, 1, 12, 0);
        Vklad v = new Vklad(d, 10);
        assertEquals(d, v.getDatum());
        assertEquals(10, v.getKolik());
        assertThrows(IllegalArgumentException.class, () -> new Vklad(d, 0));
        assertThrows(NullPointerException.class, () -> new Vklad(null, 10));
    }

    @Test
    void vyberZaznam() {
        LocalDateTime d = LocalDateTime.of(2026, 1, 1, 12, 0);
        Vyber v = new Vyber(d, 20);
        assertEquals(d, v.getDatum());
        assertEquals(20, v.getKolik());
        assertThrows(IllegalArgumentException.class, () -> new Vyber(d, -1));
        assertThrows(NullPointerException.class, () -> new Vyber(null, 20));
    }
}
