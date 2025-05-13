package framework.model;

import framework.view.GameObserver;

import java.io.FileNotFoundException;

public interface GameModel {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers();
    void restartGame();
    void saveGame();
    boolean loadGame() throws FileNotFoundException;
    int getRows();
    public int getCols();
    GameTile getTileAt(int row, int col);
    int getScore();
    void clickMove(int row, int col);
    void keyMove(int direction);
    void cheat();
    void undo();

    public boolean isGameWon();
    public boolean isGameLost();
    public boolean isGameOver();
}
