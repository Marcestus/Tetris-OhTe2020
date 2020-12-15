package tetris.domain;

import java.util.*;

/**
 * Pelilogiikkaa kuvaava luokka.
 * Aktiivisella palikalla tarkoitetaan sitä palikkaa, jota pelaaja liikuttaa.
 * Passiivisilla tiilillä tarkoitetaan jo pelattuja palikoita, jotka ovat jäätyneet paikoilleen. Näistä pyritään muodostamaan täysiä rivejä.
 * Passiivisten palikoiden käsittely hajotetaan yksittäisiksi tiiliksi, jotta täysien rivien poistaminen helpottuu.
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
    private GameSpeed gameSpeed;
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
    
    public TetrisGame() {
        this.boardHeight = 20;
        this.boardWidth = 10;
        this.score = new Score();
        this.gameSpeed = new GameSpeed();
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
        this.gameSpeed = new GameSpeed();
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

    public ArrayList<Tile> getPassiveTiles() {
        return passiveTiles;
    }

    public Score getScore() {
        return score;
    }
    
    public GameSpeed getGameSpeed() {
        return gameSpeed;
    }
    
    /**
     * Aktiivisen palikan tiilien sijaintitietojen päivitys.
     * Avustaa GameView-luokkaa.
     * @return aktiivisen palikan tiilien koordinaatit boolean[][] -taulukkona
     */
    public boolean[][] getActiveShapeCoord() {
        for (Tile activeTile : activeShapeTiles) {
            activeTileCoord[activeTile.getX()][activeTile.getY()] = true;
        }
        return activeTileCoord;
    }
    
    /**
     * Aktiivisen palikan tiilien sijaintitietojen nollaaminen.
     * Avustaa GameView-luokkaa.
     */
    public void resetActiveShapeCoord() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                activeTileCoord[j][i] = false;
            }
        }
    }
    
    /**
     * Passiivisten tiilien sijaintitietojen päivitys.
     * Avustaa GameView-luokkaa.
     * @return passiivisten tiilien koordinaatit boolean[][] -taulukkona
     */
    public boolean[][] getPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = true;
        }
        return passiveTileCoord;
    }
    
    /**
     * Passiivisten tiilien sijaintietojen nollaaminen.
     * * Avustaa GameView-luokkaa.
     */
    public void resetPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = false;
        }
    }
    
    /**
     * Uuden aktiivisen palikan luominen, kun vanha ei pääse enää liikkumaan alaspäin.
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
     * Testauksen vaihtoehtoinen metodi uuden aktiivisen palikan luomiselle, kun vanha ei pääse enää liikkumaan alaspäin.
     * Testausta varten metodille annetaan palikan tyyppi, eli se ei ole satunnainen.
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
     * Aktiivisen palikan liikuttaminen alaspäin.
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
     * Aktiivisen palikan liikuttaminen alaspäin niin kauan, kunnes se ei pääse enää putoamaan alaspäin ("Hard drop").
     * Kun palikka ei enää pääse liikkumaan alaspäin, sen tiilet siirretään passiivisten joukkoon ja kutsutaan metodia, joka luo uuden aktiivisen palikan.
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
     * Uusiin mahdollisiin koordinaatteihin vaikuttaa se, tiilet sijaitsevat keskitiileen nähden:
     * suoraan sivulla, vinosti nurkassa vai kahden päässä sivulla (vain "I" -palikassa)
     * Lisäksi kääntymiseen vaikuttaa, kuinka monta kääntötilaa (orientation) palikalla on:
     * "O" -palikka ei käänny ollenkaan kun taas "I", "Z" ja "S" -palikoilla on vain kaksi kääntötilaa. Ne kääntyvät vuorotellen 90 astetta myötä- ja vastapäivään.
     * "L", "J" ja "T" -palikoilla on puolestaan neljä kääntötilaa, joten ne kääntyvät jokaisella käännöksellä 90 astetta myötäpäivään.
     * Mikäli palikka mahtuu kääntymään, näiden tietojen pohjalta palikka käännetään seuraavaan kääntötilaansa.
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
        if (t.getX() == centerX + 1) { // oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getY() == centerY + 1) { // alapuolella
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = centerY;
        } else if (t.getX() == centerX - 1) { // vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getY() == centerY - 1) { // yläpuolella
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = centerY;
        }
    }
    
    private void newCoordDoubleSideTile(Tile t) {
        if (t.getX() == centerX + 2) { // oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 2 : centerY + 2;
        } else if (t.getY() == centerY + 2) { // alapuolella
            nextX = reverse ? centerX + 2 : centerX - 2;
            nextY = centerY;
        } else if (t.getX() == centerX - 2) { // vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 2 : centerY - 2;
        } else if (t.getY() == centerY - 2) { // yläpuolella
            nextX = reverse ? centerX - 2 : centerX + 2;
            nextY = centerY;
        }
    }
    
    private void newCoordCornerTile(Tile t) {
        if (t.getX() == centerX - 1 && t.getY() == centerY - 1) { // oikea ylänurkka
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY - 1) { // oikea alanurkka
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY + 1) { // vasen alanurkka
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX - 1 && t.getY() == centerY + 1) { // vasen ylänurkka
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
     * Vaakasuorien täysien rivien etsintä passiivisten tiilien joukosta.
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
     * Täysien rivien poistaminen ja niiden yläpuolella olevien passiivisten tiilien pudottaminen alaspäin.
     * Tämä tehdään niin monta kertaa, kuin täysiä rivejä syntyy kerralla (1-4 kpl). Poistettujen rivien kokonaismäärä päivitetään samalla Score -olioon.
     */
    public void deleteRowsAndDropRowsAboveFullRows() {
        while (!fullRows.isEmpty()) {
            rowToBeRemoved = fullRows.get(0);
            fullRows.remove(0);
            getFullRowTiles();
            getRowsAboveFullRows();
            deleteFullRows();
            dropRowsAboveFullRows();
        }
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
     * Pelin pistetilanteen ja pelinopeuden päivitys.
     */
    public void setScoreAndSpeed() {
        score.addPoints(fullRowsAtSameTime);
        score.addClearedLines(fullRowsAtSameTime);
        score.levelUp();
        gameSpeed.setGameSpeed(score.getLevel());
    }
    
    /**
     * Game over -määreiden selvittäminen eli jääkö aktiivinen palikka pelialueen yläreunan yläpuolelle.
     * @return true, jos game overin määreet täyttyvät; false, jos ei
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