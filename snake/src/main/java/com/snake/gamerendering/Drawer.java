package com.snake.gamerendering;

import com.snake.gamelogic.Item;

import javafx.geometry.Point3D;
import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.Shadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;

public class Drawer {
    private GraphicsContext g;
    private final int W = 1280;
    private final int H = 720;
    private static final int CELL_SIZE = 40;

    public Drawer(GraphicsContext g) {
        this.g = g;
    }

    public void drawRect(double x, double y, double w, double h, LinearGradient color) {
        g.setFill(color);
        g.fillRect(x, y, w, h);
    }

    public void drawRect(double x, double y, double w, double h, Color color) {
        g.setFill(color);
        g.fillRect(x, y, w, h);
    }

    public void drawText(String str, double x, double y, double zoomInOutValue) {
        g.setFont(new Font(zoomInOutValue));
        g.setStroke(Color.RED);
        g.strokeText(str, x, y);
    }

    public void drawImage(NewImage image, double x, double y, double w, double h) {
        g.drawImage(image.getImage(), x, y, w, h);
    }

    public void drawImage(NewImage image, double x, double y, double w, double h, double rotation) {
        g.drawImage(rotateImage(image.getImage(), rotation), x, y, w, h);
    }

    public Image rotateImage(Image image, double rotation) {
        Point3D yAxis = new Point3D(0, 1, 0);
        ImageView iv = new ImageView(image);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        params.setTransform(new Rotate(rotation, image.getHeight() / 2, image.getWidth() / 2, 0, yAxis));
        params.setViewport(new Rectangle2D(0, 0, image.getHeight(), image.getWidth()));
        return iv.snapshot(params, null);
    }

    public Color getColor(int length) {
        return Color.rgb((int) (255 * Math.random()),
                (int) (255 * Math.random()),
                (int) (255 * Math.random()));
    }

    public LinearGradient getLinearColor(int length, double startX, double startY, double endX, double endY) {
        double firstFunction = Math.sin(length * 90 * 3.14 / 180) + 1;
        double firstRadianAngele = length * 3.14 / 180;
        double secondFunction = Math.sin((length + 1) * 90 * 3.14 / 180) + 1;
        double secondRadianAngele = (length + 1) * 3.14 / 180;

        double r1 = firstFunction * (Math.sin(firstRadianAngele * 30) + 1) * 0.95 / 4.0;
        double g1 = firstFunction * (Math.sin(firstRadianAngele * 60) + 1) * 0.95 / 4.0;
        double b1 = firstFunction * (Math.sin(firstRadianAngele * 90) + 1) * 0.95 / 4.0;
        double r2 = secondFunction * (Math.sin(secondRadianAngele * 30) + 1) * 0.95 / 4.0;
        double g2 = secondFunction * (Math.sin(secondRadianAngele * 60) + 1) * 0.95 / 4.0;
        double b2 = secondFunction * (Math.sin(secondRadianAngele * 90) + 1) * 0.95 / 4.0;

        Stop[] stops = new Stop[] {
                new Stop(0, Color.color(r1, g1, b1)), new Stop(1, Color.color(r2, g2, b2))
        };
        LinearGradient color = new LinearGradient(startX, startY, endX, endY,
                true, CycleMethod.NO_CYCLE, stops);
        return color;
    }

    public void drawCircle(double x, double y, double w, double h, LinearGradient color) {
        Shadow shadow = new Shadow(0.5, Color.BLACK);
        Glow glow = new Glow(10);
        Bloom bloom = new Bloom(5);
        DropShadow dropShadow = new DropShadow(5, Color.BLACK);
        Lighting lighting = new Lighting(new Light.Spot(50, 50, 10, -50, Color.WHITE));
        // 20, 20, 10, Color.YELLOW

        g.setFill(color);
        g.setEffect(lighting);
        g.fillOval(x, y, w, h);
        g.setEffect(null);
    }

    public void draw() {
        for (int i = 0; i < H / CELL_SIZE; i++) {
            for (int j = 0; j < W / CELL_SIZE; j++) {
                g.setFill(Color.BLACK);
                g.strokeText(j + " " + i, j * CELL_SIZE, i * CELL_SIZE + 20);
            }
        }
    }

    public void drawGrid(Item[][] items) {
        for (int i = 0; i < items.length; i++) {
            for (int j = 0; j < items[i].length; j++) {
                if (items[i][j] != null) {
                    g.setFill(Color.BLACK);
                    g.fillRect(i * 40, j * 40, 40, 40);
                }
            }
        }
    }
}
