
public class Vector2I {
    private int X, Y, G, H, F;
    private Vector2I previous;

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


    public int getG() {
        return G;
    }

    public void setG(int g) {
        G = g;
    }

    public int getH() {
        return H;
    }

    public void setH(int h) {
        H = h;
    }

    public int getF() {
        return F;
    }

    public void setF(int f) {
        F = f;
    }

    public Vector2I getPrevious() {
        return previous;
    }

    public void setPrevious(Vector2I previous) {
        this.previous = previous;
    }

    @Override
    public boolean equals(Object obj) {
        Vector2I pos = (Vector2I) obj;
        return (pos.getX() == this.getX() && pos.getY() == this.getY());

    }

}

