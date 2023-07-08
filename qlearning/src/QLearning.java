import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class QLearning {

    private final int ACTIONS;
    private final int COLUMNS;
    private final int ROWS;
    private final int CELL_SIZE;
    private Node[] blocks;
    private double[][] rMap;
    private State[][] map;
    private Node start;
    private Node goal;
    private Node goal1;
    private Node goal2;
    private State current;
    private State drawNode;
    private double alpha = 0.1;
    private double gamma = 0.1;
    private int episodes = 10000;
    private GraphicsContext graphicsContext;


    public QLearning(int ACTIONS, int COLUMNS, int ROWS, int CELL_SIZE, GraphicsContext graphicsContext) {
        this.ACTIONS = ACTIONS;
        this.COLUMNS = COLUMNS;
        this.ROWS = ROWS;
        this.CELL_SIZE = CELL_SIZE;
        this.graphicsContext = graphicsContext;
        setup();
    }

    private void setup() {
        initMap();
        initRMap();
        initGoals();
    }


    public void run() {
        runQLearning();
        draw();
    }

    private void runQLearning() {
        for (int i = 0; i < episodes; i++) {
            current = new State(start.getX(), start.getY(), COLUMNS, ROWS);
            System.out.println("---------------" + i);
            while (!isItFinalState(current)) {
                ArrayList<Node> actions = findNewState(current);

                int index = (int) (Math.random() * actions.size());
                Node action = actions.get(index);

                double q = current.getQ();
                double maxQ = maxQ(actions);
//                System.out.println(action.getX() + " " + action.getY());
                double r = rMap[action.getY()][action.getX()];
                double value = q + alpha * (r + gamma * maxQ - q);
                current.setQ(value);
                current = map[action.getY()][action.getX()];
            }
        }
    }

    private void drawPath() {
        while (true) {
            drawRect(drawNode.getPos(), 0, 255, 0);
            System.out.println(drawNode.getPos());
            if (drawNode.equals(goal)) break;
            drawNode = policy(drawNode);//Check weather to take the same drawNode or take the node equals to the it in map
        }
    }

    private State policy(State currentState) {
        ArrayList<Node> actions = findNewState(currentState);
        State withMaxValue = currentState;
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < actions.size(); i++) {
            Node nextState = actions.get(i);
            if (nextState.getQ() > maxValue) {
                withMaxValue = map[nextState.getY()][nextState.getX()];
                maxValue = nextState.getQ();
                System.out.println(maxValue);
            }
        }
        return withMaxValue;
    }

    private double maxQ(ArrayList<Node> nodes) {
        double maxValue = Double.MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            if (nodes.get(i) != null) {
                double value = nodes.get(i).getQ();
                if (value > maxValue)
                    maxValue = value;
            }
        }
        return maxValue;
    }

    private boolean isItFinalState(State current) {
        return current.getPos().equals(goal, goal1, goal2);
    }

    private ArrayList<Node> findNewState(State currentState) {
        return map[currentState.getPos().getY()][currentState.getPos().getX()].getNeighbors();
    }

    private void drawQValue() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                ArrayList<Node> arrayTemp = map[i][j].getNeighbors();
                for (int a = 0; a < arrayTemp.size(); a++) {
                    Node temp = arrayTemp.get(a);
                    int v = (int) (temp.getQ() * 100000);
                    graphicsContext.strokeText(String.valueOf(v), temp.getX() * CELL_SIZE, temp.getY() * CELL_SIZE + (CELL_SIZE * a) / 4 + 20);
                }
            }
        }
    }


    private void initMap() {
        map = new State[ROWS][COLUMNS];
        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                map[y][x] = new State(x, y, COLUMNS, ROWS);
            }
        }
    }

    private void drawRect(Node node, int r, int g, int b) {
        graphicsContext.setFill(Color.rgb(r, g, b));
        graphicsContext.fillRect(node.getX() * CELL_SIZE, node.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
    }

    private void draw() {
        drawRect(goal, 255, 0, 0);
        drawRect(goal1, 255, 0, 0);
        drawRect(goal2, 255, 0, 0);
        System.out.println("D");
        drawPath();
        drawQValue();
    }


    private void initRMap() {
        rMap = new double[ROWS][COLUMNS];
        rMap[(ROWS - 1) / 4][(COLUMNS - 1) / 4] = 100;
        rMap[(ROWS - 1) / 2][(COLUMNS - 1) / 2] = 1000;
        rMap[ROWS - 1][COLUMNS - 1] = 10000;
    }

    private void initGoals() {
        start = new Node(0, 0, 0);
        goal = new Node(ROWS - 1, COLUMNS - 1, 0);
        goal1 = new Node((ROWS - 1) / 2, (COLUMNS - 1) / 2, 0);
        goal2 = new Node((ROWS - 1) / 4, (COLUMNS - 1) / 4, 0);
        current = new State(start.getX(), start.getY(), COLUMNS, ROWS);
        drawNode = new State(start.getX(), start.getY(), COLUMNS, ROWS);
    }

    private void initBlocks() {
        int size = 10;
        blocks = new Node[10];
        for (int i = 0; i < size; i++) {
            int x = (int) (Math.random() * (COLUMNS - 1));
            int y = (int) (Math.random() * (ROWS - 1));
            Node temp = new Node(x, y, 0);
            if (!temp.equals(start, goal, goal1, goal2))
                blocks[i] = temp;
        }
    }

    private void drawBlocks() {
        for (int i = 0; i < blocks.length; i++) {
            drawRect(blocks[i], 255, 255, 255);
        }
    }

    private boolean isThereBlockHere(Node pos) {
        for (int i = 0; i < blocks.length; i++) {
            if (pos.equals(blocks[i])) return true;
        }
        return false;
    }

}
