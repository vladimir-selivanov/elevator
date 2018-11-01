package com.buffalo.transport;

public class Command {
    private int from;
    private int to;
    private Direction direction;

    public Command(int from, int to) {
        this.from = from;
        this.to = to;
        this.direction = Direction.getInstance(from, to);
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return "Command{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
