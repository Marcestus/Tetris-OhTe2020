# Arkkitehtuurikuvaus

## Pakkausrakenne

Ohjelman rakenne noudattaa kolmitasoista kerrosarkkitehtuuria: ui käsittelee käyttöliittymän, domain sovelluslogiikan ja dao tietojen pysyväistalletuksen.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/pakkausrakenne.png">

## Sovelluslogiikka

Sovelluksen logiikan perustan luovat luokat Tile ja Shape, jotka kuvaavat pelin palikoita ja niiden rakenneosia, tiiliä.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/luokkakaavio.png">

## Päätoiminnallisuudet

Seuraavat sekvenssikaaviot kuvastavat sovelluksen päätoiminnallisuuksia.

**Palikan liikkuminen alaspäin estyy, luodaan uusi palikka**

Aktiivinen palikka liikkuu tietyn ajan kuluessa yhden peliruudun verran alaspäin, mikäli se ei törmää pelialueen alareunaan tai passiivisiin palikoihin. Kuvatussa tilanteessa palikka ei pääse liikkumaan enää alaspäin.

<img src="https://github.com/Marcestus/ot-harjoitustyo/blob/master/dokumentaatio/kuvat/moveDown().png">

Kun GameView antaa käskyn palikan liikuttamiseksi alaspäin, pelilogiikkaa ylläpitävä TetrisGame siis selvittää ensin, pääseekö palikka liikkumaan. Koska se ei pääse, aktiivisen palikan sijainti siirretään passiivisten palikoiden listaan ja pyydetään uutta palikkaa. Shape ja Tile luovat uuden palikan ja palauttavat sen TetrisGame:lle. Kun metodi päättyy, GameView jatkaa seuraavaan ajoitettuun tehtäväänsä.
