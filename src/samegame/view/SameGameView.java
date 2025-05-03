package samegame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.*;

import framework.model.GameBoard;
import framework.view.GameObserver;
import framework.view.GameView;

public class SameGameView extends JPanel implements GameView, GameObserver{

    private GameBoard board;
    private int width;
    private int height;
    private int tilesize = 50;

    @Override
    public void update() {
        repaint();
    }

    /**
     * @return px width of the JPanel
     */
    @Override
    public int getWidth() {
        return width;
    }
    /**
     * @return px height of the JPanel
     */
    @Override
    public int getHeight() {
        return height;
    }

    /**
     * @return returns tilesize
     */
    @Override
    public int getTileSize() {
        return tilesize;
    }

    /**
     * Draws the grid lines
     */

    public void drawGrid(Graphics g) {
        g.setColor(Color.black);
		
		for (int i = 0; i <= board.getRows(); i++) {
		    g.drawLine(0, tilesize * i, width, tilesize * i);
		}

		for (int j = 0; j <= board.getCols(); j++) {
		    g.drawLine(tilesize * j, 0, tilesize * j, height);
		}
    }

    /**
     * Fills in the grids with corresponding tile color
     */
    public void drawTiles(Graphics g) {
        for (int i = 0; i < board.getRows(); i++) {
            for (int j = 0; j < board.getCols(); j++) {
                Color c = board.getTileAt(i, j).getColor();
        
                g.setColor(c.darker());
                g.fillRect(j * tilesize, i * tilesize, tilesize, tilesize);
        
                g.setColor(c);
                g.fillRect(j * tilesize + 5, i * tilesize + 5, tilesize - 10, tilesize - 10);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawTiles(g);
    }
    public SameGameView(GameBoard board) {
        this.board = board;
        this.height = board.getRows() * tilesize;
        this.width = board.getCols() * tilesize;

        this.setPreferredSize(new Dimension(width, height));
    }


}
