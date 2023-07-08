package com.snake.gamelogic.items;

import java.util.ArrayList;

import com.snake.gamelogic.BodyPart;
import com.snake.gamelogic.GameLogic;
import com.snake.gamelogic.Item;
import com.snake.gamelogic.Snake;
import com.snake.gamelogic.Vector2I;

public class Apple implements Item {
    private GameLogic gameLogic;
    private final Snake snake;
    private final Vector2I pos;

    public Apple(Snake snake) {
        this.snake = snake;
        pos = new Vector2I();
    }

    public Apple(GameLogic gameLogic) {
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
        return false;
    }

    @Override
    public String name() {
        return "Apple";
    }

    @Override
    public void onAction() {
        ArrayList<BodyPart> body = snake.getBody();
        body.add(snake.combinePoints(body.get(body.size() - 1)));
        snake.setSpeed(Math.exp((-1.0 / 100.0) * body.size() + 5.0) + 1.0);
        gameLogic.setScore(gameLogic.getScore() + 1);
    }

}
