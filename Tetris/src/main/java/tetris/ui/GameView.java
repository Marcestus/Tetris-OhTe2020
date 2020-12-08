package tetris.ui;

import javafx.application.Application;
import javafx.stage.Stage;
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
import javafx.scene.text.Font;
import javafx.util.Duration;
import tetris.domain.TetrisGame;

/**
 * Graafista käyttöliittymää hallitseva luokka.
 */

public class GameView extends Application {
    static final int tileSize = 40;
    private int boardWidth;
    private int boardHeight;
    private TetrisGame game;
    private GridPane tetrisGrid;
    private Scene scene;
    private boolean[][] activeShapeLocation;
    private boolean[][] passiveShapesLocation;
    private HBox gameScene;
    private VBox infoScreen;
    private String gameStatusText;
    private int currentLinesCleared;
    private int currentPoints;
    private int currentLevel;
    private boolean gameOver;
    private int currentGameSpeed;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        game = new TetrisGame();
        boardWidth = game.getBoardWidth();
        boardHeight = game.getBoardHeight();
        activeShapeLocation = new boolean[boardWidth][boardHeight];
        passiveShapesLocation = new boolean[boardWidth][boardHeight];
    }

    @Override
    public void start(Stage stage) throws Exception {
        tetrisGrid = new GridPane();
        tetrisGrid.setPrefSize(boardWidth * tileSize, boardHeight * tileSize);
        tetrisGrid.setAlignment(Pos.CENTER);
        tetrisGrid.setVgap(3);
        tetrisGrid.setHgap(3);
        tetrisGrid.setPadding(new Insets(3, 3, 3, 3));
        
        infoScreen = new VBox();
        infoScreen.setAlignment(Pos.CENTER_LEFT);
        infoScreen.setSpacing(25);
        
        gameScene = new HBox();
        gameScene.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        gameScene.setAlignment(Pos.CENTER);
        gameScene.setSpacing(25);
        
        scene = new Scene(gameScene);
        
        refreshGameScene();
        currentGameSpeed = game.getGameSpeed().getSpeed();
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
        PauseTransition shapePause = new PauseTransition(Duration.millis(currentGameSpeed));
        shapePause.setOnFinished(event -> {
            if (game.gameOver()) {
                gameOver = true;
                gameStatusText = "Game over!";
                shapeMove.stop();
                refreshGameScene();
            } else {
                gameOver = false;
                gameStatusText = "Game on!";
                game.moveDown();
                if (game.checkForFullRows()) {
                    game.deleteRowsAndDropRowsAboveFullRows();
                    game.setScoreAndSpeed();
                    currentPoints = game.getScore().getPoints();
                    currentLevel = game.getScore().getLevel();
                    currentLinesCleared = game.getScore().getLinesCleared();
                    currentGameSpeed = game.getGameSpeed().getSpeed();
                }
                refreshGameScene();
            }
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
            } else if (key.getCode().equals(KeyCode.UP)) {
                game.rotate();
            } else if (key.getCode().equals(KeyCode.DOWN)) {
                game.hardDrop();
            }
            refreshGameScene();
        });
    }
    
    private void refreshGameScene() {
        infoScreen.getChildren().clear();
        gameScene.getChildren().clear();
        tetrisGrid.getChildren().clear();
        refreshTetrisGrid();
        refreshInfoScreen();
        gameScene.getChildren().addAll(tetrisGrid, infoScreen);
    }
    
    private void refreshTetrisGrid() {
        game.resetActiveShapeCoord();
        activeShapeLocation = game.getActiveShapeCoord();
        game.resetPassiveTileCoord();
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 2; y < boardHeight; y++) {
                passiveShapesLocation[x][y] = false;
            }
        }
        passiveShapesLocation = game.getPassiveTileCoord();
        colorTiles();
    }
    
    private void colorTiles() {
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 2; y < boardHeight; y++) {
                Rectangle rect = new Rectangle(tileSize, tileSize);
                if (activeShapeLocation[x][y]) {
                    rect.setFill(Color.GREEN);
                } else if (passiveShapesLocation[x][y]) {
                    rect.setFill(Color.ORANGE);
                } else {
                    rect.setFill(Color.LIGHTGRAY);
                }
                tetrisGrid.add(rect, x, y);
            }
        }
    }
    
    private void refreshInfoScreen() {
        Label logo = new Label("T E T R I S");
        logo.setFont(Font.font("Arial", 24));
        
        Label instructionsText = new Label("Joka kymmenes poistettu rivi"
                + "\n" + "kasvattaa pelinopeutta"
                + "\n"
                + "\n" + "Liikuta palikkaa nuolinäppäimillä:"
                + "\n"
                + "\n" + "LEFT - palikka liikkuu vasempaan"
                + "\n" + "RIGHT - palikka liikkuu oikealle"
                + "\n" + "UP - palikka kääntyy"
                + "\n" + "DOWN - hard drop"
                + "\n" + "  eli palikka putoaa kohtisuoraan"
                + "\n" + "  alas paikoilleen");
        
        Label gameStatus = new Label("Game status: " + gameStatusText);
        
        VBox currentScore = new VBox();
        currentScore.setSpacing(10);
        Label lines = new Label("Lines: " + currentLinesCleared);
        Label points = new Label("Points: " + currentPoints);
        Label level = new Label("Level: " + currentLevel + " / 10");
        currentScore.getChildren().addAll(lines, points, level);
        
        Label ending = new Label();
        if (gameOver && currentPoints > 0) {
            ending = new Label("Onneksi olkoon,"
                + "\n" + "sait " + currentPoints + " pistettä!"
                + "\n"
                + "\n" + "Pelin leaderboardin" 
                + "\n" + "luonti on vielä kesken,"
                + "\n" + "joten pisteitä ei voi"
                + "\n" + "ikävä kyllä vertailla...");
        } else if (gameOver) {
            ending = new Label("Harmin paikka,"
                + "\n" + "et saanut yhtään pisteitä!"
                + "\n"
                + "\n" + "Pelin leaderboardin" 
                + "\n" + "luonti on vielä kesken,"
                + "\n" + "joten pisteitä ei voi"
                + "\n" + "vielä vertailla...");
        }
        
        infoScreen.getChildren().addAll(logo, instructionsText, gameStatus, currentScore, ending);
    }
}
