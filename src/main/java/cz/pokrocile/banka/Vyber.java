package cz.pokrocile.banka;

import java.time.LocalDateTime;

/**
 * Záznam o výběru z účtu.
 */
public class Vyber extends Transakce {

    /**
     * @param datum datum a čas výběru
     * @param kolik vybraná částka (&gt; 0)
     */
    public Vyber(LocalDateTime datum, int kolik) {
        super(datum, kolik);
    }
}
