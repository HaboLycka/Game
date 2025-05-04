package tfegame.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import framework.model.GameModel;
import framework.view.GameObserver;
import framework.view.GameView;

public class TFEGameView extends JPanel implements GameView, GameObserver{

    private GameModel model;
    private int height;
    private int width;
    private int tilesize = 100;

    @Override
    public void update() {
        repaint();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getTileSize() {
        return tilesize;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawGrid(g);
        drawTiles(g);
    } 

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

    public TFEGameView(GameModel model) {
        this.model = model;
        this.height = model.getRows() * tilesize;
        this.width = model.getCols() * tilesize;

        this.setPreferredSize(new Dimension(width, height));
    }


    
}
