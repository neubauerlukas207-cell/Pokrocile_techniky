package cz.pokrocile.nadrz;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Testy nádrže – testuje se proti rozhraní {@link INadrz}.
 */
class NadrzTest {

    private INadrz nadrz;

    @BeforeEach
    void setUp() {
        nadrz = new Nadrz(100);
    }

    @Test
    @DisplayName("Nová nádrž je prázdná a má zadanou kapacitu")
    void novaNadrz() {
        assertEquals(0, nadrz.getStav());
        assertEquals(100, nadrz.getKapacita());
    }

    @Test
    @DisplayName("Zápornou kapacitu nelze vytvořit")
    void zapornaKapacita() {
        assertThrows(IllegalArgumentException.class, () -> new Nadrz(-1));
    }

    @Test
    void pridejZvysiStav() throws PlnaNadrzException {
        nadrz.pridej(30);
        assertEquals(30, nadrz.getStav());
        nadrz.pridej(20);
        assertEquals(50, nadrz.getStav());
    }

    @Test
    @DisplayName("Lze naplnit přesně do kapacity")
    void pridejDoPlna() throws PlnaNadrzException {
        nadrz.pridej(100);
        assertEquals(100, nadrz.getStav());
    }

    @Test
    void pridejNulu() throws PlnaNadrzException {
        nadrz.pridej(0);
        assertEquals(0, nadrz.getStav());
    }

    @Test
    @DisplayName("Přeplnění vyhodí PlnaNadrzException a stav se nezmění")
    void pridejPreplneni() throws PlnaNadrzException {
        nadrz.pridej(90);
        assertThrows(PlnaNadrzException.class, () -> nadrz.pridej(11));
        assertEquals(90, nadrz.getStav());
    }

    @Test
    @DisplayName("Přidání do plné nádrže vyhodí výjimku")
    void pridejDoPlne() throws PlnaNadrzException {
        nadrz.pridej(100);
        assertThrows(PlnaNadrzException.class, () -> nadrz.pridej(1));
        assertEquals(100, nadrz.getStav());
    }

    @Test
    @DisplayName("Obrovské množství nezpůsobí přetečení int")
    void pridejPreteceni() throws PlnaNadrzException {
        nadrz.pridej(50);
        assertThrows(PlnaNadrzException.class, () -> nadrz.pridej(Integer.MAX_VALUE));
        assertEquals(50, nadrz.getStav());
    }

    @Test
    void pridejZaporne() {
        assertThrows(IllegalArgumentException.class, () -> nadrz.pridej(-5));
        assertEquals(0, nadrz.getStav());
    }

    @Test
    void odeberSnizStav() throws Exception {
        nadrz.pridej(80);
        nadrz.odeber(30);
        assertEquals(50, nadrz.getStav());
    }

    @Test
    @DisplayName("Lze odebrat celý obsah")
    void odeberVse() throws Exception {
        nadrz.pridej(80);
        nadrz.odeber(80);
        assertEquals(0, nadrz.getStav());
    }

    @Test
    void odeberNulu() {
        assertDoesNotThrow(() -> nadrz.odeber(0));
        assertEquals(0, nadrz.getStav());
    }

    @Test
    @DisplayName("Odebrání více, než je v nádrži, vyhodí PrazdnaNadrzException a stav se nezmění")
    void odeberVic() throws PlnaNadrzException {
        nadrz.pridej(10);
        assertThrows(PrazdnaNadrzException.class, () -> nadrz.odeber(11));
        assertEquals(10, nadrz.getStav());
    }

    @Test
    void odeberZPrazdne() {
        assertThrows(PrazdnaNadrzException.class, () -> nadrz.odeber(1));
        assertEquals(0, nadrz.getStav());
    }

    @Test
    void odeberZaporne() {
        assertThrows(IllegalArgumentException.class, () -> nadrz.odeber(-1));
        assertEquals(0, nadrz.getStav());
    }

    @Test
    @DisplayName("Nádrž s nulovou kapacitou")
    void nulovaKapacita() {
        INadrz n = new Nadrz(0);
        assertEquals(0, n.getKapacita());
        assertDoesNotThrow(() -> n.pridej(0));
        assertThrows(PlnaNadrzException.class, () -> n.pridej(1));
        assertThrows(PrazdnaNadrzException.class, () -> n.odeber(1));
    }
}
