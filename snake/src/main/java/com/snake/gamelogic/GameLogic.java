package com.snake.gamelogic;

import java.util.ArrayList;

import com.snake.gamelogic.items.Apple;
import com.snake.gamelogic.items.Killer;
import com.snake.gamelogic.items.PoisonApple;
import com.snake.gamelogic.items.SlowerTimer;
import com.snake.gamelogic.items.Star;

public class GameLogic {
    private final Snake snake;
    private final GridMap map;
    private ArrayList<Item> items;
    private int score = 0;
    private int highestScore = 0;
    private double zoomValue = 30;
    private long zoomTime = 5;
    private SlowerTimer slowerTimer;
    public boolean isAppleTaken = false;
    private Item apple;

    public GameLogic(int gridXSize, int gridYSize) {
        snake = new Snake(new BodyPart(new Vector2I(20, 20), Directions.DOWN));
        map = new GridMap(gridXSize, gridYSize);
        items = new ArrayList<>();
        apple = new Apple(this);
        spawnItem(apple);
        spawnItem(new Killer(snake));
        spawnItem(new PoisonApple(this));

    }

    public void update() {
        boolean collided = snake.collided();
        boolean outOfBounds = snake.getX() > map.getSizeX() - 1 || snake.getX() < 0 || snake.getY() > map.getSizeY() - 1
                || snake.getY() < 0;
        if (collided || outOfBounds) {
            snake.reset();
            score = 0;
        }
        if (slowerTimer != null && System.currentTimeMillis() - slowerTimer.getSlowModeTime() >= 10000
                && slowerTimer.isTaken()) {
            snake.setSpeed();
            slowerTimer = null;
        }
        checkSlowerTimer();
        Item item = map.getItemAtPosition(snake.getX(), snake.getY());
        if (item != null) {
            map.setItem(null, snake.getX(), snake.getY());
            snake.doAction(item);
            if (snake.getBody().size() == 0) {
                snake.reset();
                score = 0;
            }

            if (score > highestScore)
                highestScore = score;
            items.remove(item);
            items.trimToSize();
            if (item instanceof Apple) {
                apple = new Apple(this);
                spawnItem(apple);
                isAppleTaken = false;
            } else if (item instanceof PoisonApple) {
                spawnItem(new PoisonApple(this));
            } else if (item instanceof Killer) {
                spawnItem(new Killer(snake));
            }
        }

        spawnSpecialItems();
        snake.update();
    }

    private void checkSlowerTimer() {
        if (slowerTimer != null) {
            zoomValue -= 2;
            if (System.currentTimeMillis() - slowerTimer.getSpawnTime() >= 1000) {
                slowerTimer.setSpawnTime(slowerTimer.getSpawnTime() + 1000);
                zoomValue = 30;
                zoomTime--;
            }
            if (zoomTime <= 0) {
                items.remove(slowerTimer);
                map.setItem(null, slowerTimer.getPos().getX(), slowerTimer.getPos().getY());
                zoomTime = 5;
                if (!slowerTimer.isTaken())
                    slowerTimer = null;
            }
        }
    }

    private void spawnSpecialItems() {
        if (slowerTimer == null && snake.getSpeed() < 120 && Math.random() < 0.002 && Math.random() > 0.001) {
            slowerTimer = new SlowerTimer(this);
            spawnItem(slowerTimer);
        }
        if (Math.random() < 0.002 && Math.random() > 0.001 && snake.getBody().size() > 10)
            spawnItem(new Star(this));
    }

    public void spawnItem(Item item) {
        boolean unplaced = true;
        Vector2I pos = new Vector2I();
        while (unplaced) {
            pos.setX((int) (Math.random() * map.getSizeX()));
            pos.setY((int) (Math.random() * map.getSizeY()));
            item.getPos().setX(pos.getX());
            item.getPos().setY(pos.getY());
            if (map.getItemAtPosition(pos.getX(), pos.getY()) == null)
                unplaced = false;
        }
        map.setItem(item, pos.getX(), pos.getY());
        items.add(items.size(), item);
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Item[] getItems() {
        Item[] items = new Item[this.items.size()];
        for (int i = 0; i < items.length; i++) {
            items[i] = this.items.get(i);
        }
        return items;
    }

    public int getScore() {
        return score;
    }

    public Snake getSnake() {
        return snake;
    }

    public GridMap getMap() {
        return map;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public double getZoomValue() {
        return zoomValue;
    }

    public long getZoomTime() {
        return zoomTime;
    }

    public Item getApple() {
        return apple;
    }
}
