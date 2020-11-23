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
    
    public TetrisGame() {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape();
        this.activeShapeTiles = activeShape.getTiles();
    }
    
    //testusta varten luodaan aina ensimmäiseksi palikaksi tietty palikka
    public TetrisGame(int value) {
        this.passiveShapes = new ArrayList<>();
        this.activeShape = createRandomShape(value);
        this.activeShapeTiles = activeShape.getTiles();
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
    
    // gravitaation vaikutus aktiviseen palikkaan
    public void moveDown() {
        // tutkitaan, pääseekö palikka putoamaan alaspäin
        moveDownPossible = canActiveShapeMove("down") && isActiveShapeNearAnyEdge("down");
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
        moveLeftPossible = canActiveShapeMove("left") && isActiveShapeNearAnyEdge("left");
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
        moveRightPossible = canActiveShapeMove("right") && isActiveShapeNearAnyEdge("right");
        // jos on, liikutetaan
        if (moveRightPossible) {
            for (Tile activeShapeTile : activeShapeTiles) {
                activeShapeTile.setX(activeShapeTile.getX() + 1);
            }
        }
        // jos ei, ei tehdä mitään
    }
    
    private boolean isActiveShapeNearAnyEdge(String direction) {
        // tutkitaan, osuuko aktiivinen palikka johonkin reunaan
        for (Tile activeShapeTile : activeShapeTiles) {
            // valitaan käytettävä reuna
            if (direction.equals("right")) {
                edge = boardWidth - 1;
            } else if (direction.equals("left")) {
                edge = 0;
            } else if (direction.equals("down")) {
                edge = boardHeight - 1;
                if (activeShapeTile.getY() == (edge)) {
                    return false;
                }
            }
            // jos valittuun reunaan osutaan, ei voida liikkua
            if (activeShapeTile.getX() == (edge)) {
                return false;
            }
            
        }
        // jos valittuun reunaan ei osuta, voidaan liikkua
        return true;
    }
    
    private boolean canActiveShapeMove(String direction) {
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
