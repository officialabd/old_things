package com.snake.gamelogic;

public interface Item {

    public Vector2I getPos();

    public boolean isHarmful();

    public String name();

    public void onAction();
}
