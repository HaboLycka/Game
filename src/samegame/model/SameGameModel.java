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

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SameGameModel implements GameModel {

    /** List of observers that will be notified of game state changes */
    private List<GameObserver> observers = new ArrayList<>();
    
    /** The game board containing the tiles */
    private GameBoard board;
    
    /** Current score */
    private int score = 0;
    
    /** Flag indicating if the game has been won */
    private boolean gameWon = false;
    
    /** Flag indicating if the game has been lost */
    private boolean gameLost = false;
    
    /** Stored game states for undo functionality */
    private List<int[][]> storedStates = new ArrayList<>();
    
    /** Stored scores for undo functionality */
    private List<Integer> storedScores = new ArrayList<>();

    /**
     * Adds an observer to be notified of game state changes.
     * 
     * @param observer The observer to add
     */
    @Override
    public void addObserver(GameObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the notification list.
     * 
     * @param observer The observer to remove
     */
    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers of a game state change.
     */
    @Override
    public void notifyObservers() {
        for (GameObserver o : observers) {
            o.update();
        }
    }

    /**
     * Saves the current game state to a file.
     * Saves the score and the state of each tile on the board.
     */
    @Override
    public void saveGame() {
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("Game/src/samegame/savedata.txt"))) {
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

    /**
     * Loads a saved game state from a file.
     * 
     * @return true if the game was successfully loaded, false otherwise
     */
    @Override
    public boolean loadGame() {
        File f = new File("Game/src/samegame/savedata.txt");

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

    /**
     * Checks if a save file is valid for loading.
     * 
     * @param f The save file to check
     * @return true if the file is valid, false otherwise
     * @throws FileNotFoundException if the file doesn't exist
     */
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

    /**
     * Gets the current score.
     * 
     * @return The current score
     */
    @Override
    public int getScore() {
        return score;
    }

    /**
     * Gets the number of rows in the game board.
     * 
     * @return The number of rows
     */
    @Override
    public int getRows() {
        return board.getRows();
    }

    /**
     * Gets the number of columns in the game board.
     * 
     * @return The number of columns
     */
    @Override
    public int getCols() {
        return board.getCols();
    }

    /**
     * Gets the tile at the specified position.
     * 
     * @param row The row index
     * @param col The column index
     * @return The GameTile at the specified position
     */
    @Override
    public GameTile getTileAt(int row, int col) {
        return board.getTileAt(row, col);
    }

    /**
     * Restarts the game by resetting the board and score.
     */
    @Override
    public void restartGame() {
        board.resetBoard();
        score = 0;
        gameWon = false;
        gameLost = false;
        notifyObservers();
    }

    /**
     * Undoes the last move by restoring the previous game state.
     */
    public void undo() {
        loadGameFromStoredStates();
    }

    /**
     * Loads a game state from the stored states stack.
     * Used for undoing moves.
     */
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
    
    /**
     * Handles keyboard movement input.
     * Not implemented for SameGame.
     * 
     * @param direction The direction of the key move
     */
    @Override
    public void keyMove(int direction) {
        return;
    }

    /**
     * Processes a click move at the specified position.
     * If a valid group of matching tiles is found, they are removed and the score is updated.
     * After removing tiles, the remaining tiles are collapsed downward and compacted leftward.
     * 
     * @param row The row index of the click
     * @param col The column index of the click
     */
    @Override
    public void clickMove(int row, int col) {
        
        if (gameWon || gameLost) return;
        
        List<Point> group = new ArrayList<>();
        int state = board.getTileAt(row, col).getState();
        // recursively adds all matching neighbours of the same colour
        findCluster(group, row, col, state);
        int n = group.size();

        if (n < 2) return;
        saveStates();
        for (Point p : group) {
            board.getTileAt(p.y, p.x).setColor(Color.WHITE);
            board.getTileAt(p.y, p.x).setState(0);
        }
            score += (n - 2) * (n - 2);
        // Tiles go down
        collapse();
        // Tiles go left
        compact();

        if(board.getTileAt(board.getRows() - 1, 0).isEmpty())
            gameWon = true;

        if (findLargestCluster().size() < 2 && !gameWon)
            gameLost = true;
        saveGame();
        playsound();
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
                board.getTileAt(p.y, p.x).setColor(Color.ORANGE);
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

    private void playsound() {
        try {

            java.net.URL audioUrl = getClass().getClassLoader().getResource("samegame/eventAudio.wav");
            if (audioUrl == null) {
                System.err.println("Could not find audio file");
                return;
            }
            AudioInputStream audio = AudioSystem.getAudioInputStream(audioUrl);
            Clip clip = AudioSystem.getClip(); 
            clip.open(audio);
            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1) {
        }
    }

    public SameGameModel(GameBoard board) {
        this.board = board;
    }

    public Color getColor(int index) {
        return board.getColor(index);
    }
}
