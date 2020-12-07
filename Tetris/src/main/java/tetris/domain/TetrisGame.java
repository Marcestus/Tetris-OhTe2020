package tetris.domain;

import java.util.*;

public class TetrisGame {
    
    public final int boardHeight = 20;
    public final int boardWidth = 10;
    private Shape activeShape;
    private Tile[] activeShapeTiles;
    boolean moveDownPossible; 
    boolean moveLeftPossible;
    boolean moveRightPossible;
    private int edge;
    private int passiveTileDirectionX;
    private int passiveTileDirectionY;
    private int centerX;
    private int centerY;
    private String shapeType;
    private String tileType;
    private int nextX;
    private int nextY;
    private Tile[] newCoordinates;
    private int orientation;
    private boolean reverse;
    private boolean[][] passiveTileCoord;
    private boolean[][] activeTileCoord;
    private ArrayList<Integer> fullRows;
    private HashSet<Integer> rowsAboveFullRows;
    private ArrayList<Tile> passiveTiles;
    private int[] fullRowCounter;
    private HashSet<Integer> tileToBeRemoved;
    private ArrayList<Tile> newPassiveTiles;
    private int rowToBeRemoved;
    private int fullRowsAtSameTime;
    private Score score;
    private GameSpeed speed;
    private int gameSpeed;
    
    //pääkonstruktori
    public TetrisGame() {
        this.activeShape = createRandomShape();
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
        this.fullRows = new ArrayList<>();
        this.rowsAboveFullRows = new HashSet<>();
        this.passiveTiles = new ArrayList<>();
        this.fullRowCounter = new int[boardHeight];
        this.tileToBeRemoved = new HashSet<>();
        this.newPassiveTiles = new ArrayList<>();
        this.score = new Score();
        this.speed = new GameSpeed();
    }
    
    //testusta varten tietyllä palikalla alkava konstruktori
    public TetrisGame(int value) {
        this.activeShape = createRandomShape(value);
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
        this.fullRows = new ArrayList<>();
        this.rowsAboveFullRows = new HashSet<>();
        this.passiveTiles = new ArrayList<>();
        this.fullRowCounter = new int[boardHeight];
        this.tileToBeRemoved = new HashSet<>();
        this.newPassiveTiles = new ArrayList<>();
    }
    
