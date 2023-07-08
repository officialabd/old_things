package com.snake.gamelogic.items;

import com.snake.gamelogic.GameLogic;
import com.snake.gamelogic.Item;
import com.snake.gamelogic.Snake;
import com.snake.gamelogic.Vector2I;

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
