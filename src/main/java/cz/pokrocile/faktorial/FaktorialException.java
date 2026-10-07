package cz.pokrocile.faktorial;

/**
 * Výjimka generovaná při výpočtu faktoriálu z neplatného vstupu
 * (záporné číslo nebo číslo, jehož faktoriál přeteče rozsah typu {@code long}).
 */
public class FaktorialException extends Exception {

    /** Hodnota, pro kterou výpočet selhal. */
    private final int vstup;

    /**
     * Vytvoří výjimku.
     *
     * @param zprava popis chyby
     * @param vstup  neplatná vstupní hodnota
     */
    public FaktorialException(String zprava, int vstup) {
        super(zprava);
        this.vstup = vstup;
    }

    /**
     * @return neplatná vstupní hodnota, která výjimku způsobila
     */
    public int getVstup() {
        return vstup;
    }
}
