package samegame;

import java.awt.event.*;

import javax.swing.JFrame;

import samegame.model.*;
import samegame.view.*;
import samegame.controller.*;

public class Main {
    public static void main(String[] args) {

        SameGameBoard board = new SameGameBoard(15, 25, 3);
        SameGameModel model = new SameGameModel(board);
        SameGameView view = new SameGameView(model);
        SameGameConsoleView consoleview = new SameGameConsoleView(board);

        model.addObserver(view);
        model.addObserver(consoleview);
        SameGameController controller = new SameGameController(model, view);
        
        view.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.mouseInput(e);
            }
        });

        view.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                controller.keyInput(e);
            }
        });

        JFrame frame = new JFrame("SameGame Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        view.setFocusable(true);
        view.requestFocusInWindow();
    }
}