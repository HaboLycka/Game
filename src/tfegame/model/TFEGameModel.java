package tfegame.model;

import framework.model.GameBoard;
import framework.model.GameModel;
import framework.model.GameTile;
import framework.view.GameObserver;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class TFEGameModel implements GameModel {
    private List<GameObserver> observers = new ArrayList<>();
    private GameBoard board;

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
        return 0;
    }

    @Override
    public void keyMove(int direction) {
        switch(direction) {
            case 0:
            tilesMoveUp();
            newTiles();
            notifyObservers();
            break;

            case 1:
            tilesMoveDown();
            newTiles();
            notifyObservers();
            break;

            case 2:
            tilesMoveLeft();
            newTiles();
            notifyObservers();
            break;

            case 3:
            tilesMoveRight();
            newTiles();
            notifyObservers();
            break;
        }
    }
    private void newTiles() {
        int emptytiles = ((TFEGameBoard)(board)).emptyTiles();
        if (emptytiles > 0) {
            spawnNewTiles(emptytiles);
        }
    }

    private void spawnNewTiles(int emptytiles) {
        if (emptytiles > 2) emptytiles = 2;

        for (int i = 0; i < emptytiles; i++) {
            int row =  (int)(Math.random() * board.getRows());
            int col =  (int)(Math.random() * board.getCols());

            if (!board.getTileAt(row, col).isEmpty()) {
                i--;
                continue;
            }
            GameTile tile = board.getTileAt(row, col);
            tile.setState(tile.getState() + 1);
            tile.setColor(board.getColor(tile.getState()));
        }
    }

    /**
     * TODO - make movement into a generic function
     *          
        // moveUp
            // 0 -> rows
            // 0 -> cols

        // moveDown
            // rows -> 0
            // cols -> 0

        // moveLeft
            // 0 -> rows
            // 0 -> cols
        
        // moveRight
            // rows -> 0
            // cols -> 0

        // down/up inverse
        // left/right inverse
     */
    private void tilesMoveUp() {
        int emptyrow = 0;


        for (int row = 0; row < board.getRows(); row++)
         {
            boolean emptyfound = false;
            for (int col = 0; col < board.getCols(); col++) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptyrow = row;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(emptyrow, col);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    row = emptyrow;
                }
            }
        }


        for (int col = 0; col < board.getCols(); col++) {
            for (int row = 0; row < board.getRows() - 1; row++) {
                GameTile t1 = board.getTileAt(row, col);
                GameTile t2 = board.getTileAt(row + 1, col);

                if (t1.getState() == t2.getState() && t1.getState() != 0) {
                    t1.setState(t1.getState() + 1);
                    t1.setColor(board.getColor(t1.getState()));
                    t2.setColor(Color.WHITE);
                    t2.setState(0);
                    break;
                }
                
            }
        }

        for (int col = 0; col < board.getCols(); col++) {
            boolean emptyfound = false;
            for (int row = 0; row < board.getRows(); row++) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptyrow = row;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(emptyrow, col);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    row = emptyrow;
                    
                }
            }
        }
    }

    private void tilesMoveDown() {
        int emptyrow = 0;

        for (int col = board.getCols() - 1; col >= 0; col--) {
            boolean emptyfound = false;
            for (int row = board.getRows() - 1; row >= 0 ; row--) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptyrow = row;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(emptyrow, col);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    row = emptyrow;
                }
            }
        }

        for (int col = board.getCols() - 1; col >= 0; col--) {
            for (int row = board.getRows() - 1; row > 0 ; row--) {
                GameTile t1 = board.getTileAt(row, col);
                GameTile t2 = board.getTileAt(row - 1, col);

                if (t1.getState() == t2.getState() && t1.getState() != 0) {
                    t1.setState(t1.getState() + 1);
                    t1.setColor(board.getColor(t1.getState()));
                    t2.setColor(Color.WHITE);
                    t2.setState(0);
                    break;
                }
                
            }
        }

        for (int col = board.getCols() - 1; col >= 0; col--) {
            boolean emptyfound = false;
            for (int row = board.getRows() - 1; row >= 0 ; row--) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptyrow = row;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(emptyrow, col);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    row = emptyrow;
                }
            }
        }

    }
    private void tilesMoveLeft() {
        int emptycol = 0;

        for (int row = 0; row < board.getRows(); row++) {
            boolean emptyfound = false;
            for (int col = 0; col < board.getCols(); col++) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptycol = col;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(row, emptycol);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    col = emptycol;
                }
            }
        }

        // merge tiles with same state

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols() - 1; col++) {
                GameTile t1 = board.getTileAt(row, col);
                GameTile t2 = board.getTileAt(row, col + 1);

                if (t1.getState() == t2.getState() && t1.getState() != 0) {
                    t1.setState(t1.getState() + 1);
                    t1.setColor(board.getColor(t1.getState()));
                    t2.setColor(Color.WHITE);
                    t2.setState(0);
                    break;
                }
                
            }
        }

        for (int row = 0; row < board.getRows(); row++) {
            boolean emptyfound = false;
            for (int col = 0; col < board.getRows(); col++) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptycol = col;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(row, emptycol);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    col = emptycol;
                }
            }
        }


    }
    private void tilesMoveRight() {
        int emptycol = 0;

        for (int row = board.getRows() - 1; row >= 0; row--) {
            boolean emptyfound = false;
            for (int col = board.getRows() - 1; col >= 0 ; col--) {
                if (!emptyfound && board.getTileAt(row, col).isEmpty()) {
                    emptyfound = true;
                    emptycol = col;
                }

                if (emptyfound && !board.getTileAt(row, col).isEmpty()) {
                    GameTile emptytile = board.getTileAt(row, emptycol);
                    GameTile tile = board.getTileAt(row, col);
                    switchTiles(tile, emptytile);
                    emptyfound = false;
                    col = emptycol;
                }
            }
        }
    }

    private void switchTiles(GameTile t1, GameTile t2) {
        int state = t1.getState();
        Color c = t1.getColor();

        t1.setColor(t2.getColor());
        t1.setState(t2.getState());

        t2.setColor(c);
        t2.setState(state);
    }

    public Color getColor(int index) {
        return board.getColor(index);
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