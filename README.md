# Tetris

Sovellus on variaatio suositusta Tetris-pelistä. Pelissä kerätään pisteitä järjestelemällä palikoita kokonaisiksi vaakasuoriksi riveiksi. Kokonaiset rivit poistuvat ja niiden yläpuolella olevat mahdolliset rivit putoavat alaspäin. Enemmän pisteitä saa, mikäli saa täytettyä useamman rivin samaan aikaan.

Palikat putoavat jatkuvasti alaspäin tietyllä nopeudella. Palikoiden putoamisnopeus määräytyy valitun vaikeustason mukaan (easy, moderate, hard).

Viisi parasta pelitulosta pääsevät leaderboardiin.

## Dokumentaatio

[Käyttöohje](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md)

[Vaatimusmäärittely](https://github.com/marcestus/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testausdokumentti](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/testausdokumentti.md)

[Työaikakirjanpito](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/viikko5)

[Viikko 6](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/viikko6)

[Loppupalautus](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/loppupalautus)

## Komentorivitoiminnot

### Testaus

Sovelluksen testit voidaan suorittaa komennolla **mvn test**

Testikattavuusraportti luodaan puolestaan komennolla **mvn jacoco:report**

Selaimeen avattava raportti löytyy *'target/site/jacoco/index.html'*

### Suoritettavan jarin generointi

Suoritettava jar-tiedosto generoidaan komennolla **mvn package**

Sovellus generoituu hakemistoon *'target/'*

Sovelluksen voi suorittaa komennolla **java -jar Tetris-1.0-SNAPSHOT.jar**

Generoinnin voi purkaa komennolla **mvn clean**, joka tyhjentää koko *'target/'* -hakemiston

### Ohjelman suorittaminen komentoriviltä

Komento **mvn compile exec:java -Dexec.mainClass=tetris.main.Main** suorittaa ohjelman komentorivillä

### Checkstyle

Checkstyle -tarkistukset on määritelty sovelluksen juuressa sijaitsevassa *'checkstyle.xml'* -tiedostossa

Tarkistukset suoritetaan komennolla **mvn jxr:jxr checkstyle:checkstyle**

Selaimeen avattava raportti löytyy *'target/site/checstyle.html'*

### JavaDoc 

JavaDoc luodaan komenolla **mvn javadoc:javadoc**

Selaimeen avattava JavaDoc -dokumentointi löytyy *'target/site/apidocs/index.html'*
