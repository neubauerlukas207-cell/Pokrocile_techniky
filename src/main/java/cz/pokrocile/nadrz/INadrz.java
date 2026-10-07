package cz.pokrocile.nadrz;

/**
 * Rozhraní nádrže s omezenou kapacitou.
 * <p>
 * Stav nádrže je vždy v intervalu {@code <0, kapacita>}.
 */
public interface INadrz {

    /**
     * Přidá do nádrže zadané množství.
     *
     * @param mnozstvi přidávané množství (musí být &gt;= 0)
     * @throws PlnaNadrzException       pokud by přidáním byla překročena kapacita;
     *                                  stav nádrže zůstane nezměněn
     * @throws IllegalArgumentException pokud je množství záporné
     */
    void pridej(int mnozstvi) throws PlnaNadrzException;

    /**
     * Odebere z nádrže zadané množství.
     *
     * @param mnozstvi odebírané množství (musí být &gt;= 0)
     * @throws PrazdnaNadrzException    pokud v nádrži není dostatek obsahu;
     *                                  stav nádrže zůstane nezměněn
     * @throws IllegalArgumentException pokud je množství záporné
     */
    void odeber(int mnozstvi) throws PrazdnaNadrzException;

    /**
     * @return aktuální množství v nádrži
     */
    int getStav();

    /**
     * @return maximální kapacita nádrže
     */
    int getKapacita();
}
