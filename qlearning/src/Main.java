import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application {
    Pane root;
    GraphicsContext g;
    int ACTIONS = 4;
    int COLUMNS = 20;
    int ROWS = 20;
    int CELL_SIZE = 80;
    int GRID_WIDTH = 1600;//COLUMNS * CELL_SIZE
    int GRID_HEIGHT = 1600;//ROWS * CELL_SIZE
     

    @Override
    public void start(Stage stg) {
        stg.setScene(new Scene(createContent()));
        stg.show();
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(GRID_WIDTH, GRID_HEIGHT);

        Canvas canvas = new Canvas(GRID_WIDTH, GRID_HEIGHT);
        g = canvas.getGraphicsContext2D();

        initQLearning();

        root.getChildren().addAll(canvas);
        return root;
    }

    private void initQLearning() {
        QLearning q = new QLearning(ACTIONS, COLUMNS, ROWS, CELL_SIZE, g);
        q.run();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
