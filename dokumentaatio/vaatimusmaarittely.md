# Vaatimusmäärittely

### Sovelluksen tarkoitus

Sovellus on variaatio suositusta Tetris-pelistä. Pelissä kerätään pisteitä järjestelemällä palikoita kokonaisiksi vaakasuoriksi riveiksi. Kokonaiset rivit poistuvat ja niiden yläpuolella olevat mahdolliset rivit putoavat alaspäin. Enemmän pisteitä saa, mikäli saa täytettyä useamman rivin samaan aikaan.

Palikat putoavat jatkuvasti alaspäin tietyllä nopeudella. Palikoiden putoamisnopeus määräytyy valitun vaikeustason mukaan (easy, moderate, hard).

Viisi parasta pelitulosta pääsevät leaderboardiin.

### Käyttäjät

Pelissä on vain yhdenlaisia käyttäjärooleja, eli pelaajia.

### Käyttöliittymäluonnos

Sovellus koostuu neljästä eri näkymästä

<src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/kayttoliittymaluonnos.jpg">

Sovellus aukeaa alkuvalikkoon, josta voidaan lukea ohjeet ja käynnistää peli. Ennen pelin käynnistystä valitaan vaikeustaso liukuvalikosta. Näkymästä voidaan myös siirtyä tarkastelemaan leaderboardia.

Pelinäkymässä on itse pelialueen lisäksi inforuutu, josta näkyy mm. kuluvan pelin pistetilanne ja pelin paras tulos.

Pelin päättyessä pelaajan pisteistä riippuen pääsee joko syöttämään oman nimimerkin leaderboardiin erillisessä HighScore -näkymässä tai sitten Leaderboard -näkymä viiteen parhaaseen tulokseen aukeaa suoraan.

Leaderboard -näkymästä siirrytään takaisin alkuvalikkoon nappia painamalla.

### Sovelluksen toiminnallisuudet

#### Alkuvalikko

- Pelaaja näkee pelin logon ja ohjeistuksen **(tehty vko 7)**
- Pelaaja valitsee vaikeustason liukuvalikosta (easy, moderate, hard) **(tehty vko 7)**
- Pelaaja voi aloittaa pelin nappia painamalla (Start game) **(tehty vko 7)**
- Pelaaja voi siirtyä tarkastelemaan leaderboardia nappia painamalla (Leaderboard) **(tehty vko 7)**

#### Ydintoiminnallisuudet (varsinainen pelinäkymä)

- Pelissä on seitsemän erimuotoista palikkaa **(tehty vko3)**
- Palikat ilmestyvät yksitellen satunnaisessa järjestyksessä pelialueen yläreunaan, josta ne alkavat pudota kohtisuoraan alaspäin tietyllä nopeudella **(tehty vko3)**
  - Palikoiden määrää ei ole rajattu **(tehty vko3)**
- Pelaaja voi liikuttaa putoavaa palikkaa vasemmalle ja oikealle sekä kääntää sitä 90 astetta kerrallaan **(liikutus vasemmalle ja oikealle tehty vko4, kääntäminen tehty vko5)**
  - Palikoita ei voi liikuttaa toisten palikoiden eikä pelialueen reunojen päälle **(tehty vko4)**
- Pelaaja voi pudottaa palikan näppämistön komennolla suoraan paikoilleen eli kunnes se osuu pelialueen alareunaan tai toisen palikan (tai sen osan) yläreunaan **(tehty vko6)**
- Kun palikka tippuu pelialueen alareunaan tai osuu toisen palikan (tai sen osan) yläreunaan, palikka jäätyy paikoilleen ja seuraava palikka alkaa pudota pelialueen yläreunasta **(tehty vko4)**
- Kun pelaaja on saanut muodostettua palikoista kokonaisen vaakasuoran rivin, rivi poistuu **(tehty vko6)**
  - Kaikki poistuneen rivin yläpuolella olleet rivit siirtyvät samalla alaspäin **(tehty vko6)**
- Poistuvat rivit kerryttävät pelaajalle pisteitä **(tehty vko6)**
  - Mitä useamman rivin pelaaja saa poistettua kerralla, sitä enemmän pisteitä kertyy **(tehty vko6)**
  - Mitä kovemman vaikeustason pelaaja on valinnut, sitä enemmän pisteitä kertyy **(tehty vko 7)**
  - Pisteet päivittyvät reaaliajassa pelinäkymän oikeaan reunaan **(tehty vko 7)**
  - Pelinäkymän oikeassa reunassa on myös pelin highscore eli paras tulos **(tehty vko 7)**
- Pelaajan on mahdollista pitää tauko pelaamisesta (pause-toiminto) **(tehty vko 7)**
- Peli päättyy, kun pelaaja ei saa pidettyä palikoita enää pelialueella, eli kun palikka tai sen osa jää pelialueen yläreunan yläpuolelle **(tehty vko4)**

#### Leaderboard- ja HighScore -näkymät

- Kun peli päättyy, pelaaja näkee, riittävätkö hänen pisteensä parhaan viiden pelaajan joukkoon pääsemiseen **(tehty vko 7)**
- Mikäli pisteet riittävät, pelaaja pääsee kirjoittamaan nimimerkkinsä leaderboard-listaan (HighScore -näkymässä) **(tehty vko 7)**
- Mikäli pisteet eivät riitä, pelaaja näkee suoraan leaderboardin **(tehty vko 7)**

#### Jatkokehitysideoita

- Palikan putoamisnopeus kiihtyy siksi aikaa, kun pelaaja pitää komennon antavaa painiketta painettuna
- Pelialueen viereen lisätään ruutu, joka näyttää seuraavan jonossa odottavan palikan etukäteen
- Palikoiden väri muuttuu sen mukaan, mitä lähempänä pelaaja on highscorea, esimerkiksi:
  - pisteitä alle 50% parhaasta tuloksesta: värinä punainen
  - pisteitä 51-75% parhaasta tuloksesta: värinä vaaleanpunainen
  - pisteitä 76-100% parhaasta tuloksesta: värinä vaaleanvihreä
  - pisteitä yli 100% parhaasta tuloksesta: värinä vihreä
