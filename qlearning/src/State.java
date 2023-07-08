import java.util.ArrayList;

public class State {
    private ArrayList<Action> actions = new ArrayList();
    private ArrayList<Node> neighbors = new ArrayList();
    private Node pos;
    private int COLUMNS;
    private int ROWS;
    private double q = 0;

    State(int x, int y, int COLUMNS, int ROWS) {
        this.COLUMNS = COLUMNS;
        this.ROWS = ROWS;
        pos = new Node(x, y, 0);
        initActions();
        initNeighbors();
    }

    private void initActions() {
        if (pos.getX() < COLUMNS - 1)
            actions.add(new Action("RIGHT", pos));


        if (pos.getX() > 0)
            actions.add(new Action("LEFT", pos));

        if (pos.getY() < ROWS - 1)
            actions.add(new Action("DOWN", pos));

        if (pos.getY() > 0)
            actions.add(new Action("UP", pos));

        actions.trimToSize();
    }

    public void initNeighbors() {
        for (int i = 0; i < actions.size(); i++) {
            neighbors.add(actions.get(i).applyAction());
        }
    }

    public ArrayList<Node> getNeighbors() {
        return this.neighbors;
    }

    public Node getPos() {
        return pos;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }



}
