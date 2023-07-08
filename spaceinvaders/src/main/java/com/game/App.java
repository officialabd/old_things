package com.game;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import javax.swing.JOptionPane;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {

    private Pane pane = new Pane();
    private NewImage player;
    private NewImage enemy;
    private double xWide;
    private NewImage playerFire;
    private NewImage enemyFire;
    private double time = 0;
    private int count = 0;
    private int max = 2;
    private int level;
    private int kills;
    private boolean cheat = false;
    private int cheatPercentage;
    private NewImage BackGround1;
    private NewImage BackGround2;

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(initPane());

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case A:
                    if (player.getTranslateX() >= 20)
                        player.moveLeft((5 + (level / 2)));
                    break;
                case D:
                    if (player.getTranslateX() <= scene.getWidth() - 55)
                        player.moveRight((5 + (level / 2)));
                    break;
                case W:
                    if (player.getTranslateY() >= 500)
                        player.moveUp();
                    break;
                case S:
                    if (player.getTranslateY() <= 650)
                        player.moveDown();
                    break;
                case SPACE:
                    xWide = 0;
                    shoot(player);
                    break;
                case H:
                    if (cheat)
                        cheat = false;
                    else {
                        System.out.println("Enter your cheating percentage : ");
                        cheatPercentage = new Scanner(System.in).nextInt();
                        cheat = true;
                    }
                    break;
                default:
                    System.out.println("Out of range.");
                    break;
            }
        });

        stage.setScene(scene);
        stage.show();
    }

    private void setPlayerShoot(NewImage player, int noOfShoot) {
        int yDis = 0;
        if (noOfShoot == 1)
            yDis = 1;
        playerFire = new NewImage(player.getTranslateX() + (player.getFitWidth() / 2) * noOfShoot,
                player.getTranslateY() - (yDis * 20), new Image("file:src/main/assets/Images/playerShoot.jpg"),
                "playerFire" + noOfShoot);
        NewImage.setDimensions(playerFire, 9, 9);
        pane.getChildren().add(playerFire);
    }

    private void shoot(NewImage player) {
        if (level < 7) {
            setPlayerShoot(player, 1);
        } else if (level < 10) {
            setPlayerShoot(player, 0);
            setPlayerShoot(player, 2);
        } else {
            setPlayerShoot(player, 0);
            setPlayerShoot(player, 1);
            setPlayerShoot(player, 2);
        }
    }

    private List<NewImage> enemies() {
        return pane.getChildren().stream().map(node -> (NewImage) node).collect(Collectors.toList());
    }

    private NewImage temp = null;

    private void updateKills(NewImage fire) {
        enemies().stream().filter(en -> en.type.equals("enemy")).forEach(en -> {
            if (fire.getBoundsInParent().intersects(en.getBoundsInParent())) {
                en.isDead = true;
                fire.isDead = true;
                count++;
                kills++;
                temp = en;
            }
        });
        if (temp != null) {
            enemies().remove(temp);
            temp = null;
        }
    }

    private void update() {

        time += .01;
        enemies().forEach(node -> {
            switch (node.type) {
                case "playerFire2":
                    xWide += .0001;

                    node.moveRight(xWide);
                    node.moveUp();
                    if (cheat) {
                        getEnemyLocation();
                        locateWay(node, cheatPercentage);
                    }

                    updateKills(node);

                    break;
                case "playerFire1":
                    node.moveUp();
                    if (cheat) {
                        getEnemyLocation();
                        locateWay(node, cheatPercentage);
                    }

                    updateKills(node);
                    break;
                case "playerFire0":
                    xWide += .0001;
                    node.moveLeft(xWide);
                    node.moveUp();
                    if (cheat) {
                        getEnemyLocation();
                        locateWay(node, cheatPercentage);
                    }
                    updateKills(node);
                    break;
                case "enemyFire":
                    node.moveDown();
                    if (node.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        player.isDead = true;
                        node.isDead = true;
                        finished("You lose", false);
                    }
                    break;
                case "enemy":

                    if (time > 2)
                        if (Math.random() < .3)
                            initEnemyFire(node);
                    break;

                case "Background":

                    if (BackGround1.getTranslateY() == 1500)
                        BackGround1.setTranslateY(-1500);
                    if (BackGround2.getTranslateY() == 1500)
                        BackGround2.setTranslateY(-1500);
                    BackGround1.moveDown();
                    BackGround2.moveDown();
                    break;
            }
        });

        if (count >= max)
            finished("You won, next level", true);

        pane.getChildren().removeIf(node -> ((NewImage) node).isDead);

        if (time > 2)
            time = 0;
    }

    private void locateWay(NewImage im, int per) {
        double pX = im.getTranslateX(), pY = im.getTranslateY(), eX = xCo, eY = yCo;
        if (pX <= eX) {
            double x = Math.abs(eX - pX);
            double y = pY - eY;

            im.moveRight((x / y) * (Math.pow(5, per / 100.0)));
        } else {
            double x = Math.abs(pX - eX);
            double y = pY - eY;

            im.moveLeft((x / y) * (Math.pow(5, per / 100.0)));

        }
    }

    private double xCo, yCo;

    private void getEnemyLocation() {

        enemies().forEach(node -> {
            switch (node.type) {
                case "enemy":
                    xCo = node.getTranslateX();
                    yCo = node.getTranslateY();
                    break;
            }
        });
    }

    private Pane initPane() {
        pane.setPrefSize(500, 700);

        initBG();
        player = initializer(35, 35, 250, 500, player, "file:src/main/assets/Images/plane.jpg", "player");
        initEnemy();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
            }
        };
        timer.start();

        return pane;
    }

    private void initBG() {
        BackGround1 = initializer(1500, 500, 0, 0, BackGround1, "file:src/main/assets/Images/BG22.jpg", "Background");
        BackGround2 = initializer(1500, 500, 0, -1500, BackGround2, "file:src/main/assets/Images/BG2.jpg",
                "Background");
    }

    private void initEnemyFire(NewImage im) {
        enemyFire = initializer(10, 10, im.getTranslateX() + 10, im.getTranslateY(), enemyFire,
                "file:src/main/assets/Images/enemyShoot.jpg",
                "enemyFire");
    }

    private void initEnemy() {
        max += 2;

        for (int i = 1; i <= max; i++) {
            initializer(20, 20, Math.random() * 390 + 50, Math.random() * 200 + 20, enemy,
                    "file:src/main/assets/Images/enemy.jpg", "enemy");
        }
    }

    private NewImage initializer(double h, double w, double translateX, double translateY, NewImage im,
            String imLocation, String type) {
        im = new NewImage(translateX, translateY, new Image(imLocation), type);
        NewImage.setDimensions(im, h, w);
        pane.getChildren().add(im);
        return im;
    }

    private void finished(String str, boolean b) {

        // if ((level == 7 || level == 10) && false)
        // initBG();

        System.out.println("Level " + ++level + " :");
        System.out.println("count, max : " + count + " " + max);
        JOptionPane.showMessageDialog(null, str);
        if (b) {
            count = 0;
            initEnemy();

        } else {
            System.out.println("Finished, Kills : " + kills);
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
