package framework.view;

import framework.model.GameModel;

public interface GameObserver {
    public void update(GameModel model);
}
