import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.ArrayList;


public class MainAppClass extends Application {

    private final int W = 1440;
    private final int H = 900;
    private final double screenH = Screen.getPrimary().getBounds().getHeight();
    private final double screenW = Screen.getPrimary().getBounds().getWidth();
    private final double DRAW_PER_PIXEL = 40;
    private ArrayList<FunctionBuffer> functions = new ArrayList<>();
    private ListView<String> functionsID = new ListView<>();
    private Pane root;
    private Label label;
    private ContextMenu contextMenu;
    private Drawer drawer;
    private ColorPicker colorPicker;

    @Override
    public void start(Stage stg) {
        stg.setScene(new Scene(createContent()));
        stg.setMaximized(true);
        stg.show();
    }

    private Parent createContent() {
        root = new Pane();
        root.setPrefSize(screenW, screenH - 50);

        Canvas canvas = new Canvas(screenW, H + 20);
        canvas.setTranslateX(200);
        canvas.setTranslateY(0);
        canvas.setOnMouseClicked(event -> mouseClicked(event));

        drawer = new Drawer(canvas.getGraphicsContext2D(), screenW - 250, H, functions);
        drawer.drawPlane();

        createFunctionMenu();

        root.getChildren().addAll(canvas, createSidedMenu());
        return root;
    }


    private void addFunction(String functionText, Color color) {

        try {
            functions.add(initFunction(functionText, color));
            functions.trimToSize();
            functionsID.getItems().add(functionText);
            drawer.drawFunction();
            //label.setText("Enter your function:");
        } catch (Exception e) {
            label.setText("You should assign value to all fields");
        }
    }

    private FunctionBuffer initFunction(String functionText, Color color) {
        FunctionBuffer function = new FunctionBuffer(functionText, color);
        return function;
    }


    private void createFunctionMenu() {
        contextMenu = new ContextMenu();
        MenuItem remove = new MenuItem("Remove");
        contextMenu.getItems().add(remove);
    }


    private void mouseClicked(MouseEvent event) {
        double mouseXLayout = event.getSceneX() - 200;
        double mouseYLayout = event.getSceneY();
        if (!functionsID.getItems().isEmpty()) {
            for (int i = 0; i < functions.size(); i++) {
                double newX = (mouseXLayout - 1280 / 2) / DRAW_PER_PIXEL;
                double y = functions.get(i).getFormattedFunction(newX).evaluate();
                double drawnY = (960) / 2 - y * DRAW_PER_PIXEL;

                if (drawnY >= (mouseYLayout - 7) && drawnY < (mouseYLayout + 7)) {
                    functionOptionsMenu(i, event);
                    functions.trimToSize();
                    break;
                } else if (contextMenu.isShowing())
                    contextMenu.hide();

            }
        }
    }


    private void functionOptionsMenu(int selectedFunction, MouseEvent event) {
        contextMenu.getItems().get(0).setOnAction(removeEvent -> remove(selectedFunction));
        if (!contextMenu.isShowing())
            contextMenu.show(root, event.getScreenX(), event.getScreenY());
        else
            contextMenu.hide();
    }

    private void remove(int selectedIndex) {
        try {
            functions.remove(selectedIndex);
            functionsID.getItems().remove(selectedIndex);
            functions.trimToSize();
            drawer.drawFunction();
            label.setText("Removed!");
        } catch (Exception e) {
            if (functionsID.getItems().isEmpty()) label.setText("There's no function assigned!");
            else label.setText("Maybe choose one?");
        }
    }

    // ------------------------------------------------------Later
    private void update() {
        try {
            int selectedIndex = functionsID.getSelectionModel().getSelectedIndex();
            setFunctionColor(functions.get(selectedIndex), Color.BLUE);
            drawer.drawFunction();
            label.setText("Updated!");
        } catch (Exception e) {
            if (functionsID.getItems().isEmpty()) label.setText("There's no function assigned!");
            else label.setText("Maybe choose one?");
        }
    }

    private Parent createSidedMenu() {
        Pane root = new Pane();
        root.setPrefSize(200, H);

        label = new Label("Enter your function:");
        label.setTranslateX(5);
        label.setTranslateY(10);

        TextField textField = new TextField();
        textField.setPromptText("Ex: 2x^3 + 5");
        textField.setTranslateX(5);
        textField.setTranslateY(70);

        Button draw = new Button("Draw");
        draw.setTranslateX(10);
        draw.setTranslateY(H - 20);
        draw.setOnAction(event -> {
            try {
                addFunction(textField.getText(), colorPicker.getValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Button remove = new Button("Delete Function");
        remove.setTranslateX(60);
        remove.setTranslateY(H - 20);
        remove.setOnAction(event -> remove(functionsID.getSelectionModel().getSelectedIndex()));

        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.BLACK);
        colorPicker.setTranslateX(5);
        colorPicker.setTranslateY(100);
        colorPicker.setOnAction(event -> colorPicker.getValue());

        functionsID.setPrefWidth(185);
        functionsID.setTranslateX(5);
        functionsID.setTranslateY(130);
        functionsID.setEditable(true);
        functionsID.setCellFactory(TextFieldListCell.forListView());

        functionsID.addEventHandler(ActionEvent.ANY, event -> {
            int selectedIndex;
            if (functionsID.getItems().size() > 0) {
                try {
                    System.out.println("test");
                    addFunction(functionsID.getSelectionModel().getSelectedItem(), colorPicker.getValue());

                    drawer.drawFunction();
                } catch (Exception e) {
                    label.setText("Enter your function!");
                }


            }
        });
//            if (event.getButton() == MouseButton.PRIMARY && functionsID.getItems().size() > 0 && event.getClickCount() == 2) {
//                selectedIndex = functionsID.getSelectionModel().getSelectedIndex();
//            }


        root.getChildren().

                addAll(label, textField, draw, remove, colorPicker, functionsID);
        return root;
    }

    private void setFunctionColor(FunctionBuffer function, Color color) {
        function.setColor(color);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
