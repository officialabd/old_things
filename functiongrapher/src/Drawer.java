import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Drawer {
    private GraphicsContext g;
    private final int W = 1280;
    private final int H = 960;
    private double screenH;
    private double screenW;
    private final double DRAW_PER_PIXEL = 40;
    private ArrayList<FunctionBuffer> functions;
    

    Drawer() {
        this(new Canvas().getGraphicsContext2D(), null);
    }

    Drawer(ArrayList<FunctionBuffer> functions) {
        this(new Canvas().getGraphicsContext2D(), functions);
    }

    public Drawer(GraphicsContext g, ArrayList<FunctionBuffer> functions) {
        this(g, 0, 0, functions);
    }

    public Drawer(GraphicsContext g, double screenW, double screenH, ArrayList<FunctionBuffer> functions) {
        this.g = g;
        this.screenH = screenH;
        this.screenW = screenW;
        this.functions = functions;
    }

    public void drawFunction() {
        g.clearRect(0, 0, W, H);
        drawPlane();
        functions.forEach(f -> {
            g.setStroke(f.getColor());
            g.setLineWidth(1.4);

            double oldX = 0;
            double x1 = (oldX - W / 2) / DRAW_PER_PIXEL;
            double y1 = f.getFormattedFunction(x1).evaluate();
            double oldY = H / 2 - y1 * DRAW_PER_PIXEL;

            for (int drawX = 0; drawX < W; drawX++) {
                double newX = (drawX - W / 2) / DRAW_PER_PIXEL;
                double y = f.getFormattedFunction(newX).evaluate();
                double drawY = H / 2 - y * DRAW_PER_PIXEL;
                g.strokeLine(oldX, oldY, drawX, drawY);
                oldX = drawX;
                oldY = drawY;
            }
        });
    }

    public void drawPlane() {
        g.setStroke(Color.BLACK);

        for (int i = 0; i <= W / DRAW_PER_PIXEL; i++) {
            if (i == W / DRAW_PER_PIXEL / 2) g.setLineWidth(1.1);
            else g.setLineWidth(0.5);
            g.strokeLine(i * DRAW_PER_PIXEL, 0, i * DRAW_PER_PIXEL, H);
            g.strokeText(String.valueOf((int) (i - W / DRAW_PER_PIXEL / 2)), i * DRAW_PER_PIXEL, H / 2);
        }
        for (int i = 0; i <= H / DRAW_PER_PIXEL; i++) {
            if (i == H / DRAW_PER_PIXEL / 2) g.setLineWidth(1.1);
            else g.setLineWidth(0.5);
            g.strokeLine(0, i * DRAW_PER_PIXEL, W, i * DRAW_PER_PIXEL);
            g.strokeText(String.valueOf((int) (H / DRAW_PER_PIXEL / 2 - i)), W / 2, i * DRAW_PER_PIXEL);

        }
    }


}
