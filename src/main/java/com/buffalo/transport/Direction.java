package com.buffalo.transport;

public enum Direction {
    UP(5, 5),
    DOWN(5, 5),
    STOP(20, 20);

    private int duration;
    private int cost;

    public static Direction getInstance(int from, int to) {
        if (from - to > 0) {
            return UP;
        } else if (from - to < 0) {
            return DOWN;
        } else {
            return STOP;
        }
    }

    Direction(int duration, int cost) {
        this.duration = duration;
        this.cost = cost;
    }

    public int getDuration() {
        return duration;
    }

    public int getCost() {
        return cost;
    }
}
