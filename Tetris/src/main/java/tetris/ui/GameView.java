package tetris.ui;

//Huom! Tämä ei ole varsinainen Main-tiedosto, vaan tetris.main.Main

import javafx.application.Application;
import javafx.stage.Stage;
import tetris.domain.Shape;
import tetris.domain.TetrisGame;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tetris.domain.Tile;

public class GameView extends Application {
    
    static final int BOARD_WIDTH = 10;
    static final int BOARD_HEIGHT = 18;
    static final int TILE_SIZE = 40;
    private TetrisGame game;
    private Shape currentShape;
    private int counter;
    private GridPane tetrisGrid;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() {
        game = new TetrisGame();
        tetrisGrid = new GridPane();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(tetrisGrid);
        drawGameState();
        createGravity();
        
        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.show();
    }
    
    @Override
    public void stop() {
        System.out.println("sovellus sulkeutuu");
    }
    
    //luo painovoiman, eli saa palikat putoamaan kohti alareunaa
    public void createGravity() {
        int speed = 800;
        counter = 0;
        SequentialTransition shapeFall = new SequentialTransition();
        PauseTransition shapePause = new PauseTransition(Duration.millis(speed));
        shapePause.setOnFinished(event -> {
            if (counter == 0 || counter == 16) {
                currentShape = game.createRandomShape();
                Tile[] tiles = currentShape.getTiles();
                game.addNewShapeToBoard(tiles);
                System.out.println("uusi muoto: " + currentShape.getType());
                System.out.println("");
                game.currentGameState();
                drawGameState();
                System.out.println("");
                counter = 0;
            }
            System.out.println("muoto siirtyy!");
            game.moveDown(currentShape);
            game.currentGameState();
            drawGameState();
            System.out.println("");
            counter++;
        });
        shapeFall.getChildren().add(shapePause);
        shapeFall.setCycleCount(Timeline.INDEFINITE);
        shapeFall.play();
    }
    
    //apuna pelialueen ja liikkuvan palikan piirtämisessä
    public void drawGameState() {
        tetrisGrid.setPrefSize(BOARD_WIDTH*TILE_SIZE, BOARD_WIDTH*TILE_SIZE);
        tetrisGrid.setVgap(3);
        tetrisGrid.setHgap(3);
        tetrisGrid.setPadding(new Insets(3,3,3,3));
        tetrisGrid.getChildren().clear();
        boolean[][] shapes = game.getShapes();
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                if (shapes[x][y] == true) {
                    rect.setFill(Color.GREEN);    
                } else {
                    rect.setFill(Color.LIGHTGRAY);
                }
                tetrisGrid.add(rect, x, y);
            }
        }
    }
}
