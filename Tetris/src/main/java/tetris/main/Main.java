package tetris.main;

//Sovelluksen varsinainen Main-tiedosto
//(jotta jar, maven ja javaFX toimivat yhteen)

import tetris.ui.GameView;

public class Main {
    public static void main(String[] args) {
        GameView.main(args);
    }
}
