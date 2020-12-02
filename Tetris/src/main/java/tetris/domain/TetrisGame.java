package tetris.domain;

import java.util.*;

public class TetrisGame {
    
    public final int boardHeight = 20;
    public final int boardWidth = 10;
    private Shape activeShape;
    private Tile[] activeShapeTiles;
    private ArrayList<Tile[]> passiveShapes;
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
    private int counter;
    
    //pääkonstruktori
    public TetrisGame() {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape();
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
    }
    
    //testusta varten tietyllä palikalla alkava konstruktori
    public TetrisGame(int value) {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape(value);
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0, 0), new Tile(0, 0), new Tile(0, 0), new Tile(0, 0)};
        this.passiveTileCoord = new boolean[boardWidth][boardHeight];
        this.activeTileCoord = new boolean[boardWidth][boardHeight];
    }
    
    // apumetodeja GameView:lle
    public void setActiveShape(Shape activeShape) {
        this.activeShape = activeShape;
    }
    public boolean[][] getPassiveTileCoord() {
        return passiveTileCoord;
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
    public boolean gameOver() {
        for (Tile activeShapeTile : activeShapeTiles) {
            for (Tile[] tiles : passiveShapes) {
                for (Tile tile : tiles) {
                    if (activeShapeTile.getY() < 2 && activeShapeTile.getX() == tile.getX() && activeShapeTile.getY() == tile.getY() - 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    // apumetodeja omalle luokalle ja testiluokalle
    public ArrayList<Tile[]> getPassiveShapes() {
        return passiveShapes;
    }
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
                passiveShapes.add(activeShapeTiles);
            }
            activeShape = createRandomShape();
            activeShapeTiles = activeShape.getTiles();
        }
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
            for (Tile[] passiveTiles : passiveShapes) {
                for (Tile tile : passiveTiles) {
                    if (direction.equals("right")) {
                        passiveTileDirectionX = tile.getX() - 1;
                        passiveTileDirectionY = tile.getY();
                    } else if (direction.equals("left")) {
                        passiveTileDirectionX = tile.getX() + 1;
                        passiveTileDirectionY = tile.getY();
                    } else if (direction.equals("down")) {
                        passiveTileDirectionX = tile.getX();
                        passiveTileDirectionY = tile.getY() - 1;
                    }
                    if (activeShapeTile.getX() == passiveTileDirectionX && activeShapeTile.getY() == passiveTileDirectionY) {
                        return false;
                    }
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
            for (Tile[] passiveTiles : passiveShapes) {
                for (Tile passiveTile : passiveTiles) {
                    if (passiveTile.getX() == newCoordinates[i].getX() && passiveTile.getY() == newCoordinates[i].getY()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    // näyttää tekstikäyttöliittymässä tietoa passiivisten tiilien sijainnista
    // tarkistaa jokaisella aktiivisen palikan putoamisella
    // löytyykö täysiä rivejä passiivisten tiilien joukosta
    // löytyessä tulostaa täyden rivin viereen "rivi täynnä!" -tekstin
    public void checkForFullRows() {
        for (Tile[] passiveTiles : passiveShapes) {
            for (Tile passiveTile : passiveTiles) {
                passiveTileCoord[passiveTile.getX()][passiveTile.getY()] = true;
            }
        }
        for (int i = 0; i < boardHeight; i++) {
            counter = 0;
            for (int j = 0; j < boardWidth; j++) {
                if (passiveTileCoord[j][i]) {
                    System.out.print("X ");
                    counter++;
                } else {
                    System.out.print("O ");
                }
                if (counter == 10) {
                    System.out.print("rivi täynnä!");
                }
            }
            System.out.println("");
        }
        System.out.println("");
        System.out.println("");
    }
}
