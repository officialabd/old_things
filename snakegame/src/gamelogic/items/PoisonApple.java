package gamelogic.items;

import gamelogic.GameLogic;
import gamelogic.Item;
import gamelogic.Snake;
import gamelogic.Vector2I;

public class PoisonApple implements Item {

    private GameLogic gameLogic;
    private final Snake snake;
    private final Vector2I pos;

    public PoisonApple(Snake snake) {
        this.snake = snake;
        pos = new Vector2I();
    }

    public PoisonApple(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.snake = gameLogic.getSnake();
        this.pos = new Vector2I();
    }

    @Override
    public Vector2I getPos() {
        return pos;
    }

    @Override
    public boolean isHarmful() {
        return true;
    }

    @Override
    public String name() {
        return "Poison Apple";
    }


    @Override
    public void onAction() {
        snake.getBody().remove(snake.getBody().size() - 1);
        if (snake.getBody().size() != 0)
            gameLogic.setScore(gameLogic.getScore() - 1);
    }
}
