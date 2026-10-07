package cz.pokrocile.banka;

/**
 * Vlastní výjimka – chybový stav „nedostatek finančních prostředků“.
 * Generuje se při pokusu vybrat více, než je aktuální zůstatek účtu.
 */
public class NedostatekProstredkuException extends Exception {

    /**
     * @param zprava popis chyby
     */
    public NedostatekProstredkuException(String zprava) {
        super(zprava);
    }
}
