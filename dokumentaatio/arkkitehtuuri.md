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

**Palikan liikkuminen alaspäin**

Aktiivinen palikka liikkuu tietyn ajan kuluessa yhden peliruudun verran alaspäin, mikäli se ei törmää pelialueen alareunaan tai passiivisiin palikoihin. Alla on kuvattuna molemmat tilanteet.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/moveDown.png">

**Palikan liikkuminen vasemmalle ja oikealle**

Aktiivinen palikka liikkuu pelaajan käskystä vasemmalle ja oikealle, mikäli se ei törmää passiivisiin tiiliin tai pelialueen reunoihin.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rightAndLeft.png">

**Palikan kääntyminen ja putoaminen alaspäin ("Hard drop")**

Aktiivinen palikka kääntyy pelaajan käskystä sekä putoaa alaspäin, kunnes se osuu passiiviseen tiileen tai pelialueen alareunaan.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/rotateAndHardDrop.png">

**Täysien vaakasuorien rivien poistuminen ja pisteiden päivittyminen**

Kun passiiviset tiilet muodostavat kokonaisen täyden rivin, se poistuu ja sen yläpuolella olevat passiiviset tiilet tippuvat alaspäin poistuneen rivin paikalle. Samalla pisteet päivittyvät.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/FullRowsSequence.png">
