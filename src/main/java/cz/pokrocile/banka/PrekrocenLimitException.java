package cz.pokrocile.banka;

/**
 * Výjimka generovaná, pokud by výběrem byl překročen maximální denní limit.
 */
public class PrekrocenLimitException extends BankovniUcetException {

    /**
     * @param zprava popis chyby
     */
    public PrekrocenLimitException(String zprava) {
        super(zprava, TypChyby.PREKROCEN_DENNI_LIMIT);
    }
}
