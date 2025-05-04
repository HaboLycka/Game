package samegame.view;

import framework.model.GameModel;
import framework.view.GameObserver;
import framework.view.GameView;

public class SameGameConsoleView implements GameView, GameObserver{

    private GameModel model;
    private int width;
    private int height;

    @Override
    public void update() {
        if (model.isGameOver()) {
            printGameOver();
        }
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

        System.out.println(model.getScore());

        for (int y = 0; y < height; y++) {
            System.out.print("|");
            for (int x = 0; x < width; x++) {
                System.out.print(model.getTileAt(y, x).getState() + " ");
            }
            System.out.println("|");
        }
        System.out.println("\n");
    }
    
    private void printGameOver() {
        if (model.isGameWon())
            System.out.println("You Win! \nScore:" + model.getScore());
        else
            System.out.println("No more moves, You Lose!\nScore: " + model.getScore());
    }
    public SameGameConsoleView(GameModel model) {
        this.model = model;
        this.height = model.getRows();
        this.width = model.getCols();
    }
}
