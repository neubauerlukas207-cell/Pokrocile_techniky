package cz.pokrocile;

import cz.pokrocile.banka.BankovniUcet;
import cz.pokrocile.banka.BankovniUcetNastaveni;
import cz.pokrocile.banka.IBankovniUcet;
import cz.pokrocile.banka.NedostatekProstredkuException;
import cz.pokrocile.banka.PrekrocenLimitException;
import cz.pokrocile.banka.Vyber;
import cz.pokrocile.faktorial.CalcFaktorial;
import cz.pokrocile.faktorial.FaktorialException;
import cz.pokrocile.nadrz.INadrz;
import cz.pokrocile.nadrz.Nadrz;
import cz.pokrocile.nadrz.PlnaNadrzException;
import cz.pokrocile.nadrz.PrazdnaNadrzException;
import cz.pokrocile.nastroje.ToolCisla;

/**
 * Ukázkové (smoke) spuštění všech částí úlohy.
 */
public final class Main {

    private Main() {
    }

    /**
     * @param args nepoužívá se
     */
    public static void main(String[] args) {
        System.out.println("== i) compare_int ==");
        System.out.println("compare_int(1, 2)  = " + ToolCisla.compare_int(1, 2));
        System.out.println("compare_int(2, 1)  = " + ToolCisla.compare_int(2, 1));
        System.out.println("compare_int(3, 3)  = " + ToolCisla.compare_int(3, 3));

        System.out.println("== ii) faktorial ==");
        System.out.println("calc1(5)  = " + CalcFaktorial.calc1(5));
        System.out.println("calc1(-3) = " + CalcFaktorial.calc1(-3));
        try {
            System.out.println("calc2(0)  = " + CalcFaktorial.calc2(0));
            CalcFaktorial.calc2(-3);
        } catch (FaktorialException e) {
            System.out.println("calc2(-3) -> FaktorialException: " + e.getMessage());
        }

        System.out.println("== iii) nadrz ==");
        INadrz nadrz = new Nadrz(100);
        try {
            nadrz.pridej(70);
            nadrz.odeber(20);
            System.out.println("stav po +70 -20: " + nadrz.getStav() + "/" + nadrz.getKapacita());
            nadrz.pridej(60);
        } catch (PlnaNadrzException e) {
            System.out.println("PlnaNadrzException: " + e.getMessage());
        } catch (PrazdnaNadrzException e) {
            System.out.println("PrazdnaNadrzException: " + e.getMessage());
        }
        try {
            nadrz.odeber(51);
        } catch (PrazdnaNadrzException e) {
            System.out.println("PrazdnaNadrzException: " + e.getMessage());
        }

        System.out.println("== iv) bankovni ucet ==");
        IBankovniUcet ucet = new BankovniUcet("123456789/0100", new BankovniUcetNastaveni(1000));
        ucet.vklad(1500);
        int[] pokusy = {600, 500, 400, 2000};
        for (int castka : pokusy) {
            try {
                ucet.vyber(castka);
                System.out.println("vyber " + castka + " OK, zustatek " + ucet.getAktualniStav());
            } catch (NedostatekProstredkuException e) {
                System.out.println("vyber " + castka + " ZAMITNUT [nedostatek prostredku]: " + e.getMessage());
            } catch (PrekrocenLimitException e) {
                System.out.println("vyber " + castka + " ZAMITNUT [prekrocen denni limit]: " + e.getMessage());
            }
        }
        System.out.println("vklady: " + ucet.getHistorieVkladu().length + ", vybery: " + ucet.getHistorieVyberu().length);
        for (Vyber v : ucet.getHistorieVyberu()) {
            System.out.println("  " + v);
        }
    }
}
