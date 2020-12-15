package tetris.ui;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;
import tetris.domain.TetrisGame;
import tetris.domain.Leaderboard;

/**
 * Graafista käyttöliittymää hallitseva luokka.
 */

public class GameView extends Application {
    private Stage stage;
    private Scene menuScene;
    private Scene gameScene;
    private Scene highScoreScene;
    private Scene leaderboardScene;
    private BorderPane menuPane;
    private BorderPane gamePane;
    private BorderPane highScorePane;
    private BorderPane leaderboardPane;
    private TetrisGame game;
    private Leaderboard leaderboard;
    private boolean gameOver;
    private int currentHighScore;
    private int boardWidth;
    private int boardHeight;
    private GridPane tetrisGrid;
    static final int tileSize = 40;
    private boolean[][] activeShapeLocation;
    private boolean[][] passiveShapesLocation;
    private VBox gameInfoScreen;
    private int currentLinesCleared;
    private int currentPoints;
    private int currentLevel;
    private int currentGameSpeed;
    private boolean[][] tetrisLogoLetters;
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() {
        resetGame();
    }
    
    @Override
    public void start(Stage unUsedStage) throws Exception {
        //menuScene
        tetrisLogoLetters = new boolean[21][5];
        getLetters();
        GridPane tetrisLogo = new GridPane();
        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j <= 4; j++) {
                Rectangle logoRect = new Rectangle(15, 15);
                if (tetrisLogoLetters[i][j]) {
                    logoRect.setFill(Color.GREEN);
                } else {
                    logoRect.setFill(Color.WHITESMOKE);
                }
                tetrisLogo.add(logoRect,i,j);
            }
        }
        tetrisLogo.setVgap(1);
        tetrisLogo.setHgap(1);
        tetrisLogo.setAlignment(Pos.CENTER);
        tetrisLogo.setPadding(new Insets(3, 3, 3, 3));
        Label instructionsText = new Label(
                         "Pelin tavoite: Muodosta putoavista palikoista täysiä vaakasuoria rivejä."
                + "\n" + "Täydet rivit poistuvat ja saat niistä pisteitä."
                + "\n" + "Mitä useamman rivin poistat kerralla, sitä enemmän saat pisteitä."
                + "\n" + "Joka kymmenes poistettu rivi kasvattaa palikoiden putoamisnopeutta."
                + "\n" + "Parhaat viisi tulosta pääsevät leaderboardiin!"
                + "\n"
                + "\n" + "Liikuta palikkaa nuolinäppäimillä:"
                + "\n" + "     LEFT - palikka liikkuu vasempaan"
                + "\n" + "     RIGHT - palikka liikkuu oikealle"
                + "\n" + "     UP - palikka kääntyy"
                + "\n" + "     DOWN - hard drop eli palikka putoaa suoraan alas paikoilleen"
                + "\n"
                + "\n" + "Onnea matkaan!");
        instructionsText.setFont(Font.font("Arial", 16));
        Button startGame = new Button("Start game");
        Button menuToLeaderboard = new Button("Leaderboard");
        HBox menuButtons = new HBox();
        menuButtons.setAlignment(Pos.CENTER);
        menuButtons.setSpacing(15);
        menuButtons.getChildren().addAll(startGame, menuToLeaderboard);
        startGame.setOnAction(e-> {
            if (gameOver) {
                resetGame();
            }
            stage.setScene(gameScene);
            updateGameScene();
            currentGameSpeed = game.getGameSpeed().getSpeed();
            createControls();
            createGravity();
        });
        menuToLeaderboard.setOnAction(e-> {
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        });
        menuPane = new BorderPane();
        menuPane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        menuPane.setPadding(new Insets(175,100,175,100));
        menuPane.setTop(tetrisLogo);
        menuPane.setCenter(instructionsText);
        menuPane.setBottom(menuButtons);
        BorderPane.setAlignment(tetrisLogo, Pos.TOP_CENTER);
        BorderPane.setAlignment(menuButtons, Pos.BOTTOM_CENTER);
        menuScene = new Scene(menuPane);
        stage = new Stage();
        stage.setTitle("T E T R I S");
        stage.setScene(menuScene);
        stage.show();
        
        //gameScene
        tetrisGrid = new GridPane();
        tetrisGrid.setPrefSize(boardWidth * tileSize, boardHeight * tileSize);
        tetrisGrid.setAlignment(Pos.CENTER);
        tetrisGrid.setVgap(3);
        tetrisGrid.setHgap(3);
        tetrisGrid.setPadding(new Insets(3, 3, 3, 3));
        gameInfoScreen = new VBox();
        gameInfoScreen.setAlignment(Pos.CENTER);
        gameInfoScreen.setSpacing(25);
        gamePane = new BorderPane();
        gamePane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        gamePane.setPadding(new Insets(0,90,0,25));
        gameScene = new Scene(gamePane);
        
        //highScoreScene
        highScorePane = new BorderPane();
        highScorePane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        highScorePane.setPadding(new Insets(250,0,250,0));
        highScoreScene = new Scene(highScorePane);
            
        //leaderboardScene
        leaderboardPane = new BorderPane();
        leaderboardPane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        leaderboardPane.setPadding(new Insets(250,0,250,0));
        leaderboardScene = new Scene(leaderboardPane);
    }
    
    @Override
    public void stop() {
        System.out.println("sovellus sulkeutuu");
    }
    
    private void getLetters() {
        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j <= 4; j++) {
                if (j == 0 && (i != 3 && i != 7 && i != 11 && i != 15 && i != 17)) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (i == 1 || i == 4 || i == 9 || i == 12 || i == 16) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (i > 17 && (j == 2 || j == 4)) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (j == 1 && (i == 14 || i == 18)) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (j == 2 && (i == 5 || i == 6 || i == 13 || i == 14)) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (j == 3 && (i == 13 || i == 20)) {
                    tetrisLogoLetters[i][j] = true;
                }
                if (j== 4 && (i == 5 || i == 6 || i == 14)) {
                    tetrisLogoLetters[i][j] = true;
                }
            }
        }
    }
    
    private void resetGame() {
        game = new TetrisGame();
        leaderboard = new Leaderboard();
        gameOver = false;
        currentHighScore = leaderboard.getHighScore();
        boardWidth = game.getBoardWidth();
        boardHeight = game.getBoardHeight();
        activeShapeLocation = new boolean[boardWidth][boardHeight];
        passiveShapesLocation = new boolean[boardWidth][boardHeight];
    }
    
    private void createControls() {
        gameScene.setOnKeyPressed((key) -> {
            if (key.getCode().equals(KeyCode.LEFT)) {
                game.moveLeft();
            } else if (key.getCode().equals(KeyCode.RIGHT)) {
                game.moveRight();
            } else if (key.getCode().equals(KeyCode.UP)) {
                game.rotate();
            } else if (key.getCode().equals(KeyCode.DOWN)) {
                game.hardDrop();
            }
            updateGameScene();
        });
    }
    
    private void createGravity() {
        SequentialTransition shapeMove = new SequentialTransition();
        PauseTransition shapePause = new PauseTransition(Duration.millis(currentGameSpeed));
        shapePause.setOnFinished(event -> {
            if (game.gameOver()) {
                gameOver = true;
                shapeMove.stop();
                updateGameScene();
                highScoreHandler();
            } else {
                game.moveDown();
                fullRowsHandler();
                updateGameScene();
            }
        });
        shapeMove.getChildren().add(shapePause);
        shapeMove.setCycleCount(Timeline.INDEFINITE);
        shapeMove.play();
    }
    
    private void fullRowsHandler() {
        if (game.checkForFullRows()) {
            game.deleteRowsAndDropRowsAboveFullRows();
            game.setScoreAndSpeed();
            currentPoints = game.getScore().getPoints();
            currentLevel = game.getScore().getLevel();
            currentLinesCleared = game.getScore().getLinesCleared();
            currentGameSpeed = game.getGameSpeed().getSpeed();
        }
    }
    
    private void highScoreHandler() {
        if (leaderboard.pointsEnoughForLeaderboard(game.getScore().getPoints())) {
            updateHighScoreScene();
            stage.setScene(highScoreScene);
        } else {
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        }
    }
    
    private void updateHighScoreScene() {
        Label highScoreText = new Label(" Onneksi olkoon, sait " + game.getScore().getPoints() + " pistettä" + "\n" + "ja pääsit viiden parhaan joukkoon!");
        highScoreText.setAlignment(Pos.CENTER);
        highScoreText.setFont(Font.font("Arial",20));
        Label enterNickNameText = new Label("Syötä nimimerkki:");
        enterNickNameText.setFont(Font.font("Arial",16));
        TextField nickNameInput = new TextField();
        Button okButton = new Button("OK");
        HBox nickNameBox = new HBox();
        nickNameBox.setAlignment(Pos.CENTER);
        nickNameBox.setSpacing(15);
        nickNameBox.getChildren().addAll(enterNickNameText, nickNameInput, okButton);
        okButton.setOnAction(e-> {
            leaderboard.addNewHighScoreToLeaderboard(nickNameInput.getText(),game.getScore().getPoints());
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        });
        highScorePane.setTop(highScoreText);
        highScorePane.setCenter(nickNameBox);
        BorderPane.setAlignment(highScoreText, Pos.CENTER);
        BorderPane.setAlignment(nickNameBox, Pos.CENTER);
    }
    
    private void updateGameScene() {
        gameInfoScreen.getChildren().clear();
        gamePane.getChildren().clear();
        tetrisGrid.getChildren().clear();
        updateTetrisGrid();
        updateGameInfoScreen();
        gamePane.setLeft(tetrisGrid);
        gamePane.setRight(gameInfoScreen);
    }
    
    private void updateTetrisGrid() {
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
    
    private void updateGameInfoScreen() {
        Label logo = new Label("T E T R I S");
        logo.setFont(Font.font("Arial", 24));
        logo.setAlignment(Pos.CENTER);
        Label currentScore = new Label("HighScore: " + currentHighScore
                + "\n" + "Points: " + currentPoints
                + "\n" + "Lines: " + currentLinesCleared
                + "\n" + "Level: " + currentLevel + " / 10");
        currentScore.setFont(Font.font("Arial", 16));
        currentScore.setAlignment(Pos.CENTER);
        gameInfoScreen.getChildren().addAll(logo, currentScore);
    }
    
    private void updateLeaderboardScene() {
        Label leaderboardLogo = new Label("LEADERBOARD");
        leaderboardLogo.setFont(Font.font("Arial",20));
        ArrayList<String> players = leaderboard.getLeaderBoardPlayers();
        if (players.size() < 5) {
            for (int i = players.size(); i < 5; i++) {
                players.add("-");
            }
        }
        ArrayList<Integer> points = leaderboard.getLeaderBoardPoints();
        if (points.size() < 5) {
            for (int i = points.size(); i < 5; i++) {
                points.add(0);
            }
        }
        GridPane leaderboardField = new GridPane();
        leaderboardField.add(new Label("Sija"),0,0);
        leaderboardField.add(new Label("Nimimerkki"),1,0);
        leaderboardField.add(new Label("Pisteet"),2,0);
        for (int i = 0; i <= 2; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 0) {
                    leaderboardField.add(new Label(""+(j)),i,j);
                }
                if (i == 1) {
                    leaderboardField.add(new Label(players.get(j-1)),i,j);
                }
                if (i == 2) {
                    leaderboardField.add(new Label(""+points.get(j-1)),i,j);
                }
            }
        }
        leaderboardField.setAlignment(Pos.CENTER);
        leaderboardField.setVgap(15);
        leaderboardField.setHgap(30);
        Button leaderboardToMenu = new Button("Menu");
        leaderboardToMenu.setOnAction(e -> {
            stage.setScene(menuScene);
        });
        leaderboardPane.setTop(leaderboardLogo);
        leaderboardPane.setCenter(leaderboardField);
        leaderboardPane.setBottom(leaderboardToMenu);
        BorderPane.setAlignment(leaderboardLogo, Pos.CENTER);
        BorderPane.setAlignment(leaderboardField, Pos.CENTER);
        BorderPane.setAlignment(leaderboardToMenu, Pos.CENTER);
    }
}
