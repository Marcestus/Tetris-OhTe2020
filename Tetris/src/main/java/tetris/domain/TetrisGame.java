package tetris.domain;

import java.util.*;

/**
 * Pelilogiikkaa kuvaava luokka.
 * Aktiivisella palikalla tarkoitetaan sitä palikkaa, jota pelaaja pystyy liikuttamaan.
 * Passiiviset tiilet ovat puolestaan jo pelattujen aktiivisten palikoiden tiiliä, joista täydet rivit pyritään muodostamaan.
 * Luokassa määritellään kaikki pelin perustoiminnallisuudet, kuten: - Aktiivisen palikan liikkuminen pelaajan komentojen mukaan, - Täysien rivien poistuminen,
 * - Pisteiden määräytyminen, - Pelin pelinopeuden määräytyminen, - Onko peli game over -tilassa
 */

public class TetrisGame {
    private int nextX;
    private int nextY;
    private int orientation;
    private boolean reverse;
    private int rowToBeRemoved;
    private int fullRowsAtSameTime;
    private int boardHeight;
    private int boardWidth;
    private Score score;
    private GameSpeed speed;
    private Shape activeShape;
    private Tile[] activeShapeTiles;
    private boolean[][] activeTileCoord;
    private String shapeType;
    private boolean shapeIsTypeO;
    private int centerX;
    private int centerY;
    private int edge;
    private int passiveTileDirectionX;
    private int passiveTileDirectionY;
    private ArrayList<Tile> passiveTiles;
    private boolean[][] passiveTileCoord;
    private Tile[] newCoordinates;
    private ArrayList<Integer> fullRows;
    private HashSet<Integer> rowsAboveFullRows;
    private int[] fullRowCounter;
    private HashSet<Integer> tileToBeRemoved;
    private ArrayList<Tile> newPassiveTiles;
    
    /**
     * Konstruktori alustaa pelilogiikkaan tarvittavat muuttujat, joita käytetään useammassa kuin yhdessä metodissa.
     */
    public TetrisGame() {
        this.boardHeight = 20;
        this.boardWidth = 10;
        this.score = new Score();
        this.speed = new GameSpeed();
        this.activeShape = createRandomShape();
        this.activeShapeTiles = activeShape.getTiles();
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
        this.shapeType = activeShape.getType();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.edge = -1;
        this.passiveTileDirectionX = -1;
        this.passiveTileDirectionY = -1;
        this.passiveTiles = new ArrayList<>();
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.fullRows = new ArrayList<>();
        this.rowsAboveFullRows = new HashSet<>();
        this.fullRowCounter = new int[boardHeight];
        this.tileToBeRemoved = new HashSet<>();
        this.newPassiveTiles = new ArrayList<>();
    }
    
    /**
     * Vaihtoehtoinen konstruktori testauksen käyttöön.
     * Poikkeaa vain uuden aktiivisen palikan luomisen osalta.
     * @param type minkä tyyppinen uusi aktiivinen palikka luodaan
     */
    public TetrisGame(int type) {
        this.boardHeight = 20;
        this.boardWidth = 10;
        this.score = new Score();
        this.speed = new GameSpeed();
        this.activeShape = createRandomShape(type);
        this.activeShapeTiles = activeShape.getTiles();
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
        this.shapeType = activeShape.getType();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.edge = -1;
        this.passiveTileDirectionX = -1;
        this.passiveTileDirectionY = -1;
        this.passiveTiles = new ArrayList<>();
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.fullRows = new ArrayList<>();
        this.rowsAboveFullRows = new HashSet<>();
        this.fullRowCounter = new int[boardHeight];
        this.tileToBeRemoved = new HashSet<>();
        this.newPassiveTiles = new ArrayList<>();
    }
    
    
    public int getBoardHeight() {
        return boardHeight;
    }
    
    public int getBoardWidth() {
        return boardWidth;
    }
    
    public Shape getActiveShape() {
        return activeShape;
    }
    
    public void setActiveShape(Shape activeShape) {
        this.activeShape = activeShape;
    }
    
    public Tile[] getActiveShapeTiles() {
        return activeShapeTiles;
    }
    
