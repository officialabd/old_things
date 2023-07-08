import java.util.ArrayList;

public class QLearning {
    private final int STATES_NO;
    private final int ACTIONS_NO;
    private double[][] rMap;
    private State[][] map;
    private double alpha = 0.1;
    private double gamma = 0.9;
    private State current;
    private int episodes = 100000;
    private boolean moreLearn = true;
    private double punchmentValue = -100000;

    public QLearning(int STATES_NO, int ACTIONS_NO) {
        this.STATES_NO = STATES_NO;
        this.ACTIONS_NO = ACTIONS_NO;
        setup();
    }

    void setup() {
        initRMap();
        initMap();
    }

    void runQLearning() {
        long before = System.currentTimeMillis();
        for (int i = 0; i < episodes; i++) {
            moreLearn = true;
//            current = start;
            while (moreLearn) {
                if (isItFinalState(current)) {
                    moreLearn = false;
                }
                ArrayList<Action> actions = findNewState(current);
                if (actions.size() == 0) return;

                int index = (int) (Math.random() * actions.size());
                Action action = actions.get(index);

                double q = current.getQ();
                double maxQ = maxQ(actions);
                double r = rMap[current.getY()][current.getX()];
                double value = q + alpha * (r + gamma * maxQ - q);
                current.setQ(value);
                current = action;
            }
        }
        System.out.println((System.currentTimeMillis() - before) / 1000.0);
    }

    void printPath() {
        drawRect(drawNode, 0, 255, 0);
        if (drawNode.equals(goal, goal1, goal2)) noLoop();
        drawNode = policy(drawNode);//Check weather to take the same drawNode or take the node equals to the it in map
    }

    Node policy(Node currentState) {
        ArrayList<Node> actions = findNewState(currentState);
        Node withMaxValue = currentState;
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actions.size(); i++) {
            Node nextState = actions.get(i);
            if (nextState.getQ() > maxValue) {
                withMaxValue = nextState;
                maxValue = nextState.getQ();
            }
        }
        return withMaxValue;
    }

    double maxQ(ArrayList<Action> actions) {
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actions.size(); i++) {
            if (actions.get(i) != null) {
                double value = actions.get(i).getQValue();
                if (value > maxValue)
                    maxValue = value;
            }
        }
        return maxValue;
    }

    boolean isItFinalState(State current) {
        return current.equals(goal, goal1, goal2);
    }

    ArrayList<Action> findNewState(State currentState) {
        return map[currentState.getY()][currentState.getX()].getNeighbors();
    }


    void printQValue() {
        fill(255);
        stroke(255);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Node pos = new Node(j, i);
                ArrayList<Node> nodes = findNewState(pos);
                for (int a = 0; a < nodes.size(); a++) {
                    Node temp = nodes.get(a);
                    if (temp.getQ() < 0) {
                        int v = (int) (temp.getQ() * 10);
                        textFont(f);
                        text(String.valueOf(v), j * CELL_SIZE, i * CELL_SIZE + (CELL_SIZE * a) / 4 + 15);
                    }
                }
            }
        }
    }

    void initMap() {
        map = new State[ROWS][COLUMNS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Node temp = new Node(x, y);
                map[y][x] = new State(temp, COLUMNS, ROWS);
            }
        }
    }

    void initRMap() {
        rMap = new double[ROWS][COLUMNS];
        rMap[(ROWS - 1) / 4][(COLUMNS - 1) / 4] = 100;
        rMap[(ROWS - 1) / 2][(COLUMNS - 1) / 2] = 1000;
        rMap[ROWS - 1][COLUMNS - 1] = 10000;
    }

}