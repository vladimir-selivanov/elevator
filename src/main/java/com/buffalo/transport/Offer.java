package com.buffalo.transport;

import com.buffalo.model.Elevator;

public class Offer {
    private int number;
    private int cost;

    public Offer(Elevator elevator, Command command) {
        number = elevator.getName();
        cost = elevator.getCost(command);
    }

    Offer(int number, int cost) {
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
