package samegame.view;

import framework.model.GameBoard;
import framework.view.GameObserver;
import framework.view.GameView;

public class SameGameConsoleView implements GameView, GameObserver{

    private GameBoard board;
    private int width;
    private int height;

    @Override
    public void update() {
        printBoard();
    }

    /**
     * @return px width of the JPanel
     */
    @Override
    public int getWidth() {
        return width;
    }
    /**
     * @return px height of the JPanel
     */
    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getTileSize() {
        return 1;
    }

    public void printBoard() {

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                System.out.print(board.getTileAt(y, x).getState() + " ");
            }
            System.out.println("|");
        }

        System.out.println("\n");
    }

    public SameGameConsoleView(GameBoard board) {
        this.board = board;
        this.height = board.getRows();
        this.width = board.getCols();
    }
}
