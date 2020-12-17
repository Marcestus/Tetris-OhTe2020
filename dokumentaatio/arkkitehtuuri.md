# Arkkitehtuurikuvaus

## Pakkausrakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa tetris.ui käsittelee JavaFX -pohjaisen käyttöliittymän, tetris.domain sovelluslogiikan ja tetris.dao tietojen pysyväistalletuksen.

![pakkausrakenne](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkausrakenne.png)

## Käyttöliittymä

Käyttöliittymä sisältää neljä erillistä näkymää

- menunäkymä
- pelinäkymä
- highscorenäkymä
- leaderboardnäkymä

Jokaiselle näkymälle on luotu käyttöliittymäluokkaan [tetris.ui.GameView](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/ui/GameView.java) oma Scene-olio. Näkymät ovat yksi kerrallaan näkyvissä eli asetettuna Stage-olioon.

Käyttöliittymä on pyritty eriyttämään täysin sovelluslogiikasta. Sen tarkoitus on siis vastata vain näkymien näyttämisestä ja niissä olevan datan päivittämisestä. Käyttöliittymä hyödyntää tässä tehtävässään sovelluslogiikasta vastaavaa luokkaa [tetris.domain.TetrisGame](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/TetrisGame.java) sekä leaderboardista ja sen toimintalogiiksta vastaavaa luokkaa [tetris.domain.Leaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Leaderboard.java). Sovelluslogiikka siis huolehtii mm. siitä, että pelinäkymän tiedot, kuten palikoiden sijainti ja pistetilanne, päivittyvät oikein. Leaderboard -luokka huolehtii puolestaan leaderboardnäkymän päivityksestä ja highscorenäkymän käsittelystä silloin, kun pisteet riittävät leaderboardiin pääsemiseen.

## Sovelluslogiikka

Sovelluslogiikan perustan luovat luokat [Tile](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Tile.java), [Shape](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Shape.java) ja [Score](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Score.java). Näistä kaksi ensimmäistä kuvaavat Tetris-pelin palikoita ja niiden rakenneosia, tiiliä. Score pitää puolestaan yllä pelin pistetilannetta.

![luokkakaavioTetrisGame](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio1.jpg)

Pelilogiikan perusluokkia hyödynnetään pelin sovelluslogiikasta vastaavassa [TetrisGame](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/TetrisGame.java) -luokassa. Luokka tarjoaa mm. seuraavat metodit aktiivisen Tetris-palikan liikuttamiseen, täysien rivien poistamiseen sekä pistetilanteen päivittämiseen.

```
void moveDown()
void moveLeft()
void moveRight()
void hardDrop()
void rotate()
void fullRowsHandler()
```
Lisäksi luokka huolehtii myös mm. seuraavien metodien avulla uuden aktiivisen palikan pyytämisestä perusluokilta, palikoiden putoamisnopeuden säätämisestä valitun vaikeustason mukaan ja pelin päättymistä indikoivien määreiden täyttymisen seuraamisesta.

```
Shape createRandomShape()
int setGameSpeedAndGameLevel(String chosenGameLevel)
boolean gameOver()
```

Sovelluksen src/main/resources/tetris -kansioon sijoitettu konfigurointitiedosto [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties) määrittelee palikoiden putoamisnopeuden.

[Leaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Leaderboard.java) -luokka vastaa puolestaan siitä, milloin pelitulos on kyllin hyvä päästäkseen leaderboardiin. Toimintaa avustava [HighScore](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/HighScore.java) -luokka pitää siis yllä tietoa leaderboardiin pääsevästä pelituloksesta (nimimerkki ja pisteet). Edellisiin toimintoihin luotuja metodeja Leaderboard -luokassa ovat esim.

```
ArrayList<HighScore> getLeaderboard()
boolean pointsEnoughForLeaderboard(int currentPoints)
```

![LuokkakaavioLeaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio2.jpg)

## Tietojen pysyväistalletus ja tietojen hakeminen tietokannasta

Edellä kuvattu [Leaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/Leaderboard.java) -luokka toimii linkkinä tetris.dao -pakkauksen [LeaderboardDao](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/dao/LeaderboardDao.java) -luokkaan. Leaderboard -luokka siis antaa toimintalogiikan LeaderboardDaolle ja ilmaisee mm. seuraavin metodein, miten sen kuuluu käsitellä tietokantaa.

Tietojen hakeminen:

```
int getHighScore()
ArrayList<String> getLeaderboardPlayers()
ArrayList<Integer> geLeaderboardPoints()
```

Tietojen tallentaminen:

```
void addNewHighScoreToLeaderboard(String player, int currentPoints)
```

LeaderboardDao -luokka vastaa puolestaan mm. seuraavin metodein tietojen varsinaisesta käsittelystä ohjelman tietokannassa:
```
Connection connect()
void createNewLeaderBoardTable()
ArrayList<HighScore> addScoreToDatabase(String nickname, int points)
ArrayList<HighScore> removeScoreFromDatabase()
ArrayList<HighScore> getLeaderBoardFromDatabase()
```

