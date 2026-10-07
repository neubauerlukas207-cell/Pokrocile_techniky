package cz.pokrocile.banka;

import java.time.LocalDateTime;

/**
 * Záznam o vkladu na účet.
 */
public class Vklad extends Transakce {

    /**
     * @param datum datum a čas vkladu
     * @param kolik vložená částka (&gt; 0)
     */
    public Vklad(LocalDateTime datum, int kolik) {
        super(datum, kolik);
    }
}
