package com.snake.gamelogic;

public class GridMap {
    private Item map[][];

    public GridMap(int sizeX, int sizeY) {
        map = new Item[sizeX][sizeY];
    }

    public void setItem(Item item, int x, int y) {
        if (x <= map.length && x >= 0 && y <= map[0].length && y >= 0) {
            map[x][y] = item;
        } else {
            System.err.println("Failed to set item position out of bounds: " + item.name() + " x" + x + " y" + y);
        }
    }

    public Item getItemAtPosition(int x, int y) {
        if (x <= map.length && x >= 0 && y <= map[0].length && y >= 0) {
            return map[x][y];
        }
        System.err.println("Failed to get item position out of bounds: x" + x + " y" + y);
        return null;
    }

    public Item[][] getMap() {
        return map;
    }

    public int getSizeX() {
        return map.length;
    }

    public int getSizeY() {
        return map[0].length;
    }
}
