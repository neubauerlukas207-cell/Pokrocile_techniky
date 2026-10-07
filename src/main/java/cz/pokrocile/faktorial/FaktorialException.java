package cz.pokrocile.faktorial;

/**
 * Vlastní výjimka generovaná při výpočtu faktoriálu z chybného vstupního čísla.
 */
public class FaktorialException extends Exception {

    /**
     * @param zprava popis chyby
     */
    public FaktorialException(String zprava) {
        super(zprava);
    }
}
