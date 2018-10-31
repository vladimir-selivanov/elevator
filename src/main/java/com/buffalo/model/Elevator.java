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

    public Elevator(int number, int capacity) {
        super(String.valueOf(number));
        states = new States(capacity);
        states.addState(1, 1);
        elevatorAlgorithm = ElevatorAlgorithmFactory.getInstance();
        restrictions = Collections.singletonList(new ElevatorSizeRestriction(this, MAX_SIZE));
    }

    public List<Restriction> getRestrictions() {
        return restrictions;
    }

    public int getCost(Command command) {
        return elevatorAlgorithm.getCost(states, command, restrictions);
    }
}