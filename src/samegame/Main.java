package samegame;

import java.awt.Color;
import java.awt.event.*;

import javax.swing.JFrame;

import samegame.model.*;
import samegame.view.*;
import samegame.controller.*;

public class Main {
    public static void main(String[] args) {
        Color[] colors = {Color.MAGENTA, Color.CYAN, Color.DARK_GRAY};

        SameGameBoard board = new SameGameBoard(15, 25, colors);
        SameGameModel model = new SameGameModel(board);
        SameGameView view = new SameGameView(board);
        model.addObserver(view);
        SameGameController controller = new SameGameController(model, view);
        
        view.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                controller.mouseInput(e);
            }
        });
        JFrame frame = new JFrame("SameGame Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
