package tetris.domain;

import java.util.Random;

public class TetrisGame {
    
    static final int BOARD_HEIGHT = 18;
    static final int BOARD_WIDTH = 10;
    private int[][] board;
    private boolean[][] shapes;
        
    public TetrisGame() {
        this.board = new int[BOARD_WIDTH][BOARD_HEIGHT];
        this.shapes = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    }
    
    //luo uuden palikan
    public Shape createRandomShape() {
        Random random = new Random();
        int value = random.nextInt(7);
        switch(value) {
            case 0: return new Shape("L");
            case 1: return new Shape("J");
            case 2: return new Shape("S");
            case 3: return new Shape("Z");
            case 4: return new Shape("O");
            case 5: return new Shape("I");
            default: return new Shape("T");
        }
    }
    
    //lisää uuden palikan pelialueen yläreunaan
    public void addNewShapeToBoard(Tile[] tiles) {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                board[x][y] = 0;
                shapes[x][y] = false;
            }
        }
        for (int i = 0; i < tiles.length; i++) {
            int x = tiles[i].getX();
            int y = tiles[i].getY();
            board[x][y] = 1;
        }
    }
    
    //laskee palikalle uuden kohdan, kun se on tippunut yhden alaspäin
    public void moveDown(Shape shape) {
        Tile[] tiles = shape.getTiles();
        for (int i = 0; i < tiles.length; i++) {
            tiles[i].setY(tiles[i].getY()+1);
        }
        this.addNewShapeToBoard(tiles);
    }

    public boolean[][] getShapes() {
        return shapes;
    }
    
    //avustaa tekstikäyttöliittymää, käy läpi palikan nykyisen sijainnin
    public void currentGameState() {
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                if (this.board[x][y] != 0) {
                    shapes[x][y] = true;
                    System.out.println("kohta " + x + "," + y + ": " + this.board[x][y]);
                }
            }
        }
    }

}
