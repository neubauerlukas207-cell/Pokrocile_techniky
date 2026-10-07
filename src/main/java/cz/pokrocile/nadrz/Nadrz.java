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
        // TODO
    }

    @Override
    public void pridej(int mnozstvi) throws PlnaNadrzException {
        // TODO
    }

    @Override
    public void odeber(int mnozstvi) throws PrazdnaNadrzException {
        // TODO
    }

    @Override
    public int getStav() {
        return -1; // TODO: zatím invalidní hodnota
    }

    @Override
    public int getKapacita() {
        return -1; // TODO: zatím invalidní hodnota
    }
}
