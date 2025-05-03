package samegame.model;

import framework.model.GameBoard;
import framework.model.GameTile;

import java.awt.*;

public class SameGameBoard implements GameBoard{
    private int rows;
    private int cols;
    private GameTile[][] tiles;
    private Color[] colors;
    
    @Override
    public int getRows() {
        return rows;
    }

    @Override
    public int getCols() {
        return cols;
    }

    @Override
    public GameTile getTileAt(int row, int col) {
        return tiles[row][col];
    }

    @Override
    public void initializeBoard(int rows, int cols, Color[] colors) {
        tiles = new GameTile[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int randomnum = (int)(Math.random() * colors.length);
                tiles[row][col] = new SameGameTile(colors[randomnum], randomnum + 1);
            }
        }
    }

    @Override
    public void resetBoard() {
        initializeBoard(rows, cols, colors);
    }
    public SameGameBoard(int rows, int cols, Color[] colors) {
        this.rows = rows;
        this.cols = cols;
        this.colors = colors;
        initializeBoard(rows, cols, colors);
    }
}
