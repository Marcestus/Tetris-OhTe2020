# Käyttöohje

Lataa tiedosto [Tetris.jar](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/loppupalautus)

## Konfigurointi: palikoiden putoamisnopeuden ja tietokantojen nimien vaihtaminen

Ohjelman [src/main/resources/tetris](https://github.com/Marcestus/ot-harjoitustyo/tree/master/Tetris/src/main/resources/tetris) -kansiossa on konfigurointitiedosto [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties). Tiedosto määrittelee sovelluksessa ja testeissä käytettävien tietokantojen nimet sekä pelin vaikeustasosta riippuvan palikoiden putoamisenopeuden. Tiedoston parametrejä muuttamalla tietokannat voidaan siis nimetä uudelleen ja pelinopeuksia säädellä mieleisiksi.

Tiedosto sisältää seuraavat rivit, joista neljä ylempää säätelevät tietokantojen nimiä ja kolme alempaa pelinopeutta. Mitä pienempi pelinopeuden arvo, sitä nopeammin palikat putoavat.

```
leaderboardDatabase=leaderboard.db
leaderboardDaoTestDatabase=leaderboardDaoTest.db
leaderboardFullTestDatabase=leaderboardFullTest.db
leaderboardEmptyTestDatabase=leaderboardEmptyTest.db

gameSpeedLevel1=800
gameSpeedLevel2=500
gameSpeedLevel3=200
```

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

java -jar Tetris.jar

## Peliohjeistukseen tutustuminen ja vaikeustason valitseminen

Ohjelma käynnistyy menu -näkymään, joka sisältää pelin ohjeistuksen. Ohjeistuksen alla olevasta liukuvalikosta valitaan pelin vaikeustaso (Easy, Moderate, Hard). Start game -painike aloittaa uuden pelin välittömästi ja Leaderboard -painike vie leaderboard -näkymään.

![MenuScene](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/MenuScene.png)

## Pelaaminen

Pelinäkymä sisältää pelialueen ja pelin kehitystä kuvastavan inforuudun. Inforuudun listaus sisältää leaderboardin parhaan tuloksen (HighScore) sekä nykyisen pelin pistesaldon, poistetut rivit ja vaikeustason.

Pelin voi myös tauottaa SPACE -painikkeella ja se jatkuu painamalla SPACE -painiketta uudestaan.
Peli päättyy, kun aktiivinen vihreä palikka ei pääse enää putoamaan, vaan jää pelialueen yläreunan yläpuolelle.

![GameScene](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/GameScene.png)

## HighScore -tuloksen syöttäminen

Mikäli pisteet riittävät viiden parhaan joukkoon, aukeavaan näkymään voi syöttää oman nimimerkin leaderboardia varten.
OK -painikkeella pääsee leaderboard -näkymään. Jos pisteet eivät riitä leaderboardiin pääsyyn, siirrytään suoraan seuraavaan leaderboard -näkymään.

![HighScoreScene](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/HighScoreScene.png)

## Leaderboard -näkymä ja leaderboardin nollaaminen

Näkymä sisältää viisi parasta pelitulosta. Mikäli kaikki sijat ovat täynnä, leaderboardiin pääsee vain päihittämällä viidennen sijan pistetuloksen.

![LeaderboardScene](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/LeaderboardScene.png)

Ohjelma tallentaa parhaat tulokset tietokantaan. Leaderboardin voi siis nollata poistamalla tietokannan. Tietokanta löytyy generoimisen jälkeen sovelluskansion [juuresta](https://github.com/Marcestus/ot-harjoitustyo/tree/master/Tetris). Tietokannan oletusnimi on *leaderboard.db* ja sen voi vaihtaa yllä olevilla ohjeilla sovelluksen konfigurointitiedostosta. Kun ohjelma käynnistetään poistamisen jälkeen uudestaan, se generoi automaattisesti uuden tyhjän tietokannan samaan paikkaan.

