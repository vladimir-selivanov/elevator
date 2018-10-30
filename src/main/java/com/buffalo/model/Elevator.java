package com.buffalo.model;

import com.buffalo.algorith.ElevatorAlgorithm;
import com.buffalo.algorith.ElevatorAlgorithmFactory;
import com.buffalo.algorith.resctiction.ElevatorSizeRestriction;
import com.buffalo.algorith.resctiction.Restriction;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Лифт - частный случай места
 */
public class Elevator extends Place {
    private static final int MAX_SIZE = 20;

    private final States states;
    private final ElevatorAlgorithm elevatorAlgorithm;
    private final List<Restriction> restrictions;

    public Elevator(int elevatorNumber) {
        super(String.valueOf(elevatorNumber));
        states = new States();
        states.add(State.FIRST);
        elevatorAlgorithm = ElevatorAlgorithmFactory.getInstance();
        restrictions = Collections.singletonList(new ElevatorSizeRestriction(this, MAX_SIZE));
    }

    public void addState(int from, int to) {
        State lastState = states.get(states.size() - 1);
        LocalDateTime start = LocalDateTime.now();
        if (lastState.getDirection() != Direction.STOP) {
            start = lastState.getEnd();
        }
        states.add(new State(from, to, start));
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public int getCost(Command command) {
        return elevatorAlgorithm.getCost(states, command, restrictions);
    }
}