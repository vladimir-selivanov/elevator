package com.buffalo.transport;

public class Offer {
    private int number;
    private int cost;

    public Offer(int number, int cost) {
        this.number = number;
        this.cost = cost;
    }

    public int getNumber() {
        return number;
    }

    public int getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "number=" + number +
                ", cost=" + cost +
                '}';
    }
}
