package samegame.model;

import java.awt.Color;

import framework.model.GameTile;

public class SameGameTile implements GameTile{
    private Color color;
    private int state;

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public int getState() {
        return state;    
    }

    @Override
    public void setState(int state) {
        this.state = state;    
    }
    
    public SameGameTile(Color c, int s) {
        this.color = c;
        this.state = s;
    }


}
