package samegame.model;

import framework.model.GameBoard;
import framework.model.GameModel;
import framework.view.GameObserver;

import java.awt.*;
import java.util.List;
import java.util.ArrayList;

public class SameGameModel implements GameModel{

    private List<GameObserver> observers = new ArrayList<>();
    private GameBoard board;

    @Override
    public void addObserver(GameObserver observer) {
    observers.add(observer);
    }

    @Override
    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (GameObserver o : observers) {
            o.update();
        }
    }

    @Override
    public void makeMove(int row, int col) {
        List<Point> group = new ArrayList<>();
        Color target = board.getTileAt(row, col).getColor();

        findCluster(group, row, col, target);
        if (group.size() >= 2) {
            for (Point p : group) {
                board.getTileAt(p.y, p.x).setColor(Color.WHITE);
                board.getTileAt(p.y, p.x).setState(0);
            }
        }
        
        notifyObservers();
    }

    private void findCluster(List<Point> group, int row, int col, Color target) {
        if (col < 0 || col >= board.getCols() || row < 0 || row >= board.getRows()) return;
		
		if (board.getTileAt(row, col).getState() == -1 || board.getTileAt(row, col).getColor() != target) return;
		
		for (Point pos : group) {
			if (pos.x == col && pos.y == row) return;
		}
		
		group.add(new Point(col, row));
        
		findCluster(group, row + 1, col, target);
		findCluster(group, row - 1, col, target);
		findCluster(group, row, col + 1, target);
		findCluster(group, row, col - 1, target);
    }
    
    public SameGameModel(GameBoard board) {
        this.board = board;
    }
}
