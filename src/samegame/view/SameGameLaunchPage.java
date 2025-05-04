package samegame.view;

import framework.model.GameModel;
import framework.view.GameLaunchPage;
import java.awt.event.*;
import javax.swing.*;
import samegame.controller.SameGameController;
import samegame.model.SameGameBoard;
import samegame.model.SameGameModel;

public class SameGameLaunchPage extends GameLaunchPage {

    @Override
    public void newGame() {
        SameGameBoard board = new SameGameBoard(3);
        SameGameModel model = new SameGameModel(board);
        startGame(model);
    }

    @Override
    public void loadGame() {
        SameGameBoard board = new SameGameBoard(3);
        SameGameModel model = new SameGameModel(board);
        model.loadGame();
        startGame(model);
    }
    
    @Override
    public void startGame(GameModel model) {
        SameGameView view = new SameGameView(model);
        SameGameConsoleView consoleview = new SameGameConsoleView(model);
        
        model.addObserver(consoleview);
        model.addObserver(view);
        SameGameController controller = new SameGameController(model, view);
        
        JFrame gameFrame = new JFrame("SameGame");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        view.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.mouseInput(e);
            }
        });
        
        view.setFocusable(true);
        view.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                controller.keyInput(e);
            }
        });
        
        gameFrame.setResizable(false);
        gameFrame.add(view);
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setVisible(true);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(false);
    }
}
