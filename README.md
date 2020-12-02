# Tetris

Sovellus on variaatio suositusta Tetris-pelistä. Pelissä kerätään pisteitä järjestelemällä palikoita kokonaisiksi vaakasuoriksi riveiksi. Kun kokonainen rivi saadaan muodostettua, rivi poistuu ja yläpuolella olevat rivit putoavat yhden alaspäin. Enemmän pisteitä saa, mikäli saa täytettyä useamman rivin samaan aikaan.

Palikat putoavat jatkuvasti alaspäin tietyllä nopeudella. Mitä enemmän pisteitä kertyy, sitä nopeampi on palikoiden putoamisnopeus.

**Huom. Ohjelma on vasta osittain valmis. Tällä hetkellä esimerkiksi pelialueen yläreunaan generoituvia palikoita ei voi vielä kääntää eivätkä täydet rivit poistu.**

## Dokumentaatio

[Vaatimusmäärittely](https://github.com/marcestus/ot-harjoitustyo/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuurikuvaus](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md)

[Työaikakirjanpito](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/tuntikirjanpito.md)

## Releaset

[Viikko 5](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/viikko5)

## Komentorivitoiminnot

### Testaus

Testit suoritetaan komennolla **mvn test**

Testikattavuusraportti luodaan komennolla **mvn jacoco:report**, jonka jälkeen tiedoston *'target/site/jacoco/index.html'* voi avata selaimeen

### Suoritettavan jarin generointi

Komento **mvn package** generoi hakemistoon *'target/'* suoritettavan jar-tiedoston *'Tetris-1.0-SNAPSHOT.jar'*

Komento **mvn clean** tyhjentää target-tiedoston

### Ohjelman suorittaminen komentoriviltä

Komento **mvn compile exec:java -Dexec.mainClass=tetris.main.Main** suorittaa ohjelman komentorivillä

### Checkstyle

Tiedostoon checkstyle.xml määritellyt tarkistukset suoritetaan komennolla **mvn jxr:jxr checkstyle:checkstyle** ja mahdollisia virheilmoituksia voi tarkastella avamaalla tiedoston '*target/site/checstyle.html'* selaimeen

### JavaDoc 

**Huom. Tätä ei ole vielä aloitettu**

JavaDoc generoidaan komennolla **mvn javadoc:javadoc**, jonka jälkeen tiedoston *'target/site/apidocs/index.html'* voi avata selaimeen
