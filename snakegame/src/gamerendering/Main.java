package gamerendering;

import gamelogic.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

    private final int W = 720;
    private final int H = 720;
    private static final int CELL_SIZE = 40;
    private final int COLUMNS = W / CELL_SIZE;
    private final int ROWS = H / CELL_SIZE;
    private static final double COMPONENT_WIDTH_SIZE = CELL_SIZE - 3;
    private static final double COMPONENT_HEIGHT_SIZE = CELL_SIZE - 3;
    private Pane pane;
    private GraphicsContext g;
    private Drawer drawer;
    private GameLogic gameLogic;
    private Snake snake;
    private AnimationTimer timer;
    private long lastTime = System.currentTimeMillis();
    private Label highestScore;
    private Label currentScore;
    private final NewImage snakePart = new NewImage(new Image("assets/snakepart_01.png"), "snakePart");
    private final NewImage apple = new NewImage(new Image("assets/apple.png"), "apple");
    private final NewImage killer = new NewImage(new Image("assets/killer.png"), "killer");
    private final NewImage poison = new NewImage(new Image("assets/poison.png"), "poison");
    private final NewImage star = new NewImage(new Image("assets/star.png"), "star");
    private final NewImage slowerTimer = new NewImage(new Image("assets/slowertimer.png"), "slowerTimer");


    private boolean cheatingOn = false;

    @Override
    public void start(Stage stg) {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(this::keysPressed);
        stg.setResizable(false);
        stg.setScene(scene);
        stg.show();
    }

    private void keysPressed(KeyEvent event) {
        switch (event.getCode()) {
            case A:
                snake.setDirection(Directions.LEFT);
                break;
            case D:
                snake.setDirection(Directions.RIGHT);
                break;
            case W:
                snake.setDirection(Directions.UP);
                break;
            case S:
                snake.setDirection(Directions.DOWN);
                break;
            case H:
                cheatingOn = !cheatingOn;
                break;
        }

    }

    private Parent createContent() {
        pane = new Pane();
        pane.setPrefSize(W, H + 30);
        pane.setStyle("-fx-background-color: #161C2D;");

        initInformation();

        Canvas canvas = new Canvas(W, H);
        canvas.setLayoutX(0);
        canvas.setLayoutY(30);
        g = canvas.getGraphicsContext2D();

        gameLogic = new GameLogic(COLUMNS, ROWS);
        snake = gameLogic.getSnake();

        drawer = new Drawer(g);
        initTimer();

        pane.getChildren().add(canvas);
        return pane;
    }

    private void initInformation() {
        highestScore = new Label("Highest score: 0");
        highestScore.setTextFill(Color.WHITE);
        highestScore.setLayoutX(160);
        highestScore.setLayoutY(5);

        currentScore = new Label("Current score: 0");
        currentScore.setTextFill(Color.WHITE);
        currentScore.setLayoutX(15);
        currentScore.setLayoutY(5);

        Label creators = new Label("Daniel & Abd Al-Muttalib");
        creators.setTextFill(Color.WHITE);
        creators.setLayoutX(570);
        creators.setLayoutY(5);

        Rectangle rec = new Rectangle(W, 30);
        Border border = new Border(new BorderStroke(
                Color.WHITE,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT));
        pane.setBorder(border);

        pane.getChildren().addAll(rec, currentScore, highestScore, creators);
    }

    int degrees = 360;


    private void initTimer() {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (System.currentTimeMillis() - lastTime >= gameLogic.getSnake().getSpeed()) {
                    onUpdate();
                    lastTime = System.currentTimeMillis();
                }
                draw(degrees);
                degrees--;
            }
        }

        ;
        timer.start();
    }


    private void onUpdate() {
        gameLogic.update();

        currentScore.setText("Current score: " + gameLogic.getScore());
        highestScore.setText("Highest score: " + gameLogic.getHighestScore());

    }

    private void draw(int degrees) {
        g.clearRect(0, 0, W, H);
        drawItems(degrees);
        drawSnakePartAsImage();
    }


    private void drawItems(int degrees) {
        Item[] items = gameLogic.getItems();

        for (Item item : items) {
            double x = item.getPos().getX() * CELL_SIZE + (CELL_SIZE - COMPONENT_WIDTH_SIZE) / 2;
            double y = item.getPos().getY() * CELL_SIZE + (CELL_SIZE - COMPONENT_HEIGHT_SIZE) / 2;
            switch (item.name()) {
                case "Poison Apple":
                    drawer.drawImage(poison, x, y, COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE);
                    break;
                case "Apple":
                    drawer.drawImage(apple, x, y, COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE);
                    break;
                case "Star":
                    drawer.drawImage(star, x, y, COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE, degrees);
                    break;
                case "SlowerTimer":
                    drawer.drawImage(slowerTimer, x, y, COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE);
                    drawer.drawText(gameLogic.getZoomTime() + "", x + CELL_SIZE / 2, y + CELL_SIZE / 2, gameLogic.getZoomValue());
                    break;
                default:
                    drawer.drawImage(killer, x, y, COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE);
                    break;
            }
        }
    }

    private void drawSnakePartAsImage() {
        for (int i = 0; i < snake.getBody().size(); i++) {
            BodyPart p = snake.getBody().get(i);
            drawer.drawImage(snakePart,
                    p.getPos().getX() * CELL_SIZE + ((CELL_SIZE - COMPONENT_WIDTH_SIZE) / 2),
                    p.getPos().getY() * CELL_SIZE + ((CELL_SIZE - COMPONENT_HEIGHT_SIZE) / 2),
                    COMPONENT_WIDTH_SIZE, COMPONENT_HEIGHT_SIZE);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}