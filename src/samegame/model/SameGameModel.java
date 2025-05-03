package samegame.model;

import framework.model.GameBoard;
import framework.model.GameModel;
import framework.model.GameTile;
import framework.view.GameObserver;

import java.awt.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class SameGameModel implements GameModel{

    private List<GameObserver> observers = new ArrayList<>();
    private GameBoard board;
    private int score = 0;

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
        saveGame();
        for (GameObserver o : observers) {
            o.update();
        }
    }

    
    @Override
    public void saveGame() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("src/samegame/savedata.txt"))) {
            
            bw.write(board.getRows() + "\n");
            bw.write(board.getCols() + "\n");
            bw.write(score + "\n");
            
            for (int y = 0; y < board.getRows(); y++) {
                for (int x = 0; x < board.getCols(); x++) {
                    bw.write(board.getTileAt(y, x).getState() + " ");
                }
                bw.write("\n");
            }
            
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    @Override
    public void loadGame(File f) {
        try (BufferedReader br = new BufferedReader(
            new FileReader("src/samegame/savedata.txt"))) {
        int[][] state = new int[board.getRows()][board.getCols()];
        score = br.

        for (int y = 0; y < board.getRows(); y++) {
            for (int x = 0; x < board.getCols(); x++) {
                bw.write(board.getTileAt(y, x).getState() + " ");
            }
            bw.write("\n");
        }
        
    } catch (IOException e) {
        System.err.println("Error saving game: " + e.getMessage());
    }
    }

    @Override
    public int getScore() {
        return score;
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
    public void makeMove(int row, int col) {
        List<Point> group = new ArrayList<>();
        int state = board.getTileAt(row, col).getState();
        // recurively adds all matching neighbour of the same colour
        findCluster(group, row, col, state);
        int n = group.size();
        if (n >= 2) {
            for (Point p : group) {
                board.getTileAt(p.y, p.x).setColor(Color.WHITE);
                board.getTileAt(p.y, p.x).setState(0);
            }
            score += (n - 2) * (n - 2);
        }
        // Tiles go down
        collapse();
        // Tiles go left
        compact();

        
        notifyObservers();
    }

    private void collapse() {
        for (int col = 0; col < board.getCols(); col++) {
            for (int row = board.getRows() - 1; row >= 0; row--) {
                // find an empty tile
                if (board.getTileAt(row, col).isEmpty()) {
                    // Look for a tile that is not empty above
                    for (int above = row - 1; above >= 0; above--) {
                        if (!board.getTileAt(above, col).isEmpty()) {
                            // Swap the tiles
                            switchTiles(board.getTileAt(above, col), board.getTileAt(row, col));
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * if an empty tile is found on the bottom it is shifted left
     */
    private void compact() {
        for (int col = 0; col < board.getCols(); col++) {
            // find an empty tile
            if (board.getTileAt(board.getRows() - 1, col).isEmpty()){
                int emptycol = col;
                // look for next tile that is not empty
                for (int nextcol = col + 1; nextcol < board.getCols(); nextcol++) {
                    // swap the tiles of every row on those two columns
                    if (!board.getTileAt(board.getRows() - 1, nextcol).isEmpty()) {
                        shiftrowleft(emptycol, nextcol);
                        break;
                    }
                }
            }
        }
    }
    /**
     * @param empty the row that is empty
     * @param col a row that is not empty
     */
    private void shiftrowleft(int emptycol, int col) {
        // loop from the bottom - up
        for (int row = board.getRows() - 1; row >= 0; row--) {
            // return if an empty tile is found
            if(board.getTileAt(row, col).isEmpty()) return;
            //switch tiles of the two columns on the same row
            switchTiles(board.getTileAt(row, col), board.getTileAt(row, emptycol));
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

    public void cheat() {
        List<Point> largestcluster = findLargestCluster();
        int n = largestcluster.size();
        if (n >= 2) {
            for (Point p : largestcluster) {
                board.getTileAt(p.y, p.x).setColor(Color.BLACK);
            }
            score += (n - 2) * (n - 2);
        }
        notifyObservers();
    }
    // adds matching neighbour to a List
    private void findCluster(List<Point> group, int row, int col, int state) {
        // row or col is out of bounds return
        if (col < 0 || col >= board.getCols() || row < 0 || row >= board.getRows()) return;
		
        // if tile is empty or does not have same color return
		if (board.getTileAt(row, col).isEmpty() || board.getTileAt(row, col).getState() != state) return;
		
        //check if tile is already added
		for (Point pos : group) {
			if (pos.x == col && pos.y == row) return;
		}
		
        // add the tile
		group.add(new Point(col, row));
        
        // apply same function for neighbouring tiles
		findCluster(group, row + 1, col, state);
		findCluster(group, row - 1, col, state);
		findCluster(group, row, col + 1, state);
		findCluster(group, row, col - 1, state);
    }

    private List<Point> findLargestCluster( ) {
        List<List<Point>> clusters = new ArrayList<>();

        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                GameTile tile = board.getTileAt(row, col);
                if (tile.isEmpty()) break;

                for (List<Point> c : clusters) {
                    for (Point pos : c) {
                        if (pos.x == col && pos.y == row)
                            break;
                    }
                }
                List<Point> cluster = new ArrayList<>();
                int state = tile.getState();
                findCluster(cluster, row, col, state);
                if(!cluster.isEmpty()) clusters.add(cluster);
            }   
        }

        List<Point> largest = new ArrayList<>();

        for (List<Point> p : clusters) {
            if (p.size() > largest.size()) largest = p;
        }

        return largest;
    }
    
    @Override
    public void restartGame() {
        board.resetBoard();
        score = 0;
        notifyObservers();
    }

    public SameGameModel(GameBoard board) {
        this.board = board;
    }
}
