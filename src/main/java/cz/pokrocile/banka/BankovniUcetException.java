package cz.pokrocile.banka;

/**
 * Společný předek (vlastní výjimka) všech chyb bankovního účtu.
 * <p>
 * Kromě textového popisu nese i {@link TypChyby}, takže volající může
 * typ chybového stavu zjistit buď podle třídy výjimky, nebo pomocí
 * {@link #getTypChyby()}.
 */
public abstract class BankovniUcetException extends Exception {

    /** Typ chybového stavu. */
    private final TypChyby typChyby;

    /**
     * @param zprava   popis chyby
     * @param typChyby typ chybového stavu
     */
    protected BankovniUcetException(String zprava, TypChyby typChyby) {
        super(zprava);
        this.typChyby = typChyby;
    }

    /**
     * @return typ chybového stavu
     */
    public TypChyby getTypChyby() {
        return typChyby;
    }
}
