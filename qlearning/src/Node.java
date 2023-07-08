import java.util.ArrayList;

public class Node {
    int x, y;
    double q, r;
    int ACTIONS = 4;
    int COLUMNS = 20;
    int ROWS = 20;

    Node(int x, int y, double q) {
        this.x = x;
        this.y = y;
        this.q = q;
    }

    int getX() {
        return this.x;
    }

    int getY() {
        return this.y;
    }

    double getQ() {
        return this.q;
    }

    void setQ(double q) {
        this.q = q;
    }

    void setY(int y) {
        this.y = y;
    }

    void setX(int x) {
        this.x = x;
    }

    boolean equals(Node... node) {
        boolean b = false;
        for (int i = 0; i < node.length; i++) {
            if (b) break;
            b = this.getX() == node[i].getX() && this.getY() == node[i].getY();
        }
        return b;
    }

    boolean equals(Node node) {
        return this.getX() == node.getX() && this.getY() == node.getY();
    }


    Node findNodeEqualsTo(Node... node) {
        for (int i = 0; i < node.length; i++) {
            if (this.getX() == node[i].getX() && this.getY() == node[i].getY()) return node[i];//this.equals(node[i])
        }
        return null;
    }

    public String toString() {
        return getX() + "   " + getY();
    }
}