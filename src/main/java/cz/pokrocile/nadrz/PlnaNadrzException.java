package cz.pokrocile.nadrz;

/**
 * Výjimka generovaná, pokud do nádrže nelze přidat požadované množství,
 * protože by byla překročena její kapacita.
 */
public class PlnaNadrzException extends Exception {

    /**
     * @param zprava popis chyby
     */
    public PlnaNadrzException(String zprava) {
        super(zprava);
    }
}
