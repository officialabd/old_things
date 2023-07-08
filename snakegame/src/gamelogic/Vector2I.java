package gamelogic;

public class Vector2I {
    private int X, Y;

    public Vector2I(int x, int y) {
        this.X = x;
        this.Y = y;
    }

    @Override
    public String toString() {
        return getX() + " " + getY();
    }

    public Vector2I() {
        this(0, 0);
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        this.X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        this.Y = y;
    }

    public boolean equals(Vector2I pos) {
        return (pos.getX() == this.getX() && pos.getY() == this.getY());

    }

}

