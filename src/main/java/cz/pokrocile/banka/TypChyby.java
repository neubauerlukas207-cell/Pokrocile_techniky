package cz.pokrocile.banka;

/**
 * Typ chybového stavu při výběru z bankovního účtu.
 */
public enum TypChyby {
    /** Na účtu není dostatek finančních prostředků. */
    NEDOSTATEK_PROSTREDKU,
    /** Výběrem by byl překročen maximální denní limit. */
    PREKROCEN_DENNI_LIMIT
}
