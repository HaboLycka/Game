package framework.model;

import java.awt.Color;

public interface GameBoard {
    int getRows();
    int getCols();
    GameTile getTileAt(int row, int col);
    void resetBoard();
    void initializeBoard();
    Color getColor(int index);
}
