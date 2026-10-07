package cz.pokrocile.banka;

/**
 * Rozhraní bankovního účtu.
 */
public interface IBankovniUcet {

    /**
     * @return číslo účtu
     */
    String getCisloUctu();

    /**
     * @return aktuální zůstatek účtu
     */
    int getAktualniStav();

    /**
     * Vloží částku na účet a zaznamená vklad do historie.
     *
     * @param castka vkládaná částka (&gt; 0)
     * @throws IllegalArgumentException pokud je částka &lt;= 0
     */
    void vklad(int castka);

    /**
     * Vybere částku z účtu a zaznamená výběr do historie.
     * Při chybě se stav účtu ani historie nemění.
     *
     * @param castka vybíraná částka (&gt; 0)
     * @throws NedostatekProstredkuException pokud je částka vyšší než zůstatek
     * @throws PrekrocenLimitException       pokud by byl překročen denní limit
     * @throws IllegalArgumentException      pokud je částka &lt;= 0
     */
    void vyber(int castka) throws NedostatekProstredkuException, PrekrocenLimitException;

    /**
     * @return kopie historie vkladů (od nejstaršího)
     */
    Vklad[] getHistorieVkladu();

    /**
     * @return kopie historie výběrů (od nejstaršího)
     */
    Vyber[] getHistorieVyberu();
}
