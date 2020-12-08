# Arkkitehtuurikuvaus

## Pakkausrakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria, jossa tetris.ui käsittelee käyttöliittymän, tetris.domain sovelluslogiikan ja tetris.dao tietojen pysyväistalletuksen.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkausrakenne.png">

## Käyttöliittymä

Sovelluksen käyttöliittymä sisältää tällä hetkellä vain varsinaisen pelinäkymän. Pelinäkymä on rakennettu luokassa [tetris.ui.GameView](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/ui/GameView.java).

Käyttöliittymä on pyritty eriyttämään täysin sovelluslogiikasta niin, että käyttöliittymä kutsuu vain sovelluslogiikasta vastaavaa luokkaa [tetris.domain.TetrisGame](https://github.com/Marcestus/ot-harjoitustyo/blob/master/Tetris/src/main/java/tetris/domain/TetrisGame.java).

## Sovelluslogiikka

Sovelluksen logiikan perustan luovat luokat Tile, Shape, Score ja GameSpeed. Näistä kaksi ensimmäistä kuvaavat Tetris-pelin palikoita ja niiden rakenneosia, tiiliä. Score pitää puolestaan yllä pelin pistetilannetta ja GameSpeed palikoiden putoamisnopeutta. Putoamisnopeus riippuu siitä, kuinka monta täyttä riviä pelaaja on onnistunut tuhoamaan.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaaviokuva.jpg">

Pelilogiikan perusluokkia hyödynnetään sovelluksen logiikasta vastaavassa TetrisGame -luokassa. Luokka tarjoaa metodit mm. aktiivisen Tetris-palikan liikuttamiseen, täysien rivien poistamiseen sekä pistetilanteen ja palikoiden putoamisnopeuden päivittämiseen.

## Päätoiminnallisuudet

Seuraavat sekvenssikaaviot kuvastavat sovelluksen päätoiminnallisuuksia.

**Palikan liikkuminen alaspäin estyy, luodaan uusi palikka**

Aktiivinen palikka liikkuu tietyn ajan kuluessa yhden peliruudun verran alaspäin, mikäli se ei törmää pelialueen alareunaan tai passiivisiin palikoihin. Kuvatussa tilanteessa palikka ei pääse liikkumaan enää alaspäin.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/moveDown().png">

Kun GameView antaa käskyn palikan liikuttamiseksi alaspäin, pelilogiikkaa ylläpitävä TetrisGame siis selvittää ensin, pääseekö palikka liikkumaan. Koska se ei pääse, aktiivisen palikan sijainti siirretään passiivisten palikoiden listaan ja pyydetään uutta palikkaa. Shape ja Tile luovat uuden palikan ja palauttavat sen TetrisGame:lle. Kun metodi päättyy, GameView jatkaa seuraavaan ajoitettuun tehtäväänsä.
