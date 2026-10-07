package cz.pokrocile.banka;

/**
 * Strategie ověření denního limitu pro výběr.
 * <p>
 * Díky rozhraní lze v budoucnu snadno doplnit jiné způsoby ověření
 * (např. týdenní limit, limit podle typu klienta, ...).
 */
public interface OvereniLimitu {

    /**
     * Ověří, zda lze vybrat zadanou částku s ohledem na dnešní výběry.
     *
     * @param castka         požadovaná částka výběru
     * @param historieVyberu částky výběrů provedených v daný den
     *                       ({@code null} je považováno za prázdné pole)
     * @return {@code true}, pokud součet dnešních výběrů a požadované částky
     *         nepřekročí limit, jinak {@code false}
     */
    boolean verifyDenniLimit(int castka, int[] historieVyberu);
}
