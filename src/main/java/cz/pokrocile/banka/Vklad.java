package cz.pokrocile.banka;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Záznam o vkladu na účet (neměnný objekt).
 */
public class Vklad {

    /** Datum a čas vkladu. */
    protected final LocalDateTime datum;
    /** Vložená částka. */
    protected final int kolik;

    /**
     * @param datum datum a čas vkladu, nesmí být {@code null}
     * @param kolik vložená částka (&gt; 0)
     * @throws IllegalArgumentException pokud je částka &lt;= 0
     * @throws NullPointerException     pokud je datum {@code null}
     */
    public Vklad(LocalDateTime datum, int kolik) {
        if (kolik <= 0) {
            throw new IllegalArgumentException("Částka musí být kladná: " + kolik);
        }
        this.datum = Objects.requireNonNull(datum, "datum");
        this.kolik = kolik;
    }

    /**
     * @return datum a čas vkladu
     */
    public LocalDateTime getDatum() {
        return datum;
    }

    /**
     * @return vložená částka
     */
    public int getKolik() {
        return kolik;
    }

    @Override
    public String toString() {
        return "Vklad[" + datum + ", " + kolik + "]";
    }
}
