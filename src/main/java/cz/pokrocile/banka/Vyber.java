package cz.pokrocile.banka;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Záznam o výběru z účtu.
 */
public class Vyber {

    /** Datum a čas výběru. */
    protected final LocalDateTime datum;
    /** Vybraná částka. */
    protected final int kolik;

    /**
     * @param datum datum a čas výběru, nesmí být {@code null}
     * @param kolik vybraná částka (&gt; 0)
     * @throws IllegalArgumentException pokud je částka &lt;= 0
     * @throws NullPointerException     pokud je datum {@code null}
     */
    public Vyber(LocalDateTime datum, int kolik) {
        if (kolik <= 0) {
            throw new IllegalArgumentException("Částka musí být kladná: " + kolik);
        }
        this.datum = Objects.requireNonNull(datum, "datum");
        this.kolik = kolik;
    }

    /**
     * @return datum a čas výběru
     */
    public LocalDateTime getDatum() {
        return datum;
    }

    /**
     * @return vybraná částka
     */
    public int getKolik() {
        return kolik;
    }
}
