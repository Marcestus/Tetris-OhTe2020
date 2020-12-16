# Testausdokumentti

Ohjelmaa on testattu JUnitin avulla sekä yksikkö- että integraatiotasolla. Sovelluksen toimintaa on myös testattu manuaalisesti järjestelmätasolla.

## Yksikkö- ja integraatiotestaus

**Sovelluslogiikka**

Yksikkötason testejä on tehty pääosin sovelluslogiikan perusluokille [Shape](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Shape.java) ja [Score](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Score.java). Näille on luotu omat testitiedostot [ShapeTest](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/test/java/tetris/domain/ShapeTest.java) ja [ScoreTest](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/test/java/tetris/domain/ScoreTest.java). Shape -luokkaan liittyvät yksikkötestit varmistavat, että sovellus luo Tetris-palikat oikean muotoisiksi. Score -luokan yksikkötestit puolestaan varmistavat, että pisteidenlasku ja poistettujen rivien summautuminen toimivat oikein.

Suurin osa sovelluslogiikkaan liittyvistä testeistä on puolestaan tehty integraatiotesteinä, sillä pelilogiikkaa ylläpitävä [TetrisGame](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/TetrisGame.java) -luokka hyödyntää tiiviisti edellä kuvattuja Shape- ja Score -luokkia konstruktori-kutsussaan (luodaan uusi aktiivinen Tetris-palikka ja alustetaan pisteet). Käytännössä kaikki testitiedoston [TetrisGameTest](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/test/java/tetris/domain/TetrisGameTest.java) testit pohjautuvat siis TetrisGame, Shape ja Score -luokkien yhteistyöhön, vaikka testit itsessään sisältävät alustuksen jälkeen pääosin yksikkötestausta. Testit kattavat kaikki [arkkitehtuurikuvauksessa](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/arkkitehtuuri.md#sovelluslogiikka) kuvatut sovelluslogiikan perustoiminnallisuudet.

Koska graafista käyttöliittymää hallinnoivaa [GameView](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/ui/GameView.java) -luokkaa ei testattu, käyttöliittymän kutsut on simuloitu vain siltä osin, kun niitä käsitellään TetrisGame -luokassa. Testit vaikuttavat antavan kuitenkin hyvän kuvan myös käyttöliittymän toimivuudesta, sillä sovelluslogiikka on pyritty eriyttämään mahdollisimman hyvin käyttöliittymästä.

[Leaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Leaderboard.java) -luokkaan liittyvä testitiedosto [LeaderboardTest](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/test/java/tetris/domain/LeaderboardTest.java) sisältää tietokantaluokan [LeaderboardDao](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/dao/LeaderboardDao.java) kanssa yhteistyössä luodut integraatiotestit. Näiden testien tavoite on varmistaa, että mahdollinen uusi leaderboardiin pääsevä pelitulos käsitellään oikein sekä tietokannassa että tietokantojen toimintaa hallinnoivassa Leaderboard -luokassa. Siten testit hyödyntävät myös [HighScore](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/HighScore.java) -luokan toimintaa. Testejä varten on luotu kaksi uutta testitietokantaa, jotka on määritelty sovelluksen src/main/resources/tetris -kansiosta löytyvässä [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties) -konfigurointitiedostossa.

**DAO-luokat**

[LeaderboardDao](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/dao/LeaderboardDao.java) -luokka käsittelee kaikki sovelluksen tietokantahaut ja -tallennukset. Testitiedosto [LeaderboardDaoTest](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/test/java/tetris/dao/LeaderboardDaoTest.java) käsittelee siis tietokannan muokkaamiseen ja kutsumiseen liittyvät metodit. Testit ovat luonteeltaan yksikkötestejä, vaikka ne hyödyntävätkin [HighScore](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/HighScore.java) -luokkaa testattavien asioiden tulostamiseen. Myös nämä DAO-luokan testit hyödyntävät omaa testitietokantaansa, joka on määritelty sovelluksen [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties) -konfigurointitiedostossa.

**Testikattavuus**

Käyttöliittymää ei ole testattu, mutta muuten sovelluksen rivikattavuus on 96% ja haarautumakattavus 94%. Main-luokkaakaan ei ole testattu, sillä se avustaa vain JavaFX:n avulla luodun käyttöliittymän käynnistämisen. Testaamatta jäivät myös ne muutamat suoraviivaiset getterit ja setterit, joita kutsutaan vain sovelluksen käyttöliittymä-luokasta.

![Testikattavuusraportti](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/testikattavuusraportti.png)

## Järjestelmätestaus

Seuraavaksi kuvattava järjestelmätestaus on toteutettu manuaalisesti.

**Asennus ja konfigurointi**

Sovellus on ladattu GitHubista sekä .jar-tiedostona että kokonaisena repositoriona. Pelin avaaminen on testattu Linux-ympäristössä sekä .jar -tiedostona että suoraan komentoriviltä compile-käskyllä. Molemmissa tapauksissa sovellus toimi [käyttöohjeen](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kayttoohje.md) kuvaamalla tavalla. Lisäksi toimivuutta on testattu myös yliopiston etäyhteydellä (ssh-yhteys melkki.cs.helsinki.fi). Etäyhteystestauksessa havaittiin, että yliopiston järjestelmä ei esimerkiksi näytä JavaFX-käyttöliittymässä luotuva nappeja, mutta sovellus toimi siitä huolimatta oikein.

Sovellusta on myös testattu muuttamalla sovelluksen [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties) -konfigurointitiedoston parametrejä. Ohjelma toimi tässäkin tapauksessa oikein.

**Toiminnallisuudet**

Kaikki sovelluksen näkymät ja toiminnallisuudet on testattu kattavalla manuaalisella testauksella. Highscorenäkymän syötekenttään annetut erikoistapaukset, kuten tyhjä, toimivat oikein. Myös siirtymät näkymistä toiseen sekä itse pelin pelaaminen toimivat jokaisessa tapauksesa oikein.

## Sovellukseen jääneet laatuongelmat

Ei havaittuja laatuongelmia.
