package cz.pokrocile.faktorial;

/**
 * Výpočet faktoriálu: 0! = 1, 1! = 1, n! = n * (n - 1)!.
 * <p>
 * Java nemá typ {@code unsigned int}, proto metody vracejí {@code int}.
 * Faktoriál čísla většího než 12 se do typu {@code int} nevejde,
 * proto je takové číslo také chybným vstupem.
 */
public final class CalcFaktorial {

    private CalcFaktorial() {
    }

    /**
     * První implementace – chybu signalizuje návratovou hodnotou.
     *
     * @param c vstupní číslo
     * @return {@code c!}, nebo {@code -1} v případě chybného vstupního čísla
     *         ({@code c < 0} nebo {@code c > 12})
     */
    public static int calc1(int c) {
        if (c < 0 || c > 12) {
            return -1;
        }
        return vypocet(c);
    }

    /**
     * Druhá implementace – chybu signalizuje výjimkou.
     *
     * @param c vstupní číslo
     * @return {@code c!}
     * @throws FaktorialException v případě chybného vstupního čísla
     *                            ({@code c < 0} nebo {@code c > 12})
     */
    public static int calc2(int c) throws FaktorialException {
        if (c < 0) {
            throw new FaktorialException("Faktoriál záporného čísla není definován: " + c);
        }
        if (c > 12) {
            throw new FaktorialException("Faktoriál čísla " + c + " přesahuje rozsah typu int");
        }
        return vypocet(c);
    }

    /**
     * Vlastní výpočet faktoriálu pro již ověřený vstup.
     *
     * @param c vstupní číslo (0 až 12)
     * @return {@code c!}
     */
    private static int vypocet(int c) {
        int vysledek = 1;
        for (int i = 2; i <= c; i++) {
            vysledek *= i;
        }
        return vysledek;
    }
}
