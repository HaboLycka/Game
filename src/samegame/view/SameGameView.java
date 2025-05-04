package samegame.view;

import framework.model.GameModel;
import framework.view.GameObserver;
import framework.view.GameView;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.*;

public class SameGameView extends JPanel implements GameView, GameObserver {

    private GameModel model;
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

    private void drawGrid(Graphics g) {
        g.setColor(Color.black);
		
		for (int i = 0; i <= model.getRows(); i++) {
		    g.drawLine(0, tilesize * i, width, tilesize * i);
		}

		for (int j = 0; j <= model.getCols(); j++) {
		    g.drawLine(tilesize * j, 0, tilesize * j, height);
		}
    }

    /**
     * Fills in the grids with corresponding tile color
     */
    private void drawTiles(Graphics g) {
        for (int i = 0; i < model.getRows(); i++) {
            for (int j = 0; j < model.getCols(); j++) {
                Color c = model.getTileAt(i, j).getColor();
        
                g.setColor(c.darker());
                g.fillRect(j * tilesize, i * tilesize, tilesize, tilesize);
        
                g.setColor(c);
                g.fillRect(j * tilesize + 5, i * tilesize + 5, tilesize - 10, tilesize - 10);
            }
        }
    }

    private void drawScore(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Score: " + model.getScore(), 10, 50);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (model.isGameOver()) {
            drawGameOver(g);
        }
        else {
            drawGrid(g);
            drawTiles(g);
            drawScore(g);  
        }
    }

    private void drawGameOver(Graphics g) {
        if (model.isGameOver()) {
            g.setColor(Color.BLACK);
            g.drawString("Score: " + model.getScore(), tilesize, height / 2);
            if (model.isGameWon())
                g.drawString("You Win!", tilesize, height / 2 + 15);
            else 
                g.drawString("No more moves, You Lose!", tilesize, height / 2 + 15);
        }
    }
    public SameGameView(GameModel model) {
        this.model = model;
        this.height = model.getRows() * tilesize;
        this.width = model.getCols() * tilesize;

        this.setPreferredSize(new Dimension(width, height));
    }
}
