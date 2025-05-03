package framework.model;

import framework.view.GameObserver;

public interface GameModel {
    void addObserver(GameObserver observer);
    void removeObserver(GameObserver observer);
    void notifyObservers();

    void makeMove(int row, int col);
}
