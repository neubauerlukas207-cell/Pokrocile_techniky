package cz.pokrocile.faktorial;

/**
 * Výpočet faktoriálu.
 * <p>
 * Java nemá typ {@code unsigned int}, proto se vrací {@code long}.
 * Největší vstup, jehož faktoriál se do {@code long} vejde, je {@value #MAX_VSTUP}.
 */
public final class CalcFaktorial {

    /** Nejvyšší vstup, pro který výsledek nepřeteče rozsah {@code long}. */
    public static final int MAX_VSTUP = 20;

    /** Návratová hodnota {@link #calc1(int)} pro neplatný vstup. */
    public static final long CHYBA = -1;

    private CalcFaktorial() {
    }

    /**
     * Vypočte faktoriál; chybu signalizuje návratovou hodnotou.
     *
     * @param c vstupní číslo
     * @return {@code c!}, nebo {@link #CHYBA} ({@code -1}) pokud je {@code c < 0}
     *         nebo {@code c > MAX_VSTUP}
     */
    public static long calc1(int c) {
        return 0; // TODO: zatím invalidní hodnota
    }

    /**
     * Vypočte faktoriál; chybu signalizuje výjimkou.
     *
     * @param c vstupní číslo
     * @return {@code c!}
     * @throws FaktorialException pokud je {@code c < 0} nebo {@code c > MAX_VSTUP}
     */
    public static long calc2(int c) throws FaktorialException {
        return 0; // TODO: zatím invalidní hodnota
    }
}