    // apumetodeja GameView:lle
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
    public void setActiveShape(Shape activeShape) {
        this.activeShape = activeShape;
    }
    public boolean[][] getActiveShapeCoord() {
        for (Tile activeTile : activeShapeTiles) {
            activeTileCoord[activeTile.getX()][activeTile.getY()] = true;
        }
        return activeTileCoord;
    }
    public void resetActiveShapeCoord() {
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                activeTileCoord[j][i] = false;
            }
        }
    }
    public boolean[][] getPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = true;
        }
        return passiveTileCoord;
    }
    public void resetPassiveTileCoord() {
        for (Tile passiveTile : passiveTiles) {
            passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = false;
        }
    }
    
    // apumetodeja testiluokalle
    public Tile[] getActiveShapeTiles() {
        return activeShapeTiles;
    }
    public Shape getActiveShape() {
        return activeShape;
    }
    
    // luo uuden palikan
    public Shape createRandomShape() {
        Random random = new Random();
        int value = random.nextInt(7);
        switch (value) {
            case 0: return new Shape("L");
            case 1: return new Shape("J");
            case 2: return new Shape("S");
            case 3: return new Shape("Z");
            case 4: return new Shape("O");
            case 5: return new Shape("I");
            default: return new Shape("T");
        }
    }
    
    // testausta varten luo uuden tietyn palikan
    public Shape createRandomShape(int value) {
        switch (value) {
            case 0: return new Shape("L");
            case 1: return new Shape("J");
            case 2: return new Shape("S");
            case 3: return new Shape("Z");
            case 4: return new Shape("O");
            case 5: return new Shape("I");
            default: return new Shape("T");
        }
    }
    
    // liikutetaan aktiivista palikkaa alas (gravitaation vaikutus)
    // tai siirretään se passiivisten joukkoon
    public void moveDown() {
        moveDownPossible = passivesOnTheWay("down") && edgeOnTheWay("down");
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
    
    public void hardDrop() {
        while(passivesOnTheWay("down") && edgeOnTheWay("down")) {
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
    
    // liikutetaan aktiivista palikkaa vasempaan
    public void moveLeft() {
        moveLeftPossible = passivesOnTheWay("left") && edgeOnTheWay("left");
        if (moveLeftPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() - 1);
            }
        }
    }
    
    // liikutetaan aktiivista palikkaa oikealle
    public void moveRight() {
        moveRightPossible = passivesOnTheWay("right") && edgeOnTheWay("right");
        if (moveRightPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() + 1);
            }
        }
    }
    
    // osuuko aktiivinen palikka johonkin reunaan
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
    
    // onko passiivinen palikka aktiivisen tiellä
    private boolean passivesOnTheWay(String direction) {
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
    
    // käännetään palikkaa
    public void rotate() {
        centerX = activeShapeTiles[0].getX();
        centerY = activeShapeTiles[0].getY();
        newCoordinates[0].setX(centerX);
        newCoordinates[0].setY(centerY);
        shapeType = activeShape.getType();
        orientation = activeShape.getOrientation();
        reverse = false;
        // "O"-palikkaa ei käännetä lainkaan
        if (shapeType.equals("O")) {
            return;
        }
        // "I", "Z" ja "S" -palikoilla on vain kaksi kääntötilaa,
        // joten ne kääntyvät vuorotellen myötä- ja vastapäivään
        if ((shapeType.equals("I") || shapeType.equals("Z") || shapeType.equals("S")) && orientation == 2) {
            reverse = true;
        }
        getCoordForRotation();
        // kääntäessä pidetään yllä, monesko kääntötila on päällä
        // tällä varmistetaan, edellä kuvattujen kaksivaiheisten palikoiden
        // kääntyminen edes-takaisin
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
    
    // lasketaan uudet koordinaatit aktiiviselle palikalle
    private void getCoordForRotation() {
        for (int i = 1; i < activeShapeTiles.length; i++) {
            tileType = getTileType(activeShapeTiles[i]);
            if (tileType.equals("Side")) {
                coordSideTile(activeShapeTiles[i]);
            }
            if (tileType.equals("DoubleSide")) {
                coordDoubleSideTile(activeShapeTiles[i]);
            }
            if (tileType.equals("Corner")) {
                coordCornerTile(activeShapeTiles[i]);
            }
            newCoordinates[i].setX(nextX);
            newCoordinates[i].setY(nextY);
        }
    }
    
    // onko tiili keskipalikan sivulla, nurkassa vai
    // "I"-palikan erikoispalikka, joka on keskipalikan sivulla kahden päässä
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
    
    // uusi koordinaatti sivutiilille nykyisen sijainnin perusteella
    // vertaa siis tiilien sijaintia keskipalikan nykysijaintiin
    private void coordSideTile(Tile t) {
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
    
    // uusi koordinaatti "I"-palikan erikoistiilelle
    // nykyisen sijainnin perusteella
    // vertaa siis tiilien sijaintia keskipalikan nykysijaintiin
    private void coordDoubleSideTile(Tile t) {
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
    
    // uusi koordinaatti nurkkatiilille nykyisen sijainnin perusteella
    // vertaa siis tiilien sijaintia keskipalikan nykysijaintiin
    private void coordCornerTile(Tile t) {
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
    
    // onko palikkaa mahdollista kääntää
    private boolean rotatePossible() {
        for (int i = 1; i < newCoordinates.length; i++) {
            // osuuko reunoihin
            if (newCoordinates[i].getX() > (boardWidth - 1) || newCoordinates[i].getX() < 0 || newCoordinates[i].getY() > (boardHeight - 1) || newCoordinates[i].getY() < 0) {
                return false;
            }
            // onko passiivinen palikka uuden sijainnin tiellä
            for (Tile passiveTile : passiveTiles) {
                if (passiveTile.getX() == newCoordinates[i].getX() && passiveTile.getY() == newCoordinates[i].getY()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean checkForFullRows() {
        for (int j = 0; j < boardHeight; j++) {
            fullRowCounter[j] = 0;
        }
        for (Tile passiveTile : passiveTiles) {
            fullRowCounter[passiveTile.getY()] ++;
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
    
    public int deleteRows() {
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
    
    public void getFullRowTiles() {
        for (int i = 0; i < passiveTiles.size(); i++) {
            if (passiveTiles.get(i).getY() == rowToBeRemoved) {
                tileToBeRemoved.add(i);
            }
        }
    }
    
    public void getRowsAboveFullRows() {
        for (Tile passiveTile : passiveTiles) {
            if (passiveTile.getY() < rowToBeRemoved) {
                rowsAboveFullRows.add(passiveTile.getY());
            }
        }
    }
    
    public void deleteFullRows() {
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
    
    public void dropRowsAboveFullRows() {
        for (int i = 0; i < passiveTiles.size(); i++) {
            if (rowsAboveFullRows.contains(passiveTiles.get(i).getY()) && passivesOnTheWay("down") && edgeOnTheWay("down")) {
                passiveTiles.get(i).setY(passiveTiles.get(i).getY() + 1);
            }
        }
        rowsAboveFullRows.clear();
    }
    
    public int addPoints() {
        switch(fullRowsAtSameTime) {
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
    
    public int levelUp() {
        if (score.getLinesCleared() >= (score.getLevel() + 1) * 10 && score.getLevel() <= score.getMaxLevel()) {
            score.setLevel(score.getLevel() + 1);
        }
        if (score.getLevel() == 10) {
            score.setMaxLevelReached(true);
        }
        return score.getLevel();
    }
    
    public int gameSpeed() {
        speed.setGameSpeed(score.getLevel());
        return speed.getSpeed();
    }
}
