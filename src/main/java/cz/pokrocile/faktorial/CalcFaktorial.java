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
        if (!jePlatnyVstup(c)) {
            return CHYBA;
        }
        return vypocet(c);
    }

    /**
     * Vypočte faktoriál; chybu signalizuje výjimkou.
     *
     * @param c vstupní číslo
     * @return {@code c!}
     * @throws FaktorialException pokud je {@code c < 0} nebo {@code c > MAX_VSTUP}
     */
    public static long calc2(int c) throws FaktorialException {
        if (c < 0) {
            throw new FaktorialException("Faktoriál záporného čísla není definován: " + c, c);
        }
        if (c > MAX_VSTUP) {
            throw new FaktorialException("Faktoriál " + c + " přesahuje rozsah typu long", c);
        }
        return vypocet(c);
    }

    /**
     * @param c vstupní číslo
     * @return {@code true}, pokud {@code 0 <= c <= MAX_VSTUP}
     */
    private static boolean jePlatnyVstup(int c) {
        return c >= 0 && c <= MAX_VSTUP;
    }

    /**
     * Vlastní výpočet {@code c!} pro již ověřený vstup (iterativně, 0! = 1).
     *
     * @param c platný vstup
     * @return {@code c!}
     */
    private static long vypocet(int c) {
        long vysledek = 1;
        for (int i = 2; i <= c; i++) {
            vysledek *= i;
        }
        return vysledek;
    }
}
