package tfegame.model;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import framework.model.GameBoard;
import framework.model.GameModel;
import framework.model.GameTile;
import framework.view.GameObserver;

public class TFEGameModel implements GameModel {
    private List<GameObserver> observers = new ArrayList<>();
    private GameBoard board;
    private int score = 0;
    private boolean gameWon = false;
    private boolean gameLost = false;
    private List<int[][]> storedStates = new ArrayList<>();
    private List<Integer> storedScores = new ArrayList<>();

    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (GameObserver o : observers) {
            o.update();
        }
    }

    @Override
    public void restartGame() {
        board.resetBoard();
    }

    @Override
    public void saveGame() {

    }

    @Override
    public boolean loadGame() throws FileNotFoundException {
            return false;
    }

    @Override
    public int getRows() {
        return board.getRows();
    }

    @Override
    public int getCols() {
        return board.getCols();
    }

    @Override
    public GameTile getTileAt(int row, int col) {
        return board.getTileAt(row, col);
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void keyMove(int direction) {
        switch(direction) {
            case 0:
            tilesMoveUp();
            break;

            case 1:
            tilesMoveDown();
            break;

            case 2:
            tilesMoveLeft();
            break;

            case 3:
            tilesMoveRight();
            break;
        }
    }

    private void tilesMoveUp() {
        for (int col = 0; col < board.getCols(); col++) {
            for (int row = 0; row < board.getRows(); row++) {
                                
            }
        }
    }

    private void tilesMoveDown() {
        
    }
    private void tilesMoveLeft() {
        
    }
    private void tilesMoveRight() {
        
    }

    @Override
    public void clickMove(int row, int col) {
        
    }

    @Override
    public void cheat() {

    }

    @Override
    public void undo() {
    }

    @Override
    public boolean isGameWon() {
        return false;
    }

    @Override
    public boolean isGameLost() {
        return false;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    public TFEGameModel(GameBoard board) {
        this.board = board;
    }
    
}