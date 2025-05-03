package samegame.view;

import javax.swing.*;
import java.awt.event.*;
import framework.view.GameLaunchPage;
import samegame.controller.SameGameController;
import samegame.model.SameGameBoard;
import samegame.model.SameGameModel;

public class SameGameLaunchPage extends GameLaunchPage {

    @Override
    public void startGame() {

        SameGameBoard board = new SameGameBoard(4);
        SameGameModel model = new SameGameModel(board);
        SameGameView view = new SameGameView(model);

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

    @Override
    public void loadGame() {
        SameGameBoard board = new SameGameBoard(4);
        SameGameModel model = new SameGameModel(board);
        model.loadGame();
        SameGameView view = new SameGameView(model);

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
