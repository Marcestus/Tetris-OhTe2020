package tetris.domain;

import java.util.ArrayList;
import java.util.Random;

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
    
    public TetrisGame() {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape();
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0,0), new Tile(0,0), new Tile(0,0), new Tile(0,0)};
    }
    
    //testusta varten luodaan aina ensimmäiseksi palikaksi tietty palikka
    public TetrisGame(int value) {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape(value);
        this.activeShapeTiles = activeShape.getTiles();
        this.centerX = activeShapeTiles[0].getX();
        this.centerY = activeShapeTiles[0].getY();
        this.shapeType = activeShape.getType();
        this.newCoordinates = new Tile[]{new Tile(0,0), new Tile(0,0), new Tile(0,0), new Tile(0,0)};
    }

    //luo uuden palikan
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
    
    //testausta varten random pois käytöstä, jotta saadaan haluttu palikka
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
    
    // onko game over eli jääkö aktiivinen palikka yläreunan yläpuolelle
    public boolean gameOver() {
        for (Tile activeShapeTile : activeShapeTiles) {
            // osuu alla olevaan (passiiviseen) palikkaan, ei pääse alaspäin
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
    
    // gravitaation vaikutus aktiiviseen palikkaan
    public void moveDown() {
        // tutkitaan, pääseekö palikka putoamaan alaspäin
        moveDownPossible = isPassiveShapeOnTheWay("down") && edgeOnTheWay("down");
        // jos pääsee, liikutetaan aktiivista palikkaa alaspäin
        if (moveDownPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setY(activeShapeTile.getY() + 1);
            }
        } else {
            // jos ei, siirretään aktiivinen palikka passiivisten joukkoon
            for (Tile activeShapeTile : activeShapeTiles) {
                passiveShapes.add(activeShapeTiles);
            }
            activeShape = createRandomShape();
            activeShapeTiles = activeShape.getTiles();
        }
    }
    
    // liikutetaan aktiivista palikkaa vasempaan
    public void moveLeft() {
        // onko liikuttaminen mahdollista
        moveLeftPossible = isPassiveShapeOnTheWay("left") && edgeOnTheWay("left");
        // jos on, liikutetaan
        if (moveLeftPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() - 1);
            }
        }
        // jos ei, ei tehdä mitään
    }
    
    // liikutetaan aktiivista palikkaa oikealle
    public void moveRight() {
        // onko liikuttaminen mahdollista
        moveRightPossible = isPassiveShapeOnTheWay("right") && edgeOnTheWay("right");
        // jos on, liikutetaan
        if (moveRightPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() + 1);
            }
        }
        // jos ei, ei tehdä mitään
    }
    
    private boolean edgeOnTheWay(String direction) {
        // tutkitaan, osuuko aktiivinen palikka johonkin reunaan
        for (Tile activeShapeTile : activeShapeTiles) {
            // valitaan käytettävä reuna
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
            // jos valittuun reunaan osutaan, ei voida liikkua
            if (activeShapeTile.getX() == edge) {
                return false;
            }
            
        }
        // jos valittuun reunaan ei osuta, voidaan liikkua
        return true;
    }
    
    private boolean isPassiveShapeOnTheWay(String direction) {
        // tutkitaan, osuuko aktiivinen palikka passiivisiin palikoihin
        for (Tile activeShapeTile : activeShapeTiles) {
            for (Tile[] tiles : passiveShapes) {
                for (Tile tile : tiles) {
                    // valitaan tarkasteltava suunta
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
                    // jos passiivisiin palikoihin osutaan, ei voida liikkua
                    if (activeShapeTile.getX() == passiveTileDirectionX && activeShapeTile.getY() == passiveTileDirectionY) {
                        return false;
                    }
                }
            }
        }
        // jos passiivisiin palikoihin ei osuta, voidaan liikkua
        return true;
    }
    
    public void rotate() {
        //palikan kääntäminen
        centerX = activeShapeTiles[0].getX();
        centerY = activeShapeTiles[0].getY();
        newCoordinates[0].setX(centerX);
        newCoordinates[0].setY(centerY);
        shapeType = activeShape.getType();
        orientation = activeShape.getOrientation();
        reverse = false;
        //"O"-palikkaa ei käännetä lainkaan
        if (shapeType.equals("O")) {
            return;
        }
        //näillä palikoilla on vain kaksi kääntötilaa, joten ne kääntyvät
        //vuorotellen myötä- ja vastapäivään
        if ((shapeType.equals("I") || shapeType.equals("Z") || shapeType.equals("S")) && orientation == 2) {
            reverse = true;
        }
        getCoordinatesForRotation();
        //kääntäessä pidetään yllä, monesko kääntötila on päällä
        //tällä varmistetaan, edellä kuvattujen kaksivaiheisten palikoiden
        //kääntyminen edes-takaisin
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
    
    private void getCoordinatesForRotation() {
        //lasketaan uudet koordinaatit aktiiviselle palikalle
        for (int i = 1; i < activeShapeTiles.length; i++) {
            tileType = determineTileType(activeShapeTiles[i]);
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
    
    private String determineTileType(Tile t) {
        //onko tiili keskipalikan sivulla, nurkassa vai
        //"I"-palikan erikoispalikka, joka on keskipalikan sivulla kahden päässä
        if ((t.getX() == centerX && (t.getY() == centerY + 1 || t.getY() == centerY - 1) ||
                (t.getX() == centerX + 1 || t.getX() == centerX - 1) && t.getY() == centerY)) {
            return "Side";
        } else if (shapeType.equals("I")) {
            return "DoubleSide";
        } else {
            return "Corner";
        }
    }
    
    private void coordSideTile(Tile t) {
        //uusi koordinaatti sivutiilille nykyisen sijainnin perusteella
        if (t.getX() == centerX + 1) {
            //oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getY() == centerY + 1) {
            //alapuolella
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = centerY;
        } else if (t.getX() == centerX - 1) {
            //vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getY() == centerY - 1) {
            //yläpuolella
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = centerY;
        }
    }
    
    private void coordDoubleSideTile(Tile t) {
        //uusi koordinaatti "I"-palikan erikoistiilelle
        //nykyisen sijainnin perusteella
        if (t.getX() == centerX + 2) {
            //oikealla puolella
            nextX = centerX;
            nextY = reverse ? centerY - 2 : centerY + 2;
        } else if (t.getY() == centerY + 2) {
            //alapuolella
            nextX = reverse ? centerX + 2 : centerX - 2;
            nextY = centerY;
        } else if (t.getX() == centerX - 2) {
            //vasemmalla puolella
            nextX = centerX;
            nextY = reverse ? centerY + 2 : centerY - 2;
        } else if (t.getY() == centerY - 2) {
            //yläpuolella
            nextX = reverse ? centerX - 2 : centerX + 2;
            nextY = centerY;
        }
    }
    
    private void coordCornerTile(Tile t) {
        //uusi koordinaatti nurkkatiilille nykyisen sijainnin perusteella
        if (t.getX() == centerX - 1 && t.getY() == centerY - 1) {
            //oikea ylä
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY + 1 : centerY - 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY - 1) {
            //oikea ala
            nextX = reverse ? centerX - 1 : centerX + 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX + 1 && t.getY() == centerY + 1) {
            //vasen ala
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = reverse ? centerY - 1 : centerY + 1;
        } else if (t.getX() == centerX - 1 && t.getY() == centerY + 1) {
            //vasen ylä
            nextX = reverse ? centerX + 1 : centerX - 1;
            nextY = reverse ? centerY + 1 : centerY - 1;
        }
    }
    
    private boolean rotatePossible() {
        for (int i = 1; i < newCoordinates.length; i++) {
            //osuuko reunoihin
            if (newCoordinates[i].getX() > (boardWidth-1) || newCoordinates[i].getX() < 0 || newCoordinates[i].getY() > (boardHeight-1) || newCoordinates[i].getY() < 0) {
                return false;
            }
            //onko passiivinen palikka uuden sijainnin tiellä
            for (Tile[] tiles : passiveShapes) {
                for (Tile passiveTile : tiles) {
                    if (passiveTile.getX() == newCoordinates[i].getX() && passiveTile.getY() == newCoordinates[i].getY()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    
    public ArrayList<Tile[]> getPassiveShapes() {
        return passiveShapes;
    }

    public Tile[] getActiveShapeTiles() {
        return activeShapeTiles;
    }
    
    public Shape getActiveShape() {
        return activeShape;
    }

    public void setActiveShape(Shape activeShape) {
        this.activeShape = activeShape;
    }

}
