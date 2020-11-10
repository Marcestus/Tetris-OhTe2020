# Vaatimusmäärittely

### Sovelluksen tarkoitus

Sovellus on variaatio suositusta *Tetris*-pelistä. Pelissä kerätään pisteitä järjestelemällä itsestään putoavia palikoita kokonaisiksi vaakasuoriksi riveiksi. Kun kokonainen rivi saadaan muodostettua, rivi poistuu ja yläpuolella olevat rivit putoavat yhden alaspäin. Enemmän pisteitä saa, mikäli saa täytettyä useamman rivin samaan aikaan.

Palikat putoavat jatkuvasti alaspäin tietyllä nopeudella. Mitä enemmän pisteitä kertyy, sitä nopeampi on palikoiden putoamisnopeus.

### Käyttäjät

Pelissä on vain yhdenlaisia käyttäjärooleja, eli pelaajia.

### Sovelluksen toiminnallisuudet

#### Alkuvalikko

- Pelaaja näkee pelin logon ja ohjeistuksen
- Pelaaja aloittaa pelin näppäimistön komennolla

#### Pelin ydintoiminnallisuudet

- Pelissä on seitsemän erimuotoista palikkaa
- Palikat ilmestyvät yksitellen satunnaisessa järjestyksessä pelialueen yläreunaan, josta ne alkavat pudota kohtisuoraan alaspäin tietyllä nopeudella
  - Palikoiden määrää ei ole rajattu
- Pelaaja voi liikuttaa putoavaa palikkaa vasemmalle ja oikealle sekä kääntää sitä myötäpäivään 90 astetta kerrallaan
  - Palikoita ei voi liikuttaa toisten palikoiden eikä pelialueen reunojen päälle
- Kun palikka tippuu pelialueen alareunaan tai osuu toisen palikan (tai sen osan) yläreunaan, palikka jäätyy paikoilleen ja seuraava palikka alkaa pudota pelialueen yläreunasta
- Kun pelaaja on saanut muodostettua palikoista kokonaisen vaakasuoran rivin, rivi poistuu
  - Kaikki poistuneen rivin yläpuolella olleet rivit siirtyvät samalla yhden rivin alaspäin
- Poistuvat rivit kerryttävät pelaajalle pisteitä
  - Mitä useamman rivin pelaaja saa poistettua kerralla, sitä enemmän pisteitä kertyy
- Mitä enemmän pisteitä kertyy, sitä nopeampi on palikoiden perus-putoamisnopeus
- Peli päättyy, kun pelaaja ei saa pidettyä palikoita enää pelialueella, eli kun palikka tai sen osa jää pelialueen yläreunan yläpuolelle

#### Leaderboard -näkymä

- Kun peli päättyy, pelaaja näkee, riittävätkö hänen pisteensä parhaan viiden pelaajan joukkoon pääsemiseen
- Mikäli pisteet riittävät, pelaaja pääsee kirjoittamaan nimimerkkinsä leaderboard-listaan (erillinen näkymä)
  - Mikäli pisteet eivät riitä, pelaaja näkee suoraan leaderboard -näkymän

#### Jatkokehitysideat

Pelin perustoiminnallisuuksia voidaan täydentää ajan salliessa seuraavasti

- Pelaaja voi itse kiihdyttää palikan putoamista näppäimistön komennolla kahdella eri tavalla ja niistä voidaan toteuttaa joko vain toinen tai molemmat
    - Mikäli molemmat toteutuisivat, pelityylin valinta tapahtuisi alkuvalikossa
    1. Palikka putoaa pelaajan komennosta välittömästi kohtisuoraan alaspäin paikoilleen eli kunnes se osuu pelialueen alareunaan tai toisen palikan (tai sen osan) yläreunaan
    2. Palikan putoamisnopeus kiihtyy silloin, kun pelaaja pitää komennon antavaa näppäimistön painiketta painettuna
    - Pelityyleille voidaan muodostaa omat leaderboardit
- Pelialueen viereen voidaan lisätä ruutu, joka näyttää seuraavan jonossa odottavan palikan etukäteen
  - Tämän toiminnallisuuden voisi valita alkuvalikossa joko päälle tai pois
  - Pelityyleille voidaan muodostaa omat leaderboardit
- Palikoiden väri määräytyy sen mukaan, mitä lähempänä pelaaja on pelityylinsä parasta pistetulosta
  - pisteitä alle 50% parhaasta tuloksesta: värinä punainen
  - pisteitä 51-75% parhaasta tuloksesta: värinä vaaleanpunainen
  - pisteitä 76-100% parhaasta tuloksesta: värinä vaaleanvihreä
  - pisteitä yli 100% parhaasta tulokseta: värinä vihreä

### Käyttöliittymäluonnos

Sovellus koostuu neljästä eri näkymästä

<img src="https://github.com/Marcestus/ot-harjoitustyo/tree/master/dokumentaatio/kuvat/kayttoliittymaluonnos.jpg" width="750">

Sovellus aukeaa alkuvalikkoon, josta voidaan lukea ohjeet ja käynnistää peli näppäimistökomennolla. Pelinäkymässä on itse pelialueen lisäksi highscore ja omat pisteet sekä mahdollisesti tieto seuraavana jonossa odottavasta palikasta.

Pelin päättyessä pelaajan pisteistä riippuen pääsee joko syöttämään oman nimimerkin leaderboardiin eri näkymässä tai sitten leaderboard -näkymä aukeaa suoraan.

Leaderboard -näkymästä voidaan siirtyä näppäimistökomennoilla joko alkuvalikkoon tai suoraan uuteen peliin.
