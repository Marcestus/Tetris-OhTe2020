package tetris.ui;

import java.io.InputStream;
import javafx.animation.SequentialTransition;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Properties;
import javafx.scene.control.ChoiceBox;
import javafx.scene.text.TextAlignment;
import tetris.domain.TetrisGame;
import tetris.domain.Leaderboard;

/**
 * Graafista käyttöliittymää hallitseva luokka.
 * Graafinen käyttöliittymä koostuu neljästä näkymästä: menu, game, highscore ja leaderboard.
 * Sovellus avautuu menu-näkymään, jossa pelaaja tutustuu ohjeisiin ja valitsee mieleisensä vaikeustason.
 * Menu-näkymästä päästään sekä aloittamaan peli game-näkymässä että tarkastelemaan pelituloksia leaderboard-näkymään.
 * Tetriksen pelaaminen tapahtuu game-näkymässä. Pelialueen vieressä seurataan pelitilannetta (pisteet, poistetut rivit yhteensä). Pelin saa pauselle.
 * Highscore-näkymässä pelaaja syöttää nimimerkkinsä. Näkymä tulee esiin vain, jos pelaaja saa leaderboardiin yltävän pelituloksen.
 * Leaderboard esittelee pelin viisi parasta tulosta. Täältä pääsee siirtymään takaisin menu-näkymään.
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
    private int boardWidth;
    private int boardHeight;
    static final int tileSize = 40;
    private GridPane menuSceneTetrisLogo;
    private Label menuSceneInstructionsText;
    private HBox menuSceneButtonsAndGameLevelChoiceBox;
    private boolean[][] menuSceneTetrisLogoLetters;
    private GridPane gameSceneTetrisGrid;
    private VBox gameSceneInfoScreen;
    private boolean[][] activeShapeLocation;
    private boolean[][] passiveShapesLocation;
    private TetrisGame game;
    private int gameSpeed;
    private boolean gameOver;
    private boolean gamePause;
    private int currentHighScore;
    private int currentPoints;
    private int currentLinesCleared;
    private boolean highScore;
    private Leaderboard leaderboard;
    private String leaderboardDatabaseName;
    private Properties properties;
    private InputStream stream;
    
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void init() throws Exception {
        this.properties = new Properties();
        this.stream = GameView.class.getResourceAsStream("/tetris/config.properties");
        this.properties.load(this.stream);
        this.leaderboardDatabaseName = properties.getProperty("leaderboardDatabase");
        resetTetrisGame();
    }
    
    @Override
    public void start(Stage notUsedStage) {
        //menuScene
        createMenuSceneLayout();
        menuPane = new BorderPane();
        menuPane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        menuPane.setPadding(new Insets(175, 100, 175, 100));
        menuPane.setTop(menuSceneTetrisLogo);
        menuPane.setCenter(menuSceneInstructionsText);
        menuPane.setBottom(menuSceneButtonsAndGameLevelChoiceBox);
        BorderPane.setAlignment(menuSceneTetrisLogo, Pos.TOP_CENTER);
        BorderPane.setAlignment(menuSceneButtonsAndGameLevelChoiceBox, Pos.BOTTOM_CENTER);
        menuScene = new Scene(menuPane);
        
        //gameScene
        createGameSceneLayout();
        gamePane = new BorderPane();
        gamePane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        gamePane.setPadding(new Insets(0, 90, 0, 25));
        gameScene = new Scene(gamePane);
        
        //highScoreScene
        highScorePane = new BorderPane();
        highScorePane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        highScorePane.setPadding(new Insets(250, 0, 250, 0));
        highScoreScene = new Scene(highScorePane);
            
        //leaderboardScene
        leaderboardPane = new BorderPane();
        leaderboardPane.setPrefSize(boardWidth * tileSize + 350, boardHeight * tileSize + 50);
        leaderboardPane.setPadding(new Insets(250, 0, 250, 0));
        leaderboardScene = new Scene(leaderboardPane);
        
        stage = new Stage();
        stage.setTitle("T E T R I S");
        stage.setScene(menuScene);
        stage.show();
    }
    
    @Override
    public void stop() {
        System.out.println("sovellus sulkeutuu");
    }
    
    private void resetTetrisGame() {
        try {
            game = new TetrisGame();
        } catch (Exception e) {
            System.out.println("Fetching file failed with message: " + e.getMessage());
        }
        leaderboard = new Leaderboard(leaderboardDatabaseName);
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
            } else if (key.getCode().equals(KeyCode.SPACE)) {
                if (gamePause) {
                    gamePause = false;
                    createGravity();
                } else {
                    gamePause = true;
                }
            }
            updateGameScene();
        });
    }
    
    private void createGravity() {
        SequentialTransition shapeMove = new SequentialTransition();
        PauseTransition shapePause = new PauseTransition(Duration.millis(gameSpeed));
        shapePause.setOnFinished(event -> {
            if (gamePause) {
                shapeMove.pause();
                updateGameScene();
            }
            if (game.gameOver()) {
                gameOver = true;
                shapeMove.stop();
                highScoreChecker();
            } else {
                game.moveDown();
                game.fullRowsHandler();
                updateGameScene();
            }
        });
        shapeMove.getChildren().add(shapePause);
        shapeMove.setCycleCount(Timeline.INDEFINITE);
        shapeMove.play();
    }
    
    private void createMenuSceneLayout() {
        //menuSceneTetrisLogo
        menuSceneTetrisLogoLetters = new boolean[21][5];
        getMenuSceneTetrisLogoLetters();
        menuSceneTetrisLogo = new GridPane();
        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j <= 4; j++) {
                Rectangle logoRect = new Rectangle(15, 15);
                if (menuSceneTetrisLogoLetters[i][j]) {
                    logoRect.setFill(Color.GREEN);
                } else {
                    logoRect.setFill(Color.WHITESMOKE);
                }
                menuSceneTetrisLogo.add(logoRect, i, j);
            }
        }
        menuSceneTetrisLogo.setVgap(1);
        menuSceneTetrisLogo.setHgap(1);
        menuSceneTetrisLogo.setAlignment(Pos.CENTER);
        menuSceneTetrisLogo.setPadding(new Insets(3, 3, 3, 3));
        
        //menuSceneInstructionsText
        menuSceneInstructionsText = new Label(
                         "Tervetuloa Tetriksen pariin! Muodosta putoavista palikoista"
                + "\n" + "täysiä vaakasuoria rivejä kerryttääksesi pisteitä."
                + "\n" + "Mitä useamman rivin poistat kerralla, ja mitä kovemman"
                + "\n" + "vaikeustason olet valinnut, sitä enemmän saat pisteitä."
                + "\n" + "Peli päättyy, kun palikka ei mahdu enää putoamaan."
                + "\n"
                + "\n" + "Liikuta palikkaa nuolinäppäimillä:"
                + "\n" + "LEFT - palikka liikkuu vasempaan"
                + "\n" + "RIGHT - palikka liikkuu oikealle"
                + "\n" + "UP - palikka kääntyy"
                + "\n" + "DOWN - palikka putoaa heti paikoilleen"
                + "\n"
                + "\n" + "Muut toiminnot:"
                + "\n" + "SPACE - laita peli tauolle ja jatka peliä"
                + "\n"
                + "\n" + "Parhaat viisi tulosta pääsevät leaderboardiin, onnea matkaan!"
                + "\n"
                + "\n" + "Valitse vielä vaikeustaso liukuvalikosta:");
        menuSceneInstructionsText.setFont(Font.font("Arial", 16));
        menuSceneInstructionsText.setTextAlignment(TextAlignment.CENTER);
        
        //menuSceneGameLevelChoiceBox
        ChoiceBox menuSceneGameLevelChoiceBox = new ChoiceBox();
        menuSceneGameLevelChoiceBox.getItems().add("Easy");
        menuSceneGameLevelChoiceBox.getItems().add("Moderate");
        menuSceneGameLevelChoiceBox.getItems().add("Hard");
        menuSceneGameLevelChoiceBox.setValue("Easy");
        
        //menuSceneButtons
        Button menuSceneStartGameButton = new Button("Start game");
        Button menuSceneToLeaderboardSceneButton = new Button("Leaderboard");
        
        //menuSceneButtonsAndGameLevelChoiceBox
        menuSceneButtonsAndGameLevelChoiceBox = new HBox();
        menuSceneButtonsAndGameLevelChoiceBox.setAlignment(Pos.CENTER);
        menuSceneButtonsAndGameLevelChoiceBox.setSpacing(15);
        menuSceneButtonsAndGameLevelChoiceBox.getChildren().addAll(menuSceneGameLevelChoiceBox, menuSceneStartGameButton, menuSceneToLeaderboardSceneButton);
        menuSceneStartGameButton.setOnAction(e-> {
            String choiceBoxSelection = (String) menuSceneGameLevelChoiceBox.getValue();
            resetTetrisGame();
            gameSpeed = game.setGameSpeedAndGameLevel(choiceBoxSelection);
            stage.setScene(gameScene);
            updateGameScene();
            createControls();
            createGravity();
        });
        menuSceneToLeaderboardSceneButton.setOnAction(e-> {
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        });
    }
    
    private void getMenuSceneTetrisLogoLetters() {
        for (int i = 0; i <= 20; i++) {
            for (int j = 0; j <= 4; j++) {
                if (j == 0 && (i != 3 && i != 7 && i != 11 && i != 15 && i != 17)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (i == 1 || i == 4 || i == 9 || i == 12 || i == 16) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (i > 17 && (j == 2 || j == 4)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (j == 1 && (i == 14 || i == 18)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (j == 2 && (i == 5 || i == 6 || i == 13 || i == 14)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (j == 3 && (i == 13 || i == 20)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
                if (j== 4 && (i == 5 || i == 6 || i == 14)) {
                    menuSceneTetrisLogoLetters[i][j] = true;
                }
            }
        }
    }
    
    private void createGameSceneLayout() {
        //gameSceneTetrisGrid
        gameSceneTetrisGrid = new GridPane();
        gameSceneTetrisGrid.setPrefSize(boardWidth * tileSize, boardHeight * tileSize);
        gameSceneTetrisGrid.setAlignment(Pos.CENTER);
        gameSceneTetrisGrid.setVgap(3);
        gameSceneTetrisGrid.setHgap(3);
        gameSceneTetrisGrid.setPadding(new Insets(3, 3, 3, 3));
        
        //gameSceneInfoScreen
        gameSceneInfoScreen = new VBox();
        gameSceneInfoScreen.setAlignment(Pos.CENTER);
        gameSceneInfoScreen.setSpacing(25);
    }
    
    private void updateGameScene() {
        gameSceneInfoScreen.getChildren().clear();
        gameSceneTetrisGrid.getChildren().clear();
        gamePane.getChildren().clear();
        updateGameSceneTetrisGrid();
        updateGameSceneInfoScreen();
        gamePane.setLeft(gameSceneTetrisGrid);
        gamePane.setRight(gameSceneInfoScreen);
    }
    
    private void updateGameSceneTetrisGrid() {
        game.resetActiveShapeCoord();
        activeShapeLocation = game.getActiveShapeCoord();
        game.resetPassiveTileCoord();
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 2; y < boardHeight; y++) {
                passiveShapesLocation[x][y] = false;
            }
        }
        passiveShapesLocation = game.getPassiveTileCoord();
        colorGameSceneTetrisGridTiles();
    }
    
    private void colorGameSceneTetrisGridTiles() {
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
                gameSceneTetrisGrid.add(rect, x, y);
            }
        }
    }
    
    private void updateGameSceneInfoScreen() {
        //gameSceneTetrisTitle
        Label gameSceneTetrisTitle = new Label("T E T R I S");
        gameSceneTetrisTitle.setFont(Font.font("Arial", 24));
        gameSceneTetrisTitle.setAlignment(Pos.CENTER);
        
        //gameSceneCurrentScore
        Label gameSceneCurrentScore = new Label("HighScore: " + currentHighScore
                + "\n" + "Points: " + game.getScore().getPoints()
                + "\n" + "Lines: " + game.getScore().getLinesCleared()
                + "\n" + "Level: " + game.getScore().getGameLevelText());
        gameSceneCurrentScore.setFont(Font.font("Arial", 16));
        gameSceneCurrentScore.setTextAlignment(TextAlignment.CENTER);
        
        Label gameScenePauseInstructions = new Label("");
        if (gamePause) {
            gameScenePauseInstructions = new Label("Press SPACE" + "\n" + "to continue");
        } else {
            gameScenePauseInstructions = new Label("Press SPACE" + "\n" + "to pause");
        }
        gameScenePauseInstructions.setFont(Font.font("Arial", 16));
        gameScenePauseInstructions.setTextAlignment(TextAlignment.CENTER);
        
        gameSceneInfoScreen.getChildren().addAll(gameSceneTetrisTitle, gameSceneCurrentScore, gameScenePauseInstructions);
    }
    
    private void highScoreChecker() {
        if (leaderboard.pointsEnoughForLeaderboard(game.getScore().getPoints())) {
            updateHighScoreScene();
            stage.setScene(highScoreScene);
        } else {
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        }
    }
    
    private void updateHighScoreScene() {
        highScore = true;
        //highScoreSceneText
        Label highScoreSceneText = new Label(" Onneksi olkoon, sait " + game.getScore().getPoints() + " pistettä" + "\n" + "ja pääsit viiden parhaan joukkoon!");
        highScoreSceneText.setAlignment(Pos.CENTER);
        highScoreSceneText.setFont(Font.font("Arial", 20));
        
        //highScoreSceneEnterNickNameText
        Label highScoreSceneEnterNickNameText = new Label("Syötä nimimerkki:");
        highScoreSceneEnterNickNameText.setFont(Font.font("Arial", 16));
        
        //highScoreSceneNickNameInputField
        TextField highScoreSceneNickNameTextField = new TextField();
        Button highScoreSceneOKButton = new Button("OK");
        HBox highScoreSceneNickNameInputField = new HBox();
        highScoreSceneNickNameInputField.setAlignment(Pos.CENTER);
        highScoreSceneNickNameInputField.setSpacing(15);
        highScoreSceneNickNameInputField.getChildren().addAll(highScoreSceneEnterNickNameText, highScoreSceneNickNameTextField, highScoreSceneOKButton);
        highScoreSceneOKButton.setOnAction(e-> {
            leaderboard.addNewHighScoreToLeaderboard(highScoreSceneNickNameTextField.getText(),game.getScore().getPoints());
            updateLeaderboardScene();
            stage.setScene(leaderboardScene);
        });
        
        //highScorePane
        highScorePane.setTop(highScoreSceneText);
        highScorePane.setCenter(highScoreSceneNickNameInputField);
        BorderPane.setAlignment(highScoreSceneText, Pos.CENTER);
        BorderPane.setAlignment(highScoreSceneNickNameInputField, Pos.CENTER);
    }
    
    private void updateLeaderboardScene() {
        //leaderboardSceneNoHighScoreText
        Label leaderboardSceneNoHighScoreText = new Label("");
        if (gameOver && !highScore) {
            leaderboardSceneNoHighScoreText = new Label("Harmin paikka, ei mainetta ja kunniaa tällä kertaa!");
            leaderboardSceneNoHighScoreText.setFont(Font.font("Arial", 16));
        }
        highScore = false;
        
        //leaderboardSceneTitle
        Label leaderboardSceneTitle = new Label("LEADERBOARD");
        leaderboardSceneTitle.setFont(Font.font("Arial", 20));
        
        //leaderboardSceneGrid
        ArrayList<String> leaderboardScenePlayers = leaderboard.getLeaderboardPlayers();
        if (leaderboardScenePlayers.size() < 5) {
            for (int i = leaderboardScenePlayers.size(); i < 5; i++) {
                leaderboardScenePlayers.add("-");
            }
        }
        ArrayList<Integer> leaderboardScenePoints = leaderboard.getLeaderboardPoints();
        if (leaderboardScenePoints.size() < 5) {
            for (int i = leaderboardScenePoints.size(); i < 5; i++) {
                leaderboardScenePoints.add(0);
            }
        }
        GridPane leaderboardSceneGrid = new GridPane();
        leaderboardSceneGrid.add(new Label("Sija"),0,0);
        leaderboardSceneGrid.add(new Label("Nimimerkki"),1,0);
        leaderboardSceneGrid.add(new Label("Pisteet"),2,0);
        for (int i = 0; i <= 2; i++) {
            for (int j = 1; j <= 5; j++) {
                if (i == 0) {
                    leaderboardSceneGrid.add(new Label("" + (j)), i, j);
                }
                if (i == 1) {
                    leaderboardSceneGrid.add(new Label(leaderboardScenePlayers.get(j - 1)), i, j);
                }
                if (i == 2) {
                    leaderboardSceneGrid.add(new Label("" + leaderboardScenePoints.get(j - 1)), i, j);
                }
            }
        }
        leaderboardSceneGrid.setAlignment(Pos.CENTER);
        leaderboardSceneGrid.setVgap(15);
        leaderboardSceneGrid.setHgap(30);
        
        //leaderboardSceneTitleAndGrid
        VBox leaderboardSceneTitleAndGrid = new VBox();
        leaderboardSceneTitleAndGrid.setAlignment(Pos.CENTER);
        leaderboardSceneTitleAndGrid.setSpacing(25);
        leaderboardSceneTitleAndGrid.getChildren().addAll(leaderboardSceneTitle, leaderboardSceneGrid);
        
        //leaderboardSceneToMenuSceneButton
        Button leaderboardSceneToMenuSceneButton = new Button("Menu");
        leaderboardSceneToMenuSceneButton.setOnAction(e -> {
            resetTetrisGame();
            stage.setScene(menuScene);
        });
        
        //leaderboardPane
        leaderboardPane.setTop(leaderboardSceneNoHighScoreText);
        leaderboardPane.setCenter(leaderboardSceneTitleAndGrid);
        leaderboardPane.setBottom(leaderboardSceneToMenuSceneButton);
        BorderPane.setAlignment(leaderboardSceneNoHighScoreText, Pos.CENTER);
        BorderPane.setAlignment(leaderboardSceneTitleAndGrid, Pos.CENTER);
        BorderPane.setAlignment(leaderboardSceneToMenuSceneButton, Pos.CENTER);
    }
}
