package framework.view;

import java.awt.Graphics;

public interface GameView{
    int getHeight();
    int getWidth();
    int getTileSize();

    void drawGrid(Graphics g);
    void drawTiles(Graphics g);
}
