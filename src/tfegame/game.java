package tfegame;

import java.awt.event.*;

import javax.swing.JFrame;

import tfegame.controller.TFEGameController;
import tfegame.model.TFEGameBoard;
import tfegame.model.TFEGameModel;
import tfegame.view.TFEGameConsoleView;
import tfegame.view.TFEGameView;

public class game {
    public static void main(String[] args) {
        JFrame frame = new JFrame(); 

        TFEGameBoard board = new TFEGameBoard();
        TFEGameModel model = new TFEGameModel(board);
        TFEGameView view = new TFEGameView(model);
        TFEGameConsoleView consoleview = new TFEGameConsoleView(model);
        
        model.addObserver(view);
        model.addObserver(consoleview);
        
        TFEGameController controller = new TFEGameController(model, view);

        view.setFocusable(true);
        view.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                controller.keyInput(e);
            }
        });
        //frame.setResizable(false);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
