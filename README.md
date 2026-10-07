# Pokročilé techniky – programování proti rozhraní

Java 17+ / Maven / JUnit 5. Projekt lze otevřít přímo v IntelliJ IDEA (*File → Open → pom.xml*).

## Struktura (každá třída / rozhraní v samostatném souboru)

| Balíček | Obsah |
|---|---|
| `cz.pokrocile.nastroje` | `ToolCisla.compare_int(a, b)` → -1 / 0 / +1 |
| `cz.pokrocile.faktorial` | `CalcFaktorial.calc1` (chyba = `-1`), `calc2` (chyba = `FaktorialException`) |
| `cz.pokrocile.nadrz` | rozhraní `INadrz`, třída `Nadrz`, výjimky `PlnaNadrzException`, `PrazdnaNadrzException` |
| `cz.pokrocile.banka` | rozhraní `IBankovniUcet`, `OvereniLimitu`; třídy `BankovniUcet`, `BankovniUcetNastaveni`, `Transakce` → `Vklad`, `Vyber`; vlastní výjimky `BankovniUcetException` → `NedostatekProstredkuException`, `PrekrocenLimitException` + enum `TypChyby` |

## Poznámky k návrhu

- Java nemá `unsigned int` – faktoriál vrací `long`; vstup > 20 by přetekl, proto je také považován za chybný.
- Typ chyby výběru lze zjistit podle třídy výjimky nebo přes `BankovniUcetException.getTypChyby()`.
- `OvereniLimitu` je rozhraní (strategie) – `BankovniUcetNastaveni` ho implementuje součtem dnešních výběrů.
- `BankovniUcet` přijímá volitelně `java.time.Clock`, aby šel v testech simulovat přechod na další den.
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
