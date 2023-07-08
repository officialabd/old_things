package gamelogic;

public enum Directions {

    RIGHT(new Vector2I(1, 0)),

    LEFT(new Vector2I(-1, 0)),

    UP(new Vector2I(0, -1)),

    DOWN(new Vector2I(0, 1));

    public final Vector2I pos;

    Directions(Vector2I pos) {
        this.pos = pos;
    }

    public Directions getOpposite() {
        if (this == RIGHT) {
            return LEFT;
        } else if (this == LEFT) {
            return RIGHT;
        } else if (this == UP) {
            return DOWN;
        } else if (this == DOWN) {
            return UP;
        }
        return null;
    }

}
