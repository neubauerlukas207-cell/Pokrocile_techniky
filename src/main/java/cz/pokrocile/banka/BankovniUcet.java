package cz.pokrocile.banka;

import java.time.Clock;

/**
 * Bankovní účet s historií vkladů a výběrů a s denním limitem pro výběr.
 */
public class BankovniUcet implements IBankovniUcet {

    /** Číslo účtu. */
    protected String cisloUctu;
    /** Aktuální zůstatek. */
    protected int aktualniStav;
    /** Nastavení účtu (denní limit). */
    protected BankovniUcetNastaveni nastaveni;
    /** Hodiny, podle kterých se určuje datum transakcí (kvůli testovatelnosti). */
    protected Clock hodiny;

    /**
     * Vytvoří účet s nulovým zůstatkem, používá systémové hodiny.
     *
     * @param cisloUctu číslo účtu
     * @param nastaveni nastavení účtu
     */
    public BankovniUcet(String cisloUctu, BankovniUcetNastaveni nastaveni) {
        this(cisloUctu, nastaveni, Clock.systemDefaultZone());
    }

    /**
     * Vytvoří účet s nulovým zůstatkem a zadanými hodinami.
     *
     * @param cisloUctu číslo účtu
     * @param nastaveni nastavení účtu
     * @param hodiny    zdroj aktuálního času
     */
    public BankovniUcet(String cisloUctu, BankovniUcetNastaveni nastaveni, Clock hodiny) {
        // TODO
    }

    @Override
    public String getCisloUctu() {
        return null; // TODO
    }

    @Override
    public int getAktualniStav() {
        return -1; // TODO: zatím invalidní hodnota
    }

    /**
     * @return nastavení účtu
     */
    public BankovniUcetNastaveni getNastaveni() {
        return null; // TODO
    }

    @Override
    public void vklad(int castka) {
        // TODO
    }

    @Override
    public void vyber(int castka) throws NedostatekProstredkuException, PrekrocenLimitException {
        // TODO
    }

    @Override
    public Vklad[] getHistorieVkladu() {
        return new Vklad[0]; // TODO
    }

    @Override
    public Vyber[] getHistorieVyberu() {
        return new Vyber[0]; // TODO
    }
}