**Ohjelman osien suhdetta kuvaava luokka/pakkauskaavio:**

![luokkienJaPakkaustenSuhdekaavio](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkienPakkaustenSuhde.jpg)

## Tiedostot

Sovellus tallentaa parhaat viisi pelitulosta tietokantaan.
Sovelluksen src/main/resources/tetris -kansioon sijoitettu konfigurointitiedosto [config.properties](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/resources/tetris/config.properties) tietokannan nimen.

Pelitulokset tallennetaan *Leaderboard* -tauluun, joka sisältää sarakkeet
```
id INTEGER PRIMARY KEY
nickname TEXT
points INTEGER
```
eli jokaisesta tuloksesta tallentuu yksilöivä id, pelaajan syöttämä nimimerkki ja pisteet.

## Päätoiminnallisuudet

Seuraavat sekvenssikaaviot kuvastavat sovelluksen päätoiminnallisuuksia.

**Palikan liikuttaminen vasemmalle ja oikealle**

Aktiivinen palikka liikkuu pelaajan käskystä vasemmalle ja oikealle, mikäli se ei törmää passiivisiin tiiliin tai pelialueen reunoihin.

![moveLeftAndRight](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/moveLeftAndMoveRight.png)

**Palikan kääntäminen ja pudottaminen alas**

Aktiivinen palikka kääntyy pelaajan käskystä, mikäli se ei törmää passiivisiin tiiliin tai pelialueen reunoihin. Samalla tavoin palikka putoaa suoraan paikoilleen pelaajan käskystä.

![rotateAndHardDrop](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rotateAndHardDrop.png)

**Palikan liikkuminen alaspäin, uuden palikan luominen, täysien rivien poistaminen ja pistetilanteen päivittäminen**

Aktiivinen palikka liikkuu tietyn ajan kuluessa yhden peliruudun verran alaspäin, mikäli se ei törmää pelialueen alareunaan tai passiivisiin palikoihin. Tämän jälkeen sovelluslogiikka tarkistaa, löytyykö pelialueelta täysiä vaakasuoria rivejä poistettavaksi.

Kun aktiivinen palikka ei pääse enää putoamaan, se muunnetaan passiivisiksi tiiliksi. Samalla luodaan uusi aktiivinen palikka, joka generoituu pelialueen yläreunaan. Tämän jälkeen sovelluslogiikka tarkistaa taas, löytyykö pelialueelta täysiä vaakasuoria rivejä poistettavaksi. Kun täysi rivi löytyy, se poistuu ja sen yläpuolella olevat passiiviset tiilet tippuvat alaspäin poistuneen rivin paikalle. Samalla pisteet päivittyvät.

![moveDownAndFullRowsHandler](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/moveDownAndFullRowsHandler.png)

**Pelin päättyminen ja pelituloksen tallentaminen tietokantaan**

Kun aktiivinen palikka ei mahdu enää putoamaan pelialueella, peli päättyy. Käyttöliittymä tarkistaa leaderboardin logiikasta vastaavalta luokalta, riittävätkö päättyneen pelin pisteet leaderboardiin pääsemiseen. Mikäli pisteet riittävät, siirrytään highscorenäkymään ja pyydetään pelaajalta nimimerkkiä. Kun nimimerkki on syötetty, pelitulos päivittyy tietokantaan ja siirrytään leaderboardnäkymään. Mikäli pisteet eivät riitä, siirrytään suoraan leaderboardnäkymään.

![gameOverAndUpdateLeaderboard](https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/gameOverAndUpdateLeaderboard.png)

## Ohjelman rakenteeseen jääneet heikkoudet

**Käyttöliittymä**

Ohjelman käyttöliittymän kaikki näkymät toteutetaan samassa GameView -luokassa. Rakennetta on pyritty selkeyttämään hajauttamalla toiminta omiksi metodeikseen. Nykyinen rakenne voisi kuitenkin olla syytä korvata FXML-määrittelyllä, jolloin rakenne olisi vielä selkeämpi ja luokat lyhyempiä.

**Sovelluslogiikka**

Ohjelman sovelluslogiikasta vastaava TetrisGame -luokka on melko pitkä, sillä se käsittelee kaiken pelin toimintalogiikan. Palikoiden liikuttamiseen liittyvä logiikka voitaisiin mahdollisesti vielä eriyttää omaksi luokakseen.

**DAO-luokka**

Sovelluksen Data Access Object -suunnittelumallin logiikkaa voisi vielä parantaa luomalla erilliset rajapinnat tietokantalogiikkaa ylläpitävän Leaderboard -luokan ja tietokannan hallitsemisesta vastaavan LeaderboardDao -luokan väliin. Toisaalta tietokantalogiikka on eriytetty omaksi luokakseen sovelluksen muusta logiikasta (TetrisGame -luokka), mikä parantaa osaltaan tietokannan eriyttämistä sovelluslogiikasta.
