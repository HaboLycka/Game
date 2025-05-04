package samegame.model;

import framework.model.GameBoard;
import framework.model.GameModel;
import framework.model.GameTile;
import framework.view.GameObserver;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SameGameModel implements GameModel {

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
    public void saveGame() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("src/samegame/savedata.txt"))) {
            bw.write(score + "\n");

            for (int y = 0; y < board.getRows(); y++) {
                for (int x = 0; x < board.getCols(); x++) {
                    bw.write(board.getTileAt(y, x).getState() + " ");
                }
                bw.write("\n");
            }

        } catch (IOException e) {
            System.err.println("Error saving game: ");
        }
    }

    @Override
    public boolean loadGame() {
        File f = new File("src/samegame/savedata.txt");

        if (!f.exists()) {
            System.out.println("No Previous Game Found");
            return false;
        }

        try {
            if (!isFileValid(f)) {
                System.out.println("Invalid File");
                return false;
            }

            Scanner sc = new Scanner(f);

            if (sc.hasNextInt()) {
                score = sc.nextInt();
                int[][] states = new int[board.getRows()][board.getCols()];

                for (int row = 0; row < board.getRows(); row++) 
                    for (int col = 0; col < board.getCols(); col++)
                        states[row][col] = sc.nextInt();
                sc.close();
                storedStates.add(states);
                storedScores.add(score);
                loadGameFromStoredStates();
                notifyObservers();
                return true;
            }
            sc.close();
            return false;

        } catch (FileNotFoundException e) {
            System.out.println("Save File Not Found");
            return false;
        }
    }

    private boolean isFileValid(File f) throws FileNotFoundException {
        int counter = 0;
        Scanner sc = new Scanner(f);

        if (sc.hasNextInt()) {
            sc.nextInt();
        }

        while (sc.hasNextInt()) {
            int state = sc.nextInt();
            if (state < 0 || state > 6) {
                sc.close();
                return false;
            }
            counter++;
        }
        sc.close();

        return counter == board.getRows() * board.getCols();
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
    public void restartGame() {
        board.resetBoard();
        score = 0;
        notifyObservers();
    }

    public void undo() {
        loadGameFromStoredStates();
    }

    public void loadGameFromStoredStates() {
        if (storedStates.size() <= 0 && storedScores.size() <= 0) return;
        int[][] state = storedStates.removeLast();
        int score = storedScores.removeLast();
        this.score = score;
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                GameTile tile = board.getTileAt(row, col);
                tile.setState(state[row][col]);
                if (state[row][col] == 0)
                    tile.setColor(Color.WHITE);
                else
                    tile.setColor(board.getColor(state[row][col] - 1));
            }
        }
        notifyObservers();
    }
    @Override
    public void keyMove(int direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'keyMove'");
    }

    @Override
    public void clickMove(int row, int col) {
        
        if (gameWon || gameLost) return;
        
        List<Point> group = new ArrayList<>();
        int state = board.getTileAt(row, col).getState();
        // recurively adds all matching neighbour of the same colour
        findCluster(group, row, col, state);
        int n = group.size();
        if (n >= 2) {
            saveStates();
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

        if(board.getTileAt(board.getRows() - 1, 0).isEmpty())
            gameWon = true;

        if (findLargestCluster().size() < 2 && !gameWon)
            gameLost = true;
        saveGame();
        notifyObservers();
    }

    @Override
    public boolean isGameWon() {
        return gameWon;
    }

    @Override
    public boolean isGameLost() {
        return gameLost;
    }

    @Override
    public boolean isGameOver() {
        return gameLost || gameWon;
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
            if (board.getTileAt(board.getRows() - 1, col).isEmpty()) {
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
     * @param col   a row that is not empty
     */
    private void shiftrowleft(int emptycol, int col) {
        // loop from the bottom - up
        for (int row = board.getRows() - 1; row >= 0; row--) {
            // return if an empty tile is found
            if (board.getTileAt(row, col).isEmpty())
                return;
            // switch tiles of the two columns on the same row
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
        saveStates();
        loadGameFromStoredStates();
        List<Point> largestcluster = findLargestCluster();
        int n = largestcluster.size();
        if (n >= 2) {
            for (Point p : largestcluster) {
                board.getTileAt(p.y, p.x).setColor(Color.blue);
            }
            score += (n - 2) * (n - 2);
        }
        notifyObservers();
    }

    // adds matching neighbour to a List
    private void findCluster(List<Point> group, int row, int col, int state) {
        // row or col is out of bounds return
        if (col < 0 || col >= board.getCols() || row < 0 || row >= board.getRows())
            return;

        // if tile is empty or does not have same color return
        if (board.getTileAt(row, col).isEmpty() || board.getTileAt(row, col).getState() != state)
            return;

        // check if tile is already added
        for (Point pos : group) {
            if (pos.x == col && pos.y == row)
                return;
        }

        // add the tile
        group.add(new Point(col, row));

        // apply same function for neighbouring tiles
        findCluster(group, row + 1, col, state);
        findCluster(group, row - 1, col, state);
        findCluster(group, row, col + 1, state);
        findCluster(group, row, col - 1, state);
    }

    private List<Point> findLargestCluster() {
        List<List<Point>> clusters = new ArrayList<>();

        // Go through every tile on the board
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                GameTile tile = board.getTileAt(row, col);
                if (tile.isEmpty())
                    continue;

                // Check if tile cluster has already been added
                boolean alreadyFound = false;
                for (List<Point> c : clusters) {
                    for (Point pos : c) {
                        if (pos.x == col && pos.y == row) {
                            alreadyFound = true;
                            break;
                        }
                    }
                    if (alreadyFound) break;
                }
                
                // If it hasn't add the cluster to the clusters
                if (!alreadyFound) {
                    List<Point> cluster = new ArrayList<>();
                    int state = tile.getState();
                    findCluster(cluster, row, col, state);
                    if (!cluster.isEmpty())
                        clusters.add(cluster);
                }
            }
        }

        // Find the largest cluster and return it
        List<Point> largest = new ArrayList<>();
        for (List<Point> p : clusters) {
            if (p.size() > largest.size())
                largest = p;
        }

        return largest;
    }
    
    public void saveStates() {
        int[][] s = new int[board.getRows()][board.getCols()];
        
        for (int row = 0; row < board.getRows(); row++) {
            for (int col = 0; col < board.getCols(); col++) {
                s[row][col] = board.getTileAt(row, col).getState();
            }
        }
        storedStates.add(s);
        storedScores.add(score);
    }

    public SameGameModel(GameBoard board) {
        this.board = board;
    }
}
