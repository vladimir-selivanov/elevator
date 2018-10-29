package com.buffalo.model;

import com.buffalo.ElevatorAlgorithm;
import com.buffalo.ElevatorAlgorithmFactory;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Elevator {
    final List<State> states;
    final ElevatorAlgorithm elevatorAlgorithm;

    public Elevator() {
        states = new ArrayList<>();
        states.add(State.FIRST);
        elevatorAlgorithm = ElevatorAlgorithmFactory.getInstance();
    }

    public void addState(int from, int to) {
        State lastState = states.get(states.size() - 1);
        LocalDateTime start = LocalDateTime.now();
        if (lastState.getDirection() != Direction.STOP) {
            start = lastState.getEnd();
        }
        states.add(new State(from, to, start));
    }

    public int getCost(Command command) {
        return elevatorAlgorithm.getCost(states, command);
    }
}