package cz.pokrocile.banka;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy třídy {@link BankovniUcet}.
 * Test je ve stejném balíčku, proto může číst chráněný atribut {@code aktualniStav}.
 */
class BankovniUcetTest {

    private BankovniUcet ucet;

    @BeforeEach
    void setUp() {
        ucet = new BankovniUcet("123456789/0100", new BankovniUcetNastaveni(1000));
    }

    @Test
    void novyUcet() {
        assertEquals("123456789/0100", ucet.cisloUctu);
        assertEquals(0, ucet.aktualniStav);
        assertEquals(1000, ucet.nastaveni.getDenniLimit());
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
        LocalDateTime pred = LocalDateTime.now();
        ucet.vklad(500);
        ucet.vklad(250);
        assertEquals(750, ucet.aktualniStav);

        Vklad[] h = ucet.getHistorieVkladu();
        assertEquals(2, h.length);
        assertEquals(500, h[0].getKolik());
        assertEquals(250, h[1].getKolik());
        assertEquals(pred.toLocalDate(), h[0].getDatum().toLocalDate());
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    void vkladNeplatnaCastka() {
        assertThrows(IllegalArgumentException.class, () -> ucet.vklad(0));
        assertThrows(IllegalArgumentException.class, () -> ucet.vklad(-100));
        assertEquals(0, ucet.aktualniStav);
        assertEquals(0, ucet.getHistorieVkladu().length);
    }

    // ---------------- výběr ----------------

    @Test
    void vyberSnizZustatekAZapiseHistorii() throws Exception {
        ucet.vklad(2000);
        ucet.vyber(300);
        assertEquals(1700, ucet.aktualniStav);

        Vyber[] h = ucet.getHistorieVyberu();
        assertEquals(1, h.length);
        assertEquals(300, h[0].getKolik());
        assertEquals(LocalDate.now(), h[0].getDatum().toLocalDate());
    }

    @Test
    @DisplayName("Lze vybrat přesně celý zůstatek")
    void vyberCelyZustatek() throws Exception {
        ucet.vklad(800);
        ucet.vyber(800);
        assertEquals(0, ucet.aktualniStav);
    }

    @Test
    @DisplayName("Nedostatek prostředků – NedostatekProstredkuException, stav beze změny")
    void vyberNedostatek() {
        ucet.vklad(100);
        assertThrows(NedostatekProstredkuException.class, () -> ucet.vyber(101));
        assertEquals(100, ucet.aktualniStav);
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    void vyberZPrazdnehoUctu() {
        assertThrows(NedostatekProstredkuException.class, () -> ucet.vyber(1));
    }

    @Test
    @DisplayName("Překročení denního limitu jedním výběrem")
    void vyberPresLimitNajednou() {
        ucet.vklad(5000);
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(1001));
        assertEquals(5000, ucet.aktualniStav);
        assertEquals(0, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Překročení denního limitu součtem více výběrů")
    void vyberPresLimitPostupne() throws Exception {
        ucet.vklad(5000);
        ucet.vyber(600);
        ucet.vyber(400); // přesně na limit
        assertThrows(PrekrocenLimitException.class, () -> ucet.vyber(1));
        assertEquals(4000, ucet.aktualniStav);
        assertEquals(2, ucet.getHistorieVyberu().length);
    }

    @Test
    @DisplayName("Výběry z předchozího dne se do denního limitu nepočítají")
    void vyberyZVcerejskaSeNepocitaji() throws Exception {
        ucet.vklad(5000);
        ucet.historieVyberu.add(new Vyber(LocalDateTime.now().minusDays(1), 1000));
        ucet.vyber(1000);
        assertEquals(4000, ucet.aktualniStav);
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
        assertEquals(100, ucet.aktualniStav);
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
        ucet.vyber(3);
        ucet.vyber(2);
        int[] vklady = java.util.Arrays.stream(ucet.getHistorieVkladu()).mapToInt(Vklad::getKolik).toArray();
        int[] vybery = java.util.Arrays.stream(ucet.getHistorieVyberu()).mapToInt(Vyber::getKolik).toArray();
        assertArrayEquals(new int[]{1, 2, 3}, vklady);
        assertArrayEquals(new int[]{3, 2}, vybery);
    }
}
