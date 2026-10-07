package cz.pokrocile.banka;

/**
 * Výjimka generovaná při pokusu vybrat více, než je aktuální zůstatek účtu.
 */
public class NedostatekProstredkuException extends BankovniUcetException {

    /**
     * @param zprava popis chyby
     */
    public NedostatekProstredkuException(String zprava) {
        super(zprava, TypChyby.NEDOSTATEK_PROSTREDKU);
    }
}
