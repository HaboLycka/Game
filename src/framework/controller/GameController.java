package framework.controller;

import java.awt.event.MouseEvent;
import java.awt.event.KeyEvent;

public interface GameController {
    void mouseInput(MouseEvent e);
    void keyInput(KeyEvent e);
}
