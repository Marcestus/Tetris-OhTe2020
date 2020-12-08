# Käyttöohje

Lataa tiedosto [Tetris.jar](https://github.com/Marcestus/ot-harjoitustyo/releases/tag/viikko6)

## Ohjelman käynnistäminen

Ohjelma käynnistetään komennolla 

java -jar Tetris.jar

## Pelaaminen

Peli alkaa heti, kun jar-tiedoston käynnistää.

Peliä ohjataan nuolinäppäimillä:
LEFT - aktiivinen palikka liikkuu vasemmalle
RIGHT - aktiivinen palikka liikkuu oikealle
UP - aktiivinen palikka kääntyy
DOWN - aktiivinen palikka tippuu alas ("Hard drop")

Peli päättyy, kun game status vaihtuu game on -> game over eli kun aktiivinen palikka ei enää mahdu putoamaan vaan osuu pelialueen yläreunaan.
