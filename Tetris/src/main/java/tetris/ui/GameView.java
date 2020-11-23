package tetris.ui;

//Huom! Tämä ei ole varsinainen Main-tiedosto, vaan tetris.main.Main
//(jotta jar, maven ja javaFX toimivat yhteen)

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import tetris.domain.TetrisGame;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import tetris.domain.Tile;

public class GameView extends Application {
    
    static final int TILE_SIZE = 40;
    static final int GAME_SPEED = 600;
    private int BOARD_WIDTH;
    private int BOARD_HEIGHT;
    private TetrisGame game;
    private GridPane tetrisGrid;
    private Scene scene;
    private Tile[] activeShape;
    private boolean[][] activeShapeLocation;
    private ArrayList<Tile[]> passiveShapes;
    private boolean[][] passiveShapesLocation;
    private Label instructionsText;
    private Label gameStatusText;
    private HBox gameArea;
    private VBox infoScreen;
    
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        
        game = new TetrisGame();
        BOARD_WIDTH = game.BOARD_WIDTH;
        BOARD_HEIGHT = game.BOARD_HEIGHT;
        activeShape = new Tile[4];
        activeShapeLocation = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
        passiveShapes = new ArrayList<>();
        passiveShapesLocation = new boolean[BOARD_WIDTH][BOARD_HEIGHT];
    }

    @Override
    public void start(Stage stage) throws Exception {
        tetrisGrid = new GridPane();
        tetrisGrid.setPrefSize(BOARD_WIDTH*TILE_SIZE, (BOARD_HEIGHT)*TILE_SIZE);
        tetrisGrid.setAlignment(Pos.CENTER);
        tetrisGrid.setVgap(3);
        tetrisGrid.setHgap(3);
        tetrisGrid.setPadding(new Insets(3,3,3,3));
        
        instructionsText = new Label("Liikuta palikkaa nuolinäppäimillä:"
                + "\n" + "LEFT - palikka liikkuu vasempaan"
                + "\n" + "RIGHT - palikka liikkuu oikealle");
        
        infoScreen = new VBox();
        infoScreen.setAlignment(Pos.CENTER);
        infoScreen.setSpacing(25);
        
        gameArea = new HBox();
        gameArea.setPrefSize(BOARD_WIDTH*TILE_SIZE+350, BOARD_HEIGHT*TILE_SIZE+50);
        gameArea.setAlignment(Pos.CENTER);
        gameArea.setSpacing(25);
        
        scene = new Scene(gameArea);
        
        drawGameState();
        createGravity();
        createControls();
        
        stage.setScene(scene);
        stage.setTitle("T E T R I S");
        stage.show();
    }
    
    @Override
    public void stop() {
        System.out.println("sovellus sulkeutuu");
    }
    
    private void createGravity() {
        SequentialTransition shapeMove = new SequentialTransition();
        PauseTransition shapePause = new PauseTransition(Duration.millis(GAME_SPEED));
        shapePause.setOnFinished(event -> {
            if (game.gameOver()) {
                shapeMove.stop();
                drawGameState();
            }
            game.moveDown();
            drawGameState();
        });
        shapeMove.getChildren().add(shapePause);
        shapeMove.setCycleCount(Timeline.INDEFINITE);
        shapeMove.play();
    }
    
    private void createControls() {
        scene.setOnKeyPressed((key) -> {
            if (key.getCode().equals(KeyCode.LEFT)) {
                game.moveLeft();
            } else if (key.getCode().equals(KeyCode.RIGHT)) {
                game.moveRight();
            }
            drawGameState();
        });
    }
    
    private void drawGameState() {
        // nollataan pelialue
        infoScreen.getChildren().clear();
        gameArea.getChildren().clear();
        tetrisGrid.getChildren().clear();
        // nollataan aktiivisen palikan koordinaatit
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 2; y < BOARD_HEIGHT; y++) {
                activeShapeLocation[x][y] = false;
            }
        }
        // lisätään aktiivisen palikan koordinaatit listaan
        activeShape = game.getActiveShapeTiles();
        for (Tile activeShapeTile : activeShape) {
            activeShapeLocation[activeShapeTile.getX()][activeShapeTile.getY()] = true;
        }
        // lisätään passiivisten palikoiden koordinaatit listaan
        passiveShapes = game.getPassiveShapes();
        for (Tile[] tiles : passiveShapes) {
            for (Tile passiveShapeTile : tiles) {
                passiveShapesLocation[passiveShapeTile.getX()][passiveShapeTile.getY()] = true;
            }
        }
        // väritetään neliöt edellä luotujen listojen mukaan
        colorTiles();
        // pidetään yllä gameStatus-tekstiä
        if (game.gameOver()) {
            gameStatusText = new Label("Game status:" + "\n" + "Game over!");
        } else {
            gameStatusText = new Label("Game status:" + "\n" + "Game on!");
        }
        // lisätään kaikki komponentit pelinäkymään
        infoScreen.getChildren().addAll(instructionsText,gameStatusText);
        gameArea.getChildren().addAll(tetrisGrid,infoScreen);
    }
    
    private void colorTiles() {
        // väritetään neliöt edellä luotujen listojen mukaan
        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 2; y < BOARD_HEIGHT; y++) {
                Rectangle rect = new Rectangle(TILE_SIZE, TILE_SIZE);
                if (activeShapeLocation[x][y]) {
                    rect.setFill(Color.GREEN);
                } else if (passiveShapesLocation[x][y]) {
                    rect.setFill(Color.LIGHTGREEN);
                } else {
                    rect.setFill(Color.LIGHTGRAY);
                }
                tetrisGrid.add(rect, x, y);
            }
        }
    }
}
