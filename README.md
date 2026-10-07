# Pokročilé techniky – programování proti rozhraní

Java 17+ / Maven / JUnit 5. Projekt lze otevřít přímo v IntelliJ IDEA (*File → Open → pom.xml*).

## Struktura (každá třída / rozhraní v samostatném souboru)

| Balíček | Obsah |
|---|---|
| `cz.pokrocile.nastroje` | `ToolCisla.compare_int(a, b)` → -1 / 0 / +1 |
| `cz.pokrocile.faktorial` | `CalcFaktorial.calc1` (chyba = `-1`), `calc2` (chyba = `FaktorialException`) |
| `cz.pokrocile.nadrz` | rozhraní `INadrz`, třída `Nadrz`, výjimky `PlnaNadrzException`, `PrazdnaNadrzException` |
| `cz.pokrocile.banka` | rozhraní `IBankovniUcet`; třídy `BankovniUcet`, `BankovniUcetNastaveni` (s `verifyDenniLimit`), `Vklad`, `Vyber`; vlastní výjimky `NedostatekProstredkuException`, `PrekrocenLimitException` |

## Poznámky k návrhu

- Java nemá `unsigned int` – faktoriál vrací `int`; vstup > 12 by přetekl rozsah `int`, proto je také považován za chybný (`-1` / `FaktorialException`).
- Typ chybového stavu při výběru je rozlišen třídou výjimky (`NedostatekProstredkuException` / `PrekrocenLimitException`).
- Třída `OvereniLimitu` je v zadání označena „ještě to promyslíme“, proto zatím není vytvořena; denní limit ověřuje `BankovniUcetNastaveni.verifyDenniLimit(int, int[])`.
- `BankovniUcet.ted()` je `protected`, aby testy mohly simulovat přechod na další den.
- Historie se vrací jako kopie pole, zvenku ji nelze měnit.

## Postup (viz historie commitů)

1. Návrh rozhraní + třídy se „prázdnými" metodami vracejícími invalidní hodnoty + funkční testy (testy selhávají).
2. Implementace metod, dokud všechny testy neprojdou.

## Spuštění

```bash
mvn test                                   # jednotkové testy
mvn package && java -jar target/pokrocile-techniky-1.0-SNAPSHOT.jar   # ukázka (smoke test)
mvn javadoc:javadoc                        # dokumentace
```
