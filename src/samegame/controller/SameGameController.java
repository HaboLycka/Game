package samegame.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import framework.controller.GameController;
import framework.model.GameModel;
import framework.view.GameView;

public class SameGameController implements GameController{

    private GameModel model;
    private GameView view;

    @Override
    public void mouseInput(MouseEvent e) {
        int row = e.getY() / view.getTileSize();
        int col = e.getX() / view.getTileSize();
        model.makeMove(row, col);
    }

    @Override
    public void keyInput(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_R) {
            model.restartGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            model.cheat();
        }
    }
    
    public SameGameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }
}
