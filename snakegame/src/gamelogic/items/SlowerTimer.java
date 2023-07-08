package gamelogic.items;

import gamelogic.GameLogic;
import gamelogic.Item;
import gamelogic.Snake;
import gamelogic.Vector2I;

public class SlowerTimer implements Item {
    private Vector2I pos = new Vector2I();
    private Snake snake;
    private GameLogic gameLogic;
    private long spawnTime;
    private long slowModeTime;
    private boolean isTaken = false;

    public SlowerTimer(Snake snake) {
        this.snake = snake;
    }

    public SlowerTimer(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.snake = gameLogic.getSnake();
        this.spawnTime = System.currentTimeMillis();
    }

    @Override
    public Vector2I getPos() {
        return pos;
    }

    @Override
    public boolean isHarmful() {
        return false;
    }

    @Override
    public String name() {
        return "SlowerTimer";
    }

    @Override
    public void onAction() {
        snake.setSpeed(130);
        slowModeTime = System.currentTimeMillis();
        setTaken(true);
    }

    public long getSpawnTime() {
        return spawnTime;
    }

    public void setSpawnTime(long spawnTime) {
        this.spawnTime = spawnTime;
    }

    public long getSlowModeTime() {
        return slowModeTime;
    }

    public boolean isTaken() {
        return isTaken;
    }

    public void setTaken(boolean taken) {
        isTaken = taken;
    }
}
