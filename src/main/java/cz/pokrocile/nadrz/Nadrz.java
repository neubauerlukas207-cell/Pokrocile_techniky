package cz.pokrocile.nadrz;

/**
 * Nádrž s celočíselnou kapacitou a stavem.
 */
public class Nadrz implements INadrz {

    /** Maximální kapacita nádrže. */
    protected int kapacita;
    /** Aktuální množství v nádrži. */
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

    @Override
    public void pridej(int mnozstvi) throws PlnaNadrzException {
        overNezaporne(mnozstvi);
        // porovnání s volným místem místo "stav + mnozstvi" – nehrozí přetečení int
        if (mnozstvi > kapacita - stav) {
            throw new PlnaNadrzException("Nelze přidat " + mnozstvi + ", volné místo je " + (kapacita - stav));
        }
        stav += mnozstvi;
    }

    @Override
    public void odeber(int mnozstvi) throws PrazdnaNadrzException {
        overNezaporne(mnozstvi);
        if (mnozstvi > stav) {
            throw new PrazdnaNadrzException("Nelze odebrat " + mnozstvi + ", v nádrži je " + stav);
        }
        stav -= mnozstvi;
    }

    @Override
    public int getStav() {
        return stav;
    }

    @Override
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

    @Override
    public String toString() {
        return "Nadrz[" + stav + "/" + kapacita + "]";
    }
}
