package framework.model;

import java.awt.Color;

public interface GameTile {
    Color getColor();
    void setColor(Color color);
    int getState();
    void setState(int state);
}
