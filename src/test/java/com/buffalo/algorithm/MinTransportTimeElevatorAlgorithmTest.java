package com.buffalo.algorithm;

import com.buffalo.algorith.ElevatorAlgorithm;
import com.buffalo.algorith.MinTransportTimeElevatorAlgorithm;
import com.buffalo.model.State;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MinTransportTimeElevatorAlgorithmTest {
    //todo Перевести на Spock
    public static void main(String[] args) {
        ElevatorAlgorithm elevatorAlgorithm = new MinTransportTimeElevatorAlgorithm();
        List<State> states;

        states = new ArrayList<>();
        states.add(State.FIRST);
        Command command = new Command(1, 9);
        int cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 1, 1 -> 9 = " + cost);

        states = new ArrayList<>();
        states.add(new State(9, 9, LocalDateTime.now()));
        command = new Command(9, 1);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 9, 9 -> 1 = " + cost);

        states = new ArrayList<>();
        states.add(State.FIRST);
        System.out.println(LocalDateTime.now());
        addRoute(states, 1, 4);
        addRoute(states, 4, 5);
        System.out.println(states);
        command = new Command(1, 5);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 1 -> 4 -> 5, 1 -> 5 = " + cost);

        states = new ArrayList<>();
        states.add(new State(5, 5, LocalDateTime.now()));
        System.out.println(LocalDateTime.now());
        addRoute(states, 5, 4);
        addRoute(states, 4, 1);
        System.out.println(states);
        command = new Command(5, 1);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 5 -> 4 -> 1, 5 -> 1 = " + cost);

        states = new ArrayList<>();
        states.add(State.FIRST);
        System.out.println(LocalDateTime.now());
        addRoute(states, 1, 4);
        addRoute(states, 4, 5);
        System.out.println(states);
        command = new Command(1, 9);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 1 -> 4 -> 5, 1 -> 9 = " + cost);

        states = new ArrayList<>();
        states.add(State.FIRST);
        System.out.println(LocalDateTime.now());
        addRoute(states, 1, 4);
        addRoute(states, 4, 5);
        System.out.println(states);
        command = new Command(4, 9);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 1 -> 4 -> 5, 4 -> 9 = " + cost);

        states = new ArrayList<>();
        states.add(State.FIRST);
        System.out.println(LocalDateTime.now());
        addRoute(states, 1, 4);
        addRoute(states, 4, 5);
        System.out.println(states);
        command = new Command(7, 3);
        cost = elevatorAlgorithm.getCost(states, command);
        System.out.println("cost 1 -> 4 -> 5, 7 -> 3 = " + cost);
    }

    public static void addState(List<State> states, int from, int to) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now;
        if (CollectionUtils.isNotEmpty(states)) {
            State lastState = states.get(states.size() - 1);
            // todo selivanov: тут может быть ошибка при построении виртуального графика
            if (lastState.getDirection() != Direction.STOP || now.isBefore(lastState.getEnd())) {
                start = lastState.getEnd();
            }
        }
        states.add(new State(from, to, start));
    }

    public static void addRoute(List<State> states, int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                addState(states, i, i + 1);
            }
        } else if (from > to) {
            for (int i = from; i > to; i--) {
                addState(states, i, i - 1);
            }
        }
        addState(states, to, to);
    }
}
