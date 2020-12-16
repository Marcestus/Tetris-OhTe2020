package tetris.domain;

/**
 * Yksittäistä tiiltä kuvaava luokka.
 * Tetris-palikat rakentuvat neljästä tiilestä, joilla on omat x- ja y- akselin suuntaiset koordinaatit.
 */

public class Tile {

    private int x;
    private int y;
    
    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }
    
    public void setX(int x) {
        this.x = x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setY(int y) {
        this.y = y;
    }
}
