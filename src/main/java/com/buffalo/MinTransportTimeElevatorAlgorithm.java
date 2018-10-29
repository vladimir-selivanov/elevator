package com.buffalo;

import com.buffalo.model.State;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;
import org.apache.commons.lang3.RandomUtils;

import java.time.LocalDateTime;
import java.util.List;

public class MinTransportTimeElevatorAlgorithm implements ElevatorAlgorithm {
    @Override
    public int getCost(List<State> states, Command command) {
        int result = 0;
        int currentStateIndex = getCurrentStateIndex(states);
        State state = states.get(currentStateIndex);
        Direction direction = state.getDirection();
        boolean foundFrom = false;
        boolean foundTo = false;
//        int shift = 0;
        for (int sateIndex = currentStateIndex + 1; sateIndex < states.size(); sateIndex++) {
            state = states.get(sateIndex);
            direction = state.getDirection();
            result += direction.getCost();
//            System.out.println(0 + " " + result + " " + state);
//            state.shift(shift);
            if (!foundFrom) {
                if (state.getFrom() == command.getFrom()) {
                    if (direction == Direction.STOP) {
                        if (!hasNext(states, sateIndex)) {
                            foundFrom = true;
//                            System.out.println(11 + " " + result + " " + state);
                            break;
                        } else {
                            State nextState = states.get(sateIndex + 1);
                            Direction nextDirection = nextState.getDirection();
                            if (command.getDirection() == nextDirection) {
                                foundFrom = true;
                            }
                        }

                    } else if (command.getDirection() == state.getDirection()) {
                        foundFrom = true;
                        if (hasPrev(states, sateIndex)) {
                            State prevState = states.get(sateIndex - 1);
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection == Direction.STOP) {
//                                System.out.println(-12 + " " + result + " " + state);
                                continue;
                            }
                        }
                        result += Direction.STOP.getCost();
//                        System.out.println(12 + " " + result + " " + state);
//                        State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                        states.add(stopState);
                    }
                }
            } else {
                if (!foundTo) {
                    if (state.getTo() == command.getTo()) {
                        if (direction == Direction.STOP) {
                            foundTo = true;
//                            System.out.println(21 + " " + result + " " + state);
                            break;
                        } else {
                            foundTo = true;
                            if (hasNext(states, sateIndex)) {
                                State nextState = states.get(sateIndex + 1);
                                Direction nextDirection = nextState.getDirection();
                                if (nextDirection == Direction.STOP) {
//                                    System.out.println(-22 + " " + result + " " + state);
                                    continue;
                                }
                            }
                            result += Direction.STOP.getCost();
//                            System.out.println(22 + " " + result + " " + state);
//                            State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                            states.add(stopState);
                        }
                    } else {
                        if (direction == Direction.STOP && hasPrev(states, sateIndex) && hasNext(states, sateIndex)) {
                            State nextState = states.get(sateIndex + 1);
                            Direction nextDirection = nextState.getDirection();
                            State prevState = states.get(sateIndex - 1);
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection != nextDirection) {
                                foundTo = true;
                                int newStatesCount = Math.abs(command.getTo() - state.getTo());
                                result += Direction.UP.getCost() * newStatesCount;
                                result += Direction.DOWN.getCost() * newStatesCount;
                                result += Direction.STOP.getCost();
//                                System.out.println(31 + " " + result + " " + state);
//                            State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                            states.add(stopState); много раз
//                                System.out.println(32 + " " + result + " " + state);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!foundFrom) {
//            System.out.println(41 + " " + result);
            result += (command.getFrom() > state.getTo() ? Direction.UP : Direction.DOWN).getCost() * Math.abs(command.getFrom() - state.getTo());
//            System.out.println(41 + " " + result);
            result += Direction.STOP.getCost();
//            System.out.println(41 + " " + result);
            result += command.getDirection().getCost() * Math.abs(command.getFrom() - command.getTo());
//            System.out.println(41 + " " + result);
            result += Direction.STOP.getCost();
//            System.out.println(41 + " " + result);
        } else if (!foundTo) {
//            System.out.println(42 + " " + result);
            result += command.getDirection().getCost() * Math.abs(state.getTo() - command.getTo());
//            System.out.println(42 + " " + result);
            result += Direction.STOP.getCost();
//            System.out.println(42 + " " + result);
        }
        result -= Direction.STOP.getCost();
        return result;
    }

    private int getCurrentStateIndex(List<State> states) {
        int result = states.size() - 1;
        LocalDateTime now = LocalDateTime.now();
        State state = states.get(result);
        while (state.getStart().isAfter(now)) {
            result--;
            state = states.get(result);
        }
        return result;
    }

    private boolean hasNext(List<State> states, int index) {
        return index < states.size() - 1;
    }

    private boolean hasPrev(List<State> states, int index) {
        return index > 0;
    }
}
