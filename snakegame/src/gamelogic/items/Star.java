package gamelogic.items;

import gamelogic.GameLogic;
import gamelogic.Item;
import gamelogic.Snake;
import gamelogic.Vector2I;

public class Star implements Item {
    private Vector2I pos;
    private Snake snake;
    private GameLogic gameLogic;

    public Star(Snake snake) {
        this.snake = snake;
        pos = new Vector2I();
    }

    public Star(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.snake = gameLogic.getSnake();
        pos = new Vector2I();
    }

    @Override
    public Vector2I getPos() {
        return pos;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }

    @Override
    public String name() {
        return "Star";
    }

    @Override
    public void onAction() {
        gameLogic.setScore(gameLogic.getScore() + 5);
    }
}
