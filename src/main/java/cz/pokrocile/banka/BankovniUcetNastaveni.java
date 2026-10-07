package cz.pokrocile.banka;

/**
 * Nastavení bankovního účtu – maximální denní limit pro výběr.
 */
public class BankovniUcetNastaveni implements OvereniLimitu {

    /** Maximální součet výběrů za jeden den. */
    protected int denniLimit;

    /**
     * @param denniLimit maximální denní limit pro výběr (&gt;= 0)
     * @throws IllegalArgumentException pokud je limit záporný
     */
    public BankovniUcetNastaveni(int denniLimit) {
        // TODO
    }

    /**
     * @return maximální denní limit pro výběr
     */
    public int getDenniLimit() {
        return -1; // TODO: zatím invalidní hodnota
    }

    @Override
    public boolean verifyDenniLimit(int castka, int[] historieVyberu) {
        return false; // TODO
    }
}
