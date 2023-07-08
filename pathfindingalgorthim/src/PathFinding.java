import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class PathFinding {
    private ArrayList<Vector2I> closeSet;
    private ArrayList<Vector2I> openSet;
    private ArrayList<Vector2I> path;
    private final int COLUMNS;
    private final int ROWS;
    private final int CELL_SIZE;
    private Vector2I start;
    private Vector2I goal;
    private final int DIST_BETWEEN_NEIGHBORS = 1;
    private GraphicsContext g;
    private ArrayList<Vector2I> blocks;
    private Color blockColor;
    private int MAX_BLOACKS_NO;

    public PathFinding(int COLUMNS, int ROWS, int CELL_SIZE, Vector2I start, Vector2I goal, GraphicsContext g,
            Color blockColor, int MAX_BLOACKS_NO) {
        this.COLUMNS = COLUMNS;
        this.ROWS = ROWS;
        this.CELL_SIZE = CELL_SIZE;
        this.start = start;
        this.goal = goal;
        this.g = g;
        this.blockColor = blockColor;
        closeSet = new ArrayList<>();
        openSet = new ArrayList<>();
        this.path = new ArrayList<>();
        blocks = createBlocks();
        openSet.add(start);
        timer.start();
        this.MAX_BLOACKS_NO = MAX_BLOACKS_NO;

    }

    AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {

            aStarAlgorithm();
        }
    };
    Vector2I current;

    public void aStarAlgorithm() {

        if (openSet.isEmpty()) {
            System.out.println("No path Found");
            timer.stop();
            return;
        }

        drawSet(openSet, Color.YELLOW);
        drawSet(closeSet, Color.BLUE);
        current = findLowestFCost();
        reconstruct_path(current);
        drawSet(path, Color.RED);
        if (current.equals(goal)) {
            timer.stop();
            return;
        }

        openSet.remove(current);
        openSet.trimToSize();
        closeSet.add(current);
        ArrayList<Vector2I> neighbors = getNeighbors(current);
        for (int i = 0; i < neighbors.size(); i++) {
            Vector2I neighbor = neighbors.get(i);
            if (!isThisPosInThisArray(neighbor, closeSet) && !isThereBlockHere(neighbor)) {
                int tentativeGCost = gCost(current) + 1;
                boolean newPathFound = false;
                if (isThisPosInThisArray(neighbor, openSet)) {
                    if (tentativeGCost < neighbor.getG()) {
                        neighbor.setG(tentativeGCost);
                        newPathFound = true;
                    }
                } else {
                    neighbor.setG(tentativeGCost);
                    openSet.add(neighbor);
                    newPathFound = true;
                }
                if (newPathFound) {
                    neighbor.setH(hCost(neighbor));
                    neighbor.setF(neighbor.getG() + neighbor.getH());
                    neighbor.setPrevious(current);
                }
            }
        }

    }

    private void reconstruct_path(Vector2I current) {
        path.clear();
        Vector2I previous = current;
        while (previous.getPrevious() != null) {
            path.add(previous);
            previous = previous.getPrevious();
        }
        path.add(start);
    }

    private Vector2I findLowestFCost() {
        int lowestIndex = 0;
        for (int i = 0; i < openSet.size(); i++) {
            if (openSet.get(i).getF() < openSet.get(lowestIndex).getF()) {
                lowestIndex = i;
            }
        }
        return openSet.get(lowestIndex);
    }

    private boolean isThisPosInThisArray(Vector2I pos, ArrayList<Vector2I> array) {
        for (int i = 0; i < array.size(); i++) {
            if (array.get(i) != null && array.get(i).equals(pos))
                return true;
        }
        return false;
    }

    private ArrayList<Vector2I> getNeighbors(Vector2I current) {
        ArrayList<Vector2I> neighbors = new ArrayList<>();
        int x = current.getX();
        int y = current.getY();
        if (x + 1 < COLUMNS)
            neighbors.add(new Vector2I(x + 1, y));
        if (x - 1 >= 0)
            neighbors.add(new Vector2I(x - 1, y));
        if (y + 1 < ROWS)
            neighbors.add(new Vector2I(x, y + 1));
        if (y - 1 >= 0)
            neighbors.add(new Vector2I(x, y - 1));
        // --------- Ranging
        if (x < COLUMNS - 1 && y < ROWS - 1)
            neighbors.add(new Vector2I(x + 1, y + 1));
        if (x > 0 && y > 0)
            neighbors.add(new Vector2I(x - 1, y - 1));
        if (x > 0 && y < ROWS - 1)
            neighbors.add(new Vector2I(x - 1, y + 1));
        if (y > 0 && x < COLUMNS - 1)
            neighbors.add(new Vector2I(x + 1, y - 1));
        return neighbors;
    }

    private boolean isThereBlockHere(Vector2I pos) {
        for (int i = 0; i < blocks.size(); i++) {
            if (blocks.get(i) != null)
                if (blocks.get(i).equals(pos))
                    return true;
        }
        return false;
    }

    private ArrayList<Vector2I> createBlocks() {
        blocks = new ArrayList<>();
        g.setFill(blockColor);
        for (int i = 0; i < MAX_BLOACKS_NO; i++) {
            Vector2I block = new Vector2I((int) (Math.random() * COLUMNS), (int) (Math.random() * ROWS));
            if (!block.equals(goal) && !block.equals(start) && !isThereBlockHere(block)) {
                blocks.add(block);
                g.fillRect(block.getX() * CELL_SIZE, block.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            }
        }
        return blocks;
    }

    private void drawSet(ArrayList<Vector2I> set, Color color) {
        for (int i = 0; i < set.size(); i++) {
            g.setFill(color);
            g.fillRect(set.get(i).getX() * CELL_SIZE, set.get(i).getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
            // g.setFill(Color.BLACK);
            // g.fillText(counter++ + "", set.get(i).getX() * CELL_SIZE, set.get(i).getY() *
            // CELL_SIZE + 20);
        }
    }

    private int gCost(Vector2I currentPos) {
        return Math.abs(start.getX() - currentPos.getX()) + Math.abs(start.getY() - currentPos.getY());
    }

    private int hCost(Vector2I currentPos) {
        return Math.abs(goal.getX() - currentPos.getX()) + Math.abs(goal.getY() - currentPos.getY());
    }

    private int fCost(Vector2I currentPos) {
        return gCost(currentPos) + hCost(currentPos);
    }

    public ArrayList<Vector2I> getPath() {
        return path;
    }
}
