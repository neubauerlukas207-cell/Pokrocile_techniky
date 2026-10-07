package cz.pokrocile.banka;

/**
 * Vlastní výjimka – chybový stav „překročen denní limit“.
 * Generuje se, pokud by výběrem byl překročen maximální denní limit pro výběr.
 */
public class PrekrocenLimitException extends Exception {

    /**
     * @param zprava popis chyby
     */
    public PrekrocenLimitException(String zprava) {
        super(zprava);
    }
}
