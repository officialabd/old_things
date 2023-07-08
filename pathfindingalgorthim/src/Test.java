import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Test extends Application {
    private Pane root = new Pane();
    private final int CELL_SIZE = 10;
    private final int W = 800;
    private final int H = 50;
    private final int MAX_BLOACKS_NO = 0;
    private final int COLUMNS = W / CELL_SIZE;
    private final int ROWS = H / CELL_SIZE;
    private GraphicsContext g;
    private PathFinding pathFinding;

    @Override
    public void start(Stage stg) {
        stg.setScene(new Scene(createContent()));
        stg.show();
    }

    private Parent createContent() {
        root.setPrefSize(W, H);

        Canvas canvas = new Canvas(W, H);
        g = canvas.getGraphicsContext2D();
        // draw();

        initPathFinding();

        root.getChildren().addAll(canvas);
        return root;
    }

    private void initPathFinding() {
        Vector2I pos1 = new Vector2I(0, 0);
        Vector2I pos2 = new Vector2I(COLUMNS - 1, ROWS - 1);

        pathFinding = new PathFinding(COLUMNS, ROWS, CELL_SIZE, pos2, pos1, g, Color.BLACK, MAX_BLOACKS_NO);
        drawPath(pathFinding.getPath(), Color.RED);
    }

    private void drawPath(ArrayList<Vector2I> path, Color col) {
        g.setFill(col);
        for (int i = 0; i < path.size(); i++) {
            Vector2I pos = path.get(i);
            g.fillRect(pos.getX() * CELL_SIZE, pos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    private Directions generateDirection(int index) {
        ArrayList<Vector2I> way = pathFinding.getPath();
        if (findDiff(way.get(index), way.get(index + 1)).equals(Directions.DOWN))
            return Directions.DOWN;
        if (findDiff(way.get(index), way.get(index + 1)).equals(Directions.UP))
            return Directions.UP;
        if (findDiff(way.get(index), way.get(index + 1)).equals(Directions.LEFT))
            return Directions.LEFT;
        if (findDiff(way.get(index), way.get(index + 1)).equals(Directions.RIGHT))
            return Directions.RIGHT;
        return null;
    }

    private Vector2I findDiff(Vector2I pos1, Vector2I pos2) {
        return new Vector2I(pos2.getX() - pos1.getX(), pos2.getY() - pos1.getY());
    }

    public void draw() {
        g.setStroke(Color.BLACK);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                g.strokeText(j + " " + i, j * CELL_SIZE, i * CELL_SIZE + 10);
            }
        }
    }

    private void drawPath(Vector2I head, ArrayList<Directions> path) {
        Vector2I postPos = head;
        g.setStroke(Color.RED);
        for (int i = 0; i < path.size(); i++) {
            postPos = combinePoints(postPos, path.get(i));
            g.strokeRect(postPos.getX() * CELL_SIZE, postPos.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }
    }

    public Vector2I combinePoints(Vector2I head, Directions directions) {
        Vector2I pos = new Vector2I(head.getX() + directions.pos.getX(), head.getY() + directions.pos.getY());

        return pos;
    }

    public static void main(String[] args) {
        launch(args);
    }
}