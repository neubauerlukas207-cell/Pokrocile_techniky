package cz.pokrocile.nastroje;

/**
 * Pomocné funkce pro práci s čísly.
 */
public final class ToolCisla {

    private ToolCisla() {
    }

    /**
     * Komparační funkce dvou celých čísel.
     *
     * @param a první údaj
     * @param b druhý údaj
     * @return {@code -1} pokud {@code a < b}, {@code +1} pokud {@code a > b},
     *         {@code 0} pokud jsou si údaje rovny
     */
    public static int compare_int(int a, int b) {
        // Záměrně se nepoužívá "a - b", které by pro krajní hodnoty přeteklo.
        if (a < b) {
            return -1;
        }
        if (a > b) {
            return 1;
        }
        return 0;
    }
}
