package cz.pokrocile.banka;

/**
 * Nastavení bankovního účtu – maximální denní limit pro výběr.
 */
public class BankovniUcetNastaveni {

    /** Maximální součet výběrů za jeden den. */
    protected int denniLimit;

    /**
     * @param denniLimit maximální denní limit pro výběr (&gt;= 0)
     * @throws IllegalArgumentException pokud je limit záporný
     */
    public BankovniUcetNastaveni(int denniLimit) {
        if (denniLimit < 0) {
            throw new IllegalArgumentException("Denní limit nesmí být záporný: " + denniLimit);
        }
        this.denniLimit = denniLimit;
    }

    /**
     * @return maximální denní limit pro výběr
     */
    public int getDenniLimit() {
        return denniLimit;
    }

    /**
     * Ověří, zda lze vybrat zadanou částku s ohledem na dnešní výběry.
     *
     * @param castka         požadovaná částka výběru
     * @param historieVyberu částky výběrů provedených v daný den
     *                       ({@code null} je považováno za prázdné pole)
     * @return {@code true}, pokud součet dnešních výběrů a požadované částky
     *         nepřekročí denní limit, jinak {@code false}
     */
    public boolean verifyDenniLimit(int castka, int[] historieVyberu) {
        // součet v long, aby nedošlo k přetečení int
        long soucet = castka;
        if (historieVyberu != null) {
            for (int vyber : historieVyberu) {
                soucet += vyber;
            }
        }
        return soucet <= denniLimit;
    }
}
