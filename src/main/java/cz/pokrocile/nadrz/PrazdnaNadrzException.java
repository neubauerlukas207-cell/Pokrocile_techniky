package cz.pokrocile.nadrz;

/**
 * Výjimka generovaná, pokud z nádrže nelze odebrat požadované množství,
 * protože v ní není dostatek obsahu.
 */
public class PrazdnaNadrzException extends Exception {

    /**
     * @param zprava popis chyby
     */
    public PrazdnaNadrzException(String zprava) {
        super(zprava);
    }
}
