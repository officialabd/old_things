package com.snake.gamelogic;

public class BodyPart {
    private Vector2I pos;
    private Directions dir;

    public BodyPart(Vector2I pos, Directions dir) {
        this.pos = pos;
        this.dir = dir;
    }

    public Vector2I getPos() {
        return pos;
    }

    public void setPos(Vector2I pos) {
        this.pos = pos;
    }

    public Directions getDir() {
        return dir;
    }

    public void setDir(Directions dir) {
        this.dir = dir;
    }
}
