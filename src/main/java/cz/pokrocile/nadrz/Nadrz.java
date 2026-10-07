package cz.pokrocile.nadrz;

/**
 * Nádrž s kapacitou a aktuálním stavem.
 * Stav nádrže je vždy v rozsahu 0 až kapacita.
 */
public class Nadrz {

    /** Maximální kapacita nádrže. */
    protected int kapacita;
    /** Aktuální stav (množství v nádrži). */
    protected int stav;

    /**
     * Vytvoří prázdnou nádrž.
     *
     * @param kapacita kapacita nádrže (&gt;= 0)
     * @throws IllegalArgumentException pokud je kapacita záporná
     */
    public Nadrz(int kapacita) {
        if (kapacita < 0) {
            throw new IllegalArgumentException("Kapacita nesmí být záporná: " + kapacita);
        }
        this.kapacita = kapacita;
        this.stav = 0;
    }

    /**
     * Přidá do nádrže zadané množství.
     *
     * @param mnozstvi přidávané množství (&gt;= 0)
     * @throws PlnaNadrzException       pokud by byla překročena kapacita (stav se nezmění)
     * @throws IllegalArgumentException pokud je množství záporné
     */
    public void pridej(int mnozstvi) throws PlnaNadrzException {
        overNezaporne(mnozstvi);
        // porovnání s volným místem místo "stav + mnozstvi" – nehrozí přetečení int
        if (mnozstvi > kapacita - stav) {
            throw new PlnaNadrzException("Nelze přidat " + mnozstvi + ", volné místo je " + (kapacita - stav));
        }
        stav += mnozstvi;
    }

    /**
     * Odebere z nádrže zadané množství.
     *
     * @param mnozstvi odebírané množství (&gt;= 0)
     * @throws PrazdnaNadrzException    pokud v nádrži není dostatek obsahu (stav se nezmění)
     * @throws IllegalArgumentException pokud je množství záporné
     */
    public void odeber(int mnozstvi) throws PrazdnaNadrzException {
        overNezaporne(mnozstvi);
        if (mnozstvi > stav) {
            throw new PrazdnaNadrzException("Nelze odebrat " + mnozstvi + ", v nádrži je " + stav);
        }
        stav -= mnozstvi;
    }

    /**
     * @return aktuální stav nádrže
     */
    public int getStav() {
        return stav;
    }

    /**
     * @return kapacita nádrže
     */
    public int getKapacita() {
        return kapacita;
    }

    /**
     * @param mnozstvi kontrolované množství
     * @throws IllegalArgumentException pokud je množství záporné
     */
    private static void overNezaporne(int mnozstvi) {
        if (mnozstvi < 0) {
            throw new IllegalArgumentException("Množství nesmí být záporné: " + mnozstvi);
        }
    }
}
