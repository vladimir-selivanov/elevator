package com.buffalo.algorithm;

import com.buffalo.algorithm.resctiction.Restriction;
import com.buffalo.model.States;
import com.buffalo.transport.Command;
import org.apache.commons.lang3.RandomUtils;

import java.util.List;

public class RandomElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getCost(States states, Command command, List<Restriction> restrictions, boolean buildRoute) {
        return RandomUtils.nextInt(1, 100);
    }
}
