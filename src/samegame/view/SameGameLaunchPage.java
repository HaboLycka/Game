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
        
        JFrame frame = new JFrame("SameGame");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
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
        
        frame.setResizable(false);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                displayLaunchPage();
            }
        });
        
        this.setVisible(false);
    }

    private void displayLaunchPage() {
        this.setVisible(true);
    }
}
