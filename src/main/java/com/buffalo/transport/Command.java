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

    @Override
    public String toString() {
        return "Command{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