    /**
     * Kertoo GameView -luokalle, missä kohdassa pelialuetta aktiivisen palikan tiilet sijaitsevat.
     * GameView -luokka käsittelee aktiivisen palikan tiilet koordinaatteina, joten tässä luokassa luodaan tarvittava koordinaatisto hyödyntäen
     * boolean[][]-taulukkoa. Eli mikäli kyseisen koordinaatin arvo on true, GameView piirtää siihen aktiivisen palikan tiilen.
     * @return aktiivisen palikan tiilien koordinaatit boolean[][] -taulukkona
     */
    public boolean[][] getActiveShapeCoord() {
        for (Tile activeTile : activeShapeTiles) {
            activeTileCoord[activeTile.getX()][activeTile.getY()] = true;
        }
        return activeTileCoord;
    }
    
    /**
     * Nollaa tiedon siitä, missä kohdassa pelialuetta aktiivisen palikan tiilet sijaitsevat.
     */
    public void resetActiveShapeCoord() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                activeTileCoord[j][i] = false;
            }
        }
    }
    
    /**
     * Kertoo GameView -luokalle, missä kohdassa pelialuetta passiiviset tiilet sijaitsevat.
     * Passiivisia tiiliä ei ole enää mielekästä käsitellä neljästä tiilestä koostuvina Tetris-palikoina, joten ne hajotetaan yksittäisiksi tiiliksi.
     * Näin täysien rivien poistaminen helpottuu. Aktiivisen palikan tapaan tiilet käsitellään GameView -luokassa koordinaatteina.
     * Eli mikäli kyseisen koordinaatin arvo on true, GameView piirtää siihen passiivisen tiilen.
     * @return passiivisten tiilien koordinaatit boolean[][] -taulukkona
     */
    public boolean[][] getPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = true;
        }
        return passiveTileCoord;
    }
    
    /**
     * Nollaa tiedon siitä, missä kohdassa pelialuetta passiiviset tiilet sijaitsevat.
     */
    public void resetPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = false;
        }
    }
    
    /**
     * Kun aktiivinen palikka ei pääse enää alaspäin, luodaan uusi aktiivinen palikka.
     * Aktiivinen palikka arvotaan satunnaisesti.
     * @return uusi aktiivinen palikka
     */
    public Shape createRandomShape() {
        Random random = new Random();
        int type = random.nextInt(7);
        switch (type) {
            case 0: return new Shape("L");
            case 1: return new Shape("J");
            case 2: return new Shape("S");
            case 3: return new Shape("Z");
            case 4: return new Shape("O");
            case 5: return new Shape("I");
            default: return new Shape("T");
        }
    }
    
    /**
     * Vaihtoehtoinen metodi testausta varten: kun aktiivinen palikka ei pääse enää alaspäin, luodaan uusi aktiivinen palikka.
     * Testausta varten metodille annetaan palikan tyyppi, eli se ei ole satunnainen tämän metodin avulla luotuna.
     * @param type minkä tyyppinen uusi aktiivinen palikka luodaan
     * @return uusi aktiivinen palikka
     */
    public Shape createRandomShape(int type) {
        switch (type) {
            case 0: return new Shape("L");
            case 1: return new Shape("J");
            case 2: return new Shape("S");
            case 3: return new Shape("Z");
            case 4: return new Shape("O");
            case 5: return new Shape("I");
            default: return new Shape("T");
        }
    }
    
    /**
     * Aktiivisen palikan liikkuminen alaspäin gravitaation vaikutuksesta.
     * Mikäli palikka ei enää pääse liikkumaan alaspäin, sen tiilet siirretään passiivisten joukkoon ja kutsutaan metodia, joka luo uuden aktiivisen palikan.
     */
    public void moveDown() {
        boolean moveDownPossible = passiveTilesOnTheWay("down") && edgeOnTheWay("down");
        if (moveDownPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setY(activeShapeTile.getY() + 1);
            }
        } else {
            for (Tile activeShapeTile : activeShapeTiles) {
                passiveTiles.add(activeShapeTile);
            }
            activeShape = createRandomShape();
            activeShapeTiles = activeShape.getTiles();
        }
    }
    
    /**
     * Aktiivisen palikan liikuttaminen vasempaan.
     * Liikuttaminen onnistuu, mikäli passiivinen tiili tai pelialueen reuna ei ole aktiivisen palikan tiellä.
     */
    public void moveLeft() {
        boolean moveLeftPossible = passiveTilesOnTheWay("left") && edgeOnTheWay("left");
        if (moveLeftPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() - 1);
            }
        }
    }
    
    /**
     * Aktiivisen palikan liikuttaminen oikealle.
     * Liikuttaminen onnistuu, mikäli passiivinen tiili tai pelialueen reuna ei ole aktiivisen palikan tiellä.
     */
    public void moveRight() {
        boolean moveRightPossible = passiveTilesOnTheWay("right") && edgeOnTheWay("right");
        if (moveRightPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() + 1);
            }
        }
    }
    
    /**
     * Aktiivisen palikan liikkuminen alaspäin niin kauan, kunnes se ei pääse enää putoamaan alaspäin ("Hard drop").
     * Liikkuu alaspäin, kunnes osuu passiiviseen tiileen tai pelialueen reunaan.
     */
    public void hardDrop() {
        while (passiveTilesOnTheWay("down") && edgeOnTheWay("down")) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setY(activeShapeTile.getY() + 1);
            }
        }
        for (Tile activeShapeTile : activeShapeTiles) {
            passiveTiles.add(activeShapeTile);
        }
        activeShape = createRandomShape();
        activeShapeTiles = activeShape.getTiles();
    }
    
    private boolean edgeOnTheWay(String direction) {
        for (Tile activeShapeTile : activeShapeTiles) {
            if (direction.equals("right")) {
                edge = boardWidth - 1;
            } else if (direction.equals("left")) {
                edge = 0;
            } else if (direction.equals("down")) {
                edge = boardHeight - 1;
                if (activeShapeTile.getY() == edge) {
                    return false;
                }
            }
            if (activeShapeTile.getX() == edge) {
                return false;
            }
        }
        return true;
    }
    
    private boolean passiveTilesOnTheWay(String direction) {
        for (Tile activeShapeTile : activeShapeTiles) {
            for (Tile passiveTile : passiveTiles) {
                if (direction.equals("right")) {
                    passiveTileDirectionX = passiveTile.getX() - 1;
                    passiveTileDirectionY = passiveTile.getY();
                } else if (direction.equals("left")) {
                    passiveTileDirectionX = passiveTile.getX() + 1;
                    passiveTileDirectionY = passiveTile.getY();
                } else if (direction.equals("down")) {
                    passiveTileDirectionX = passiveTile.getX();
                    passiveTileDirectionY = passiveTile.getY() - 1;
                }
                if (activeShapeTile.getX() == passiveTileDirectionX && activeShapeTile.getY() == passiveTileDirectionY) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Aktiivisen palikan kääntäminen palikan tyypin mukaisella tavalla.
     * Palikan kääntymisen jälkeisiin uusiin koordinaatteihin vaikuttaa ensinnäkin se, missä keskipalikan ympärillä olevat palikat sijaitsevat keskipalikkaan nähden:
     * - suoraan sivulla, - vinosti nurkassa, - kahden päässä sivulla (vain "I" -palikassa)
     * Lisäksi kääntymiseen vaikuttaa, kuinka monta kääntötilaa (orientation) palikalla on:
     * - "O" -palikka ei käänny ollenkaan, - "I", "Z" ja "S" -palikoilla on vain kaksi kääntötilaa, joten ne kääntyvät vuorotellen 90 astetta myötä- ja vastapäivään,
     * - "L", "J" ja "T" -palikoilla on neljä kääntötilaa, joten ne kääntyvät jokaisella käännöksellä 90 astetta myötäpäivään.
     * Näiden avulla lasketaan aktiiviselle palikalle uudet koordinaatit, mikäli palikka mahtuu kääntymään, eli se ei osu passiivisiin tiiliin tai pelialueen reunaan.
     */
    public void rotate() {
        setVariablesForRotation();
        if (shapeType.equals("O")) {
            return;
        }
        if ((shapeType.equals("I") || shapeType.equals("Z") || shapeType.equals("S")) && orientation == 2) {
            reverse = true;
        }
        getCoordForRotation();
        if (rotatePossible()) {
            for (int i = 1; i < activeShapeTiles.length; i++) {
                activeShapeTiles[i].setX(newCoordinates[i].getX());
                activeShapeTiles[i].setY(newCoordinates[i].getY());
            }
            activeShape.setOrientation(orientation + 1);
        }
        if (reverse || activeShape.getOrientation() == 4) {
            activeShape.setOrientation(1);
        }
    }
    
    private void setVariablesForRotation() {
        centerX = activeShapeTiles[0].getX();
        centerY = activeShapeTiles[0].getY();
        newCoordinates[0].setX(centerX);
        newCoordinates[0].setY(centerY);
        shapeType = activeShape.getType();
        orientation = activeShape.getOrientation();
        reverse = false;
    }
    
    private void getCoordForRotation() {
        for (int i = 1; i < activeShapeTiles.length; i++) {
            String tileType = getTileType(activeShapeTiles[i]);
            if (tileType.equals("Side")) {
                newCoordSideTile(activeShapeTiles[i]);
            }
            if (tileType.equals("DoubleSide")) {
                newCoordDoubleSideTile(activeShapeTiles[i]);
            }
            if (tileType.equals("Corner")) {
                newCoordCornerTile(activeShapeTiles[i]);
            }
            newCoordinates[i].setX(nextX);
            newCoordinates[i].setY(nextY);
        }
    }
    
    private String getTileType(Tile t) {
        if ((t.getX() == centerX && (t.getY() == centerY + 1 || t.getY() == centerY - 1) ||
            (t.getX() == centerX + 1 || t.getX() == centerX - 1) && t.getY() == centerY)) {
            return "Side";
        } else if (shapeType.equals("I")) {
            return "DoubleSide";
        } else {
            return "Corner";
        }
    }
    
    private void newCoordSideTile(Tile t) {
        if (t.getX() == centerX + 1) {
            // oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getY() == centerY + 1) {
            // alapuolella
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = centerY;
        } else if (t.getX() == centerX - 1) {
            // vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getY() == centerY - 1) {
            // yläpuolella
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = centerY;
        }
    }
    
    private void newCoordDoubleSideTile(Tile t) {
        if (t.getX() == centerX + 2) {
            // oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 2 : centerY + 2;
        } else if (t.getY() == centerY + 2) {
            // alapuolella
            nextX = reverse ? centerX + 2 : centerX - 2;
            nextY = centerY;
        } else if (t.getX() == centerX - 2) {
            // vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 2 : centerY - 2;
        } else if (t.getY() == centerY - 2) {
            // yläpuolella
            nextX = reverse ? centerX - 2 : centerX + 2;
            nextY = centerY;
        }
    }
    
    private void newCoordCornerTile(Tile t) {
        if (t.getX() == centerX - 1 && t.getY() == centerY - 1) {
            // oikea ylänurkka
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY - 1) {
            // oikea alanurkka
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY + 1) {
            // vasen alanurkka
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX - 1 && t.getY() == centerY + 1) {
            // vasen ylänurkka
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = reverse ? centerY + 1 : centerY - 1;
        }
    }
    
    private boolean rotatePossible() {
        for (int i = 1; i < newCoordinates.length; i++) {
            if (newCoordinates[i].getX() > (boardWidth - 1) || newCoordinates[i].getX() < 0 || newCoordinates[i].getY() > (boardHeight - 1) || newCoordinates[i].getY() < 0) {
                return false;
            }
            for (Tile passiveTile : passiveTiles) {
                if (passiveTile.getX() == newCoordinates[i].getX() && passiveTile.getY() == newCoordinates[i].getY()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Käydään läpi passiiviset tiilet ja tarkastetaan, löytyykö niiden joukosta täysiä vaakasuoria rivejä poistettavaksi.
     * @return true, jos täysiä rivejä löytyy; false, jos ei löydy
     */
    public boolean checkForFullRows() {
        for (int j = 0; j < boardHeight; j++) {
            fullRowCounter[j] = 0;
        }
        for (Tile passiveTile : passiveTiles) {
            fullRowCounter[passiveTile.getY()]++;
        }
        for (int j = 0; j < boardHeight; j++) {
            if (fullRowCounter[j] == 10) {
                fullRows.add(j);
            }
        }
        if (!fullRows.isEmpty()) {
            fullRowsAtSameTime = fullRows.size();
            return true;
        }
        return false;
    }
    
    /**
     * Ohjataan täysien rivien poistaminen ja niiden yläpuolella olevien passiivisten tiilien putoamisen poistuneiden rivien paikalle.
     * Tämä tehdään niin monta kertaa, kuin täysiä rivejä syntyy kerralla.
     * Täysiä rivejä voi syntyä kerralla 1-4 kappaletta ja ne lisätään samalla poistettujen rivien tilastoon (kts. Score -luokka).
     * @return kuinka monta riviä poistettiin
     */
    public int deleteRowsAndDropRowsAboveFullRows() {
        while (!fullRows.isEmpty()) {
            rowToBeRemoved = fullRows.get(0);
            fullRows.remove(0);
            getFullRowTiles();
            getRowsAboveFullRows();
            deleteFullRows();
            dropRowsAboveFullRows();
            fullRows.clear();
        }
        score.setLinesCleared(score.getLinesCleared() + fullRowsAtSameTime);
        return score.getLinesCleared();
    }
    
    private void getFullRowTiles() {
        for (int i = 0; i < passiveTiles.size(); i++) {
            if (passiveTiles.get(i).getY() == rowToBeRemoved) {
                tileToBeRemoved.add(i);
            }
        }
    }
    
    private void getRowsAboveFullRows() {
        for (Tile passiveTile : passiveTiles) {
            if (passiveTile.getY() < rowToBeRemoved) {
                rowsAboveFullRows.add(passiveTile.getY());
            }
        }
    }
    
    private void deleteFullRows() {
        for (int i = 0; i < passiveTiles.size(); i++) {
            if (!tileToBeRemoved.contains(i)) {
                newPassiveTiles.add(passiveTiles.get(i));
            }
        }
        passiveTiles.clear();
        for (Tile newPassiveTile : newPassiveTiles) {
            passiveTiles.add(newPassiveTile);
        }
        tileToBeRemoved.clear();
        newPassiveTiles.clear();
    }
    
    private void dropRowsAboveFullRows() {
        for (int i = 0; i < passiveTiles.size(); i++) {
            if (rowsAboveFullRows.contains(passiveTiles.get(i).getY()) && passiveTilesOnTheWay("down") && edgeOnTheWay("down")) {
                passiveTiles.get(i).setY(passiveTiles.get(i).getY() + 1);
            }
        }
        rowsAboveFullRows.clear();
    }
    
    /**
     * Lisätään pelaajan pisteisiin poistettujen rivien mukainen määrä pisteitä.
     * Mitä enemmän pisteitä poistetaan kerralla, sitä enemmän niitä saa pisteitä.
     * @return uusi pisteiden määrä
     */
    public int addPoints() {
        switch (fullRowsAtSameTime) {
            case 1:
                score.setPoints(score.getPoints() + (40 * (score.getLevel() + 1)));
                break;
            case 2:
                score.setPoints(score.getPoints() + (100 * (score.getLevel() + 1)));
                break;
            case 3:
                score.setPoints(score.getPoints() + (300 * (score.getLevel() + 1)));
                break;
            case 4:
                score.setPoints(score.getPoints() + (1200 * (score.getLevel() + 1)));
                break;
        }
        return score.getPoints();
    }
    
    /**
     * Pelissä päästään uudelle vaikeustasolle joka kymmenennen rivin poistamisen jälkeen.
     * Mahdollinen vaikeustason kasvaminen tarkistetaan siis joka kerta, kun täysiä rivejä poistetaan.
     * Lisäksi, mikäli ollaan saavutettu maksimi-vaikeustaso, vaikeustaso ei enää voi nousta. Näin ollen Score -olioon tallennetaan, että maksimi-vaikeustaso on saavutettu.
     * @return nykyinen vaikeustaso
     */
    public int levelUp() {
        if (score.getLinesCleared() >= (score.getLevel() + 1) * 10 && score.getLevel() <= score.getMaxLevel()) {
            score.setLevel(score.getLevel() + 1);
        }
        if (score.getLevel() == 10) {
            score.setMaxLevelReached(true);
        }
        return score.getLevel();
    }
    
    /**
     * Pelinopeus riippuu siitä, millä vaikeustasolla ollaan.
     * Mahdollinen pelinopeuden kasvaminen tarkistetaan siis joka kerta, kun täysiä rivejä poistetaan eli on mahdollisuus vaikeustason nousemiseen.
     * @return nykyinen pelinopeus
     */
    public int gameSpeed() {
        speed.setGameSpeed(score.getLevel());
        return speed.getSpeed();
    }
    
    /**
     * Joka kerta, kun aktiivinen palikka putoaa gravitaation vaikutuksesta alaspäin, varmistetaan, ettei se jää niin ylös, että peli päättyy.
     * Peli siis päättyy, kun aktiivinen palikka jää pelialueen yläreunan yläpuolelle.
     * @return true, jos peli päättyy; false, jos ei
     */
    public boolean gameOver() {
        for (Tile activeShapeTile : activeShapeTiles) {
            for (Tile passiveTile : passiveTiles) {
                if (activeShapeTile.getY() < 2 && activeShapeTile.getX() == passiveTile.getX() && activeShapeTile.getY() == passiveTile.getY() - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}