package framework.model;

import java.io.File;

import framework.view.GameObserver;

public interface GameModel {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers();
    void restartGame();
    void saveGame();
    void loadGame(File f);
    int getRows();
    public int getCols();
    GameTile getTileAt(int row, int col);
    int getScore();
    void makeMove(int row, int col);
    void cheat();
}
