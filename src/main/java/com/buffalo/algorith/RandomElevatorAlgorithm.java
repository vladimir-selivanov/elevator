package com.buffalo.algorith;

import com.buffalo.algorith.resctiction.Restriction;
import com.buffalo.model.State;
import com.buffalo.transport.Command;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class RandomElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getCost(List<State> states, Command command, List<Restriction> restrictions) {
        return RandomUtils.nextInt(1, 100);
    }
}
