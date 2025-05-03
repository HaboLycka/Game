package framework.controller;

import framework.model.GameModel;
import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public interface GameController {
    void mouseInput(MouseEvent e);
    void keyInput(KeyEvent e);
}
