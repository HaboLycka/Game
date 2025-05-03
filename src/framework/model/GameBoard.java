package framework.model;

public interface GameBoard {
    int getRows();
    int getCols();
    GameTile getTileAt(int row, int col);
    void resetBoard();
    void initializeBoard(int rows, int cols, int difficulty);
}
