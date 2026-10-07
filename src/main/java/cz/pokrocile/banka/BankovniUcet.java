package cz.pokrocile.banka;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    /** Historie vkladů (od nejstaršího). */
    protected final List<Vklad> historieVkladu = new ArrayList<>();
    /** Historie výběrů (od nejstaršího). */
    protected final List<Vyber> historieVyberu = new ArrayList<>();

    /**
     * Vytvoří účet s nulovým zůstatkem.
     *
     * @param cisloUctu číslo účtu
     * @param nastaveni nastavení účtu
     * @throws NullPointerException pokud je některý parametr {@code null}
     */
    public BankovniUcet(String cisloUctu, BankovniUcetNastaveni nastaveni) {
        this.cisloUctu = Objects.requireNonNull(cisloUctu, "cisloUctu");
        this.nastaveni = Objects.requireNonNull(nastaveni, "nastaveni");
        this.aktualniStav = 0;
    }

    @Override
    public String getCisloUctu() {
        return cisloUctu;
    }

    @Override
    public int getAktualniStav() {
        return aktualniStav;
    }

    @Override
    public void vklad(int castka) {
        overKladnou(castka);
        aktualniStav = Math.addExact(aktualniStav, castka);
        historieVkladu.add(new Vklad(ted(), castka));
    }

    @Override
    public void vyber(int castka) throws NedostatekProstredkuException, PrekrocenLimitException {
        overKladnou(castka);
        if (castka > aktualniStav) {
            throw new NedostatekProstredkuException(
                    "Nedostatek prostředků: požadováno " + castka + ", zůstatek " + aktualniStav);
        }
        LocalDateTime ted = ted();
        if (!nastaveni.verifyDenniLimit(castka, dnesniVybery(ted.toLocalDate()))) {
            throw new PrekrocenLimitException(
                    "Překročen denní limit " + nastaveni.getDenniLimit() + " při výběru " + castka);
        }
        aktualniStav -= castka;
        historieVyberu.add(new Vyber(ted, castka));
    }

    /**
     * Aktuální datum a čas transakce. Metoda je {@code protected}, aby ji
     * testy mohly překrýt a simulovat přechod na další den.
     *
     * @return aktuální datum a čas
     */
    protected LocalDateTime ted() {
        return LocalDateTime.now();
    }

    /**
     * @param den den, pro který se výběry hledají
     * @return částky všech výběrů provedených v zadaný den
     */
    protected int[] dnesniVybery(LocalDate den) {
        return historieVyberu.stream()
                .filter(v -> v.getDatum().toLocalDate().equals(den))
                .mapToInt(Vyber::getKolik)
                .toArray();
    }

    /**
     * @param castka kontrolovaná částka
     * @throws IllegalArgumentException pokud částka není kladná
     */
    private static void overKladnou(int castka) {
        if (castka <= 0) {
            throw new IllegalArgumentException("Částka musí být kladná: " + castka);
        }
    }

    @Override
    public Vklad[] getHistorieVkladu() {
        return historieVkladu.toArray(new Vklad[0]);
    }

    @Override
    public Vyber[] getHistorieVyberu() {
        return historieVyberu.toArray(new Vyber[0]);
    }
}
