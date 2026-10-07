package cz.pokrocile.banka;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Bankovní účet s číslem účtu, aktuálním zůstatkem, historií vkladů a výběrů
 * a s maximálním denním limitem pro výběr.
 */
public class BankovniUcet {

    /** Číslo účtu. */
    protected String cisloUctu;
    /** Aktuální zůstatek. */
    protected int aktualniStav;
    /** Nastavení účtu (denní limit pro výběr). */
    protected BankovniUcetNastaveni nastaveni;
    /** Historie vkladů (od nejstaršího). */
    protected List<Vklad> historieVkladu = new ArrayList<>();
    /** Historie výběrů (od nejstaršího). */
    protected List<Vyber> historieVyberu = new ArrayList<>();

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

    /**
     * Vloží částku na účet a zaznamená vklad do historie.
     *
     * @param castka vkládaná částka (&gt; 0)
     * @throws IllegalArgumentException pokud částka není kladná
     */
    public void vklad(int castka) {
        overKladnou(castka);
        aktualniStav = Math.addExact(aktualniStav, castka);
        historieVkladu.add(new Vklad(LocalDateTime.now(), castka));
    }

    /**
     * Vybere částku z účtu a zaznamená výběr do historie.
     * Při chybě se zůstatek ani historie nemění.
     *
     * @param castka vybíraná částka (&gt; 0)
     * @throws NedostatekProstredkuException pokud je částka vyšší než aktuální zůstatek
     * @throws PrekrocenLimitException       pokud by byl překročen denní limit pro výběr
     * @throws IllegalArgumentException      pokud částka není kladná
     */
    public void vyber(int castka) throws NedostatekProstredkuException, PrekrocenLimitException {
        overKladnou(castka);
        if (castka > aktualniStav) {
            throw new NedostatekProstredkuException(
                    "Nedostatek prostředků: požadováno " + castka + ", zůstatek " + aktualniStav);
        }
        LocalDateTime ted = LocalDateTime.now();
        if (!nastaveni.verifyDenniLimit(castka, vyberyZeDne(ted.toLocalDate()))) {
            throw new PrekrocenLimitException(
                    "Překročen denní limit " + nastaveni.getDenniLimit() + " při výběru " + castka);
        }
        aktualniStav -= castka;
        historieVyberu.add(new Vyber(ted, castka));
    }

    /**
     * @return historie vkladů (kopie, od nejstaršího)
     */
    public Vklad[] getHistorieVkladu() {
        return historieVkladu.toArray(new Vklad[0]);
    }

    /**
     * @return historie výběrů (kopie, od nejstaršího)
     */
    public Vyber[] getHistorieVyberu() {
        return historieVyberu.toArray(new Vyber[0]);
    }

    /**
     * @param den den, pro který se výběry hledají
     * @return částky všech výběrů provedených v zadaný den
     */
    private int[] vyberyZeDne(LocalDate den) {
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
}
