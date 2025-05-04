package tfegame.controller;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import framework.controller.GameController;
import framework.model.GameModel;
import framework.view.GameView;

public class TFEGameController implements GameController{

    private GameModel model;
    private GameView view;

    @Override
    public void mouseInput(MouseEvent e) {}

    @Override
    public void keyInput(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_UP:
            model.keyMove(0);
            break;

            case KeyEvent.VK_DOWN:
            model.keyMove(1);
            break;

            case KeyEvent.VK_LEFT:
            model.keyMove(2);
            break;

            case KeyEvent.VK_RIGHT:
            model.keyMove(3);
            break;
            case KeyEvent.VK_R:
            model.restartGame();
            model.notifyObservers();
            break;

            
        }
    }
    
    public TFEGameController(GameModel model, GameView view) {
        this.model = model;
        this.view = view;
    }
}
