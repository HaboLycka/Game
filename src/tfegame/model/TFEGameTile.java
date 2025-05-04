package tfegame.model;

import framework.model.GameTile;
import java.awt.Color;

public class TFEGameTile implements GameTile{
    
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

    @Override
    public boolean isEmpty() {
        return state == 0;
    }

    public TFEGameTile(Color c) {
        color = c;
        state = 0;
    }
    
}
