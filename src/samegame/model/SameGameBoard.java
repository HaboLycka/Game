package samegame.model;

import framework.model.GameBoard;
import framework.model.GameTile;

import java.awt.*;

public class SameGameBoard implements GameBoard{
    private int rows;
    private int cols;
    private int difficulty;
    private GameTile[][] tiles;
    private final Color[] colors = { Color.MAGENTA, Color.CYAN, Color.DARK_GRAY, Color.ORANGE, Color.PINK};
    
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
    public void initializeBoard(int rows, int cols, int difficulty) {
        tiles = new GameTile[rows][cols];
        // initialize the board by setting random
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int randomnum = (int)(Math.random() * difficulty);
                tiles[row][col] = new SameGameTile(colors[randomnum], randomnum + 1);
            }
        }
    }

    @Override
    public void resetBoard() {
        initializeBoard(rows, cols, difficulty);
    }
    
    public SameGameBoard(int row, int col, int difficulty) {
        if (difficulty > 5) difficulty = 5;
        if (difficulty < 3) difficulty = 3;
        this.rows = row;
        this.cols = col;
        this.difficulty = difficulty;
        initializeBoard(rows, cols, difficulty);
    }
}
