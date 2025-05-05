package tfegame.model;

import framework.model.GameBoard;
import framework.model.GameTile;
import java.awt.Color;

public class TFEGameBoard implements GameBoard{

    private int rows = 4;
    private int cols = 4;
    private GameTile[][] tiles;
    private final Color[] colors = {Color.WHITE, Color.GRAY, Color.CYAN, Color.GREEN, Color.MAGENTA, Color.ORANGE};

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
    public void resetBoard() {
        initializeBoard();
    }

    public int emptyTiles() {
        int counter = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (tiles[row][col].isEmpty()) counter++;
            }
        }
        return counter;
    }
    @Override
    public void initializeBoard() {
        tiles = new GameTile[rows][cols];

        for (int row = 0; row < rows; row++) 
            for (int col = 0; col < cols; col++) 
                tiles[row][col] = new TFEGameTile(colors[0]);

        int r1 = (int)(Math.random() * rows);
        int c1 = (int)(Math.random() * cols);

        int r2, c2;

        do {
            r2 = (int)(Math.random() * rows);
            c2 = (int)(Math.random() * cols);
        } while (r2 == r1 && c2 == c1);

        tiles[r1][c1].setColor(colors[1]);
        tiles[r1][c1].setState(1);
        
        tiles[r2][c2].setColor(colors[1]);
        tiles[r2][c2].setState(1);
    }

    @Override
    public Color getColor(int index) {
        return colors[index];
    }
    
    public TFEGameBoard() {
        initializeBoard();
    }
}
