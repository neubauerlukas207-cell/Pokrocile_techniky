package cz.pokrocile.banka;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Společný předek pohybů na účtu (vkladů a výběrů).
 * Objekt je neměnný (immutable).
 */
public abstract class Transakce {

    /** Datum a čas provedení transakce. */
    protected final LocalDateTime datum;
    /** Částka transakce (vždy kladná). */
    protected final int kolik;

    /**
     * @param datum datum a čas provedení transakce, nesmí být {@code null}
     * @param kolik částka transakce, musí být &gt; 0
     * @throws IllegalArgumentException pokud je částka &lt;= 0
     * @throws NullPointerException     pokud je datum {@code null}
     */
    protected Transakce(LocalDateTime datum, int kolik) {
        if (kolik <= 0) {
            throw new IllegalArgumentException("Částka musí být kladná: " + kolik);
        }
        this.datum = Objects.requireNonNull(datum, "datum");
        this.kolik = kolik;
    }

    /**
     * @return datum a čas provedení transakce
     */
    public LocalDateTime getDatum() {
        return datum;
    }

    /**
     * @return částka transakce
     */
    public int getKolik() {
        return kolik;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[" + datum + ", " + kolik + "]";
    }
}
