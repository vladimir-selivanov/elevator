package com.buffalo.algorith;

import com.buffalo.algorith.resctiction.Restriction;
import com.buffalo.model.State;
import com.buffalo.transport.Command;

import java.util.List;

public interface ElevatorAlgorithm {
    int getCost(List<State> states, Command command, List<Restriction> restrictions);
}
