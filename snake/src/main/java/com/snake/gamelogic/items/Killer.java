package com.snake.gamelogic.items;

import com.snake.gamelogic.Item;
import com.snake.gamelogic.Snake;
import com.snake.gamelogic.Vector2I;

public class Killer implements Item {
    private final Snake snake;
    private final Vector2I pos;

    public Killer(Snake snake) {
        this.snake = snake;
        pos = new Vector2I();
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
        return "Killer";
    }

    @Override
    public void onAction() {
        snake.getBody().clear();
    }
}
