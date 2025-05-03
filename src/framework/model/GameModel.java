package framework.model;

import framework.view.GameObserver;

public interface GameModel {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers();
    void restartGame();
    int getRows();
    public int getCols();
    GameTile getTileAt(int row, int col);
    int getScore();
    void makeMove(int row, int col);
    void cheat();
}
