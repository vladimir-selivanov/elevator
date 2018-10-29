package com.buffalo.transport;

public class Command {
    private int from;
    private int to;

    public Command(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Direction getDirection() {
        if (from - to > 0) {
            return Direction.UP;
        } else if (from - to < 0) {
            return Direction.DOWN;
        } else {
            return Direction.STOP;
        }
    }

    @Override
    public String toString() {
        return "Command{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
