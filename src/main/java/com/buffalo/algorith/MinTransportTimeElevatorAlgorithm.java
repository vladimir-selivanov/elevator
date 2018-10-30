package com.buffalo.algorith;

import com.buffalo.algorith.resctiction.Restriction;
import com.buffalo.model.State;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;

public class MinTransportTimeElevatorAlgorithm implements ElevatorAlgorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinTransportTimeElevatorAlgorithm.class);

    @Override
    public int getCost(List<State> states, Command command, List<Restriction> restrictions) {
        int result = 0;
        int currentStateIndex = getCurrentStateIndex(states);
        State state = states.get(currentStateIndex);
        Direction direction;
        boolean foundFrom = false;
        boolean foundTo = false;
//        int shift = 0;
        for (Restriction restriction : restrictions) {
            result += (restriction.denied() ? 100 : 0);
        }

        for (int stateIndex = currentStateIndex + 1; stateIndex < states.size(); stateIndex++) {
            state = states.get(stateIndex);
            direction = state.getDirection();
            result += direction.getCost();
            LOGGER.debug("{} {} {} ", 0, result, state);
//            state.shift(shift);
            if (!foundFrom) {
                if (state.getFrom() == command.getFrom()) {
                    if (direction == Direction.STOP) {
                        if (!hasNext(states, stateIndex)) {
                            foundFrom = true;
                            LOGGER.debug("{} {} {} ", 11, result, state);
                            break;
                        } else {
                            State nextState = states.get(stateIndex + 1);
                            Direction nextDirection = nextState.getDirection();
                            if (command.getDirection() == nextDirection) {
                                foundFrom = true;
                            }
                        }

                    } else if (command.getDirection() == state.getDirection()) {
                        foundFrom = true;
                        if (hasPrev(states, stateIndex)) {
                            State prevState = states.get(stateIndex - 1);
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection == Direction.STOP) {
                                LOGGER.debug("{} {} {} ", -12, result, state);
                                continue;
                            }
                        }
                        result += Direction.STOP.getCost();
                        LOGGER.debug("{} {} {} ", 12, result, state);
//                        State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                        states.add(stopState);
                    }
                }
            } else {
                if (!foundTo) {
                    if (state.getTo() == command.getTo()) {
                        if (direction == Direction.STOP) {
                            foundTo = true;
                            LOGGER.debug("{} {} {} ", 21, result, state);
                            break;
                        } else {
                            foundTo = true;
                            if (hasNext(states, stateIndex)) {
                                State nextState = states.get(stateIndex + 1);
                                Direction nextDirection = nextState.getDirection();
                                if (nextDirection == Direction.STOP) {
                                    LOGGER.debug("{} {} {} ", -22, result, state);
                                    continue;
                                }
                            }
                            result += Direction.STOP.getCost();
                            LOGGER.debug("{} {} {} ", 22, result, state);
//                            State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                            states.add(stopState);
                        }
                    } else {
                        if (direction == Direction.STOP && hasPrev(states, stateIndex) && hasNext(states, stateIndex)) {
                            State nextState = states.get(stateIndex + 1);
                            Direction nextDirection = nextState.getDirection();
                            State prevState = states.get(stateIndex - 1);
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection != nextDirection) {
                                foundTo = true;
                                int newStatesCount = Math.abs(command.getTo() - state.getTo());
                                result += Direction.UP.getCost() * newStatesCount;
                                result += Direction.DOWN.getCost() * newStatesCount;
                                result += Direction.STOP.getCost();
                                LOGGER.debug("{} {} {} ", 31, result, state);
//                            State stopState = new State(command.getFrom(), command.getFrom(), LocalDateTime.now());
//                            states.add(stopState); много раз
                                LOGGER.debug("{} {} {} ", 32, result, state);
                                break;
                            }
                        }
                    }
                }
            }
        }
        if (!foundFrom) {
            LOGGER.debug("{} {} {} ", 41, result, state);
            int routeFrom = (command.getFrom() > state.getTo() ? Direction.UP : Direction.DOWN).getCost() * Math.abs(command.getFrom() - state.getTo());
            result += routeFrom;
            LOGGER.debug("{} {} {} ", 41, result, state);
            if (routeFrom > 0) {
                result += Direction.STOP.getCost();
            }
            LOGGER.debug("{} {} {} ", 41, result, state);
            result += command.getDirection().getCost() * Math.abs(command.getFrom() - command.getTo());
            LOGGER.debug("{} {} {} ", 41, result, state);
            result += Direction.STOP.getCost();
            LOGGER.debug("{} {} {} ", 41, result, state);
        } else if (!foundTo) {
            LOGGER.debug("{} {} {} ", 42, result, state);
            result += command.getDirection().getCost() * Math.abs(state.getTo() - command.getTo());
            LOGGER.debug("{} {} {} ", 42, result, state);
            result += Direction.STOP.getCost();
            LOGGER.debug("{} {} {} ", 42, result, state);
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
