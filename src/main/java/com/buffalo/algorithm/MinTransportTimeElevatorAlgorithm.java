package com.buffalo.algorithm;

import com.buffalo.algorithm.resctiction.Restriction;
import com.buffalo.model.State;
import com.buffalo.model.States;
import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class MinTransportTimeElevatorAlgorithm implements ElevatorAlgorithm {
    private static final Logger LOGGER = LoggerFactory.getLogger(MinTransportTimeElevatorAlgorithm.class);

    @Override
    public int getCost(States states, Command command, List<Restriction> restrictions, boolean buildRoute) {
        Context context = new Context(buildRoute, getCurrentStateIndex(states));
        State state = null;
        Direction direction = null;

        while (context.getIndex() < states.size()) {
            state = states.get(context.getIndex());
            direction = state.getDirection();
            context.incrementCost(direction);
            LOGGER.debug("{} {} {}", 0, context, state);
            if (!state.hasFreeSpace()) {
                context.incrementIndex();
                continue;
            }
            state.shift(context.getShift());
            if (context.isFoundFrom() && !context.isFoundTo()) {
                state.decrementCapacity();
            }
            // Перемещение пассажира еще не начато
            if (!context.isFoundFrom()) {
                // Ищем место посадки пассажира
                if (state.getFrom() == command.getFrom()) {
                    // Найденное место - это остановка
                    if (direction == Direction.STOP) {
                        if (!hasNext(states, context.getIndex())) {
                            context.setFoundFrom(true);
                            state.decrementCapacity();
                            break;
                        } else {
                            State nextState = states.get(context.getNextIndex());
                            Direction nextDirection = nextState.getDirection();
                            if (command.getDirection() == nextDirection) {
                                context.setFoundFrom(true);
                                state.decrementCapacity();
                                LOGGER.debug("{} {} {}", 12, context, state);
                            }
                        }
                        // Нужно добавить остановку для пасадки пассажира
                    } else if (command.getDirection() == state.getDirection()) {
                        context.setFoundFrom(true);
                        if (hasPrev(states, context.getIndex())) {
                            // Есть подозрение, что этот блок можно удалить, так как эта проверка уже была выполнена на предыдущем шаге
                            State prevState = states.get(context.getPrevIndex());
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection == Direction.STOP) {
                                LOGGER.debug("{} {} {}", -13, context, state);
                                context.incrementIndex();
                                continue;
                            }
                        }
                        context.incrementCost(Direction.STOP);
                        if (context.isBuildRoute()) {
                            State newState = states.addState(context.getIndex(), command.getFrom(), command.getFrom());
                            newState.decrementCapacity();
                            context.incrementShift(Direction.STOP);
                            context.incrementIndex();
                        }
                        LOGGER.debug("{} {} {}", 13, context, state);
                    }
                }
            } else {
                // Пассажир уже перемещается
                if (!context.isFoundTo()) {
                    // Ищем место высадки пасажира
                    if (state.getTo() == command.getTo()) {
                        // Остановка уже запланирована
                        if (direction == Direction.STOP) {
                            context.setFoundTo(true);
                            LOGGER.debug("{} {} {}", 21, context, state);
                            break;
                            // Остановка будет следующим шагом
                        } else {
                            context.setFoundTo(true);
                            state.incrementCapacity();
                            // Нашли остановку
                            if (hasNext(states, context.getIndex())) {
                                State nextState = states.get(context.getNextIndex());
                                Direction nextDirection = nextState.getDirection();
                                if (nextDirection == Direction.STOP) {
                                    LOGGER.debug("{} {} {}", -22, context, state);
                                    context.incrementIndex();
                                    continue;
                                }
                            }
                            // Запланировали остановку
                            context.incrementCost(Direction.STOP);
                            if (context.isBuildRoute()) {
                                State newState = states.addState(command.getFrom(), command.getFrom());
                                newState.incrementCapacity();
                                context.incrementShift(Direction.STOP);
                                context.incrementIndex();
                            }
                            LOGGER.debug("{} {} {}", 22, context, state);
                        }
                        // Разворот лифта
                    } else {
                        if (direction == Direction.STOP && hasPrev(states, context.getIndex()) && hasNext(states, context.getIndex())) {
                            State nextState = states.get(context.getNextIndex());
                            Direction nextDirection = nextState.getDirection();
                            State prevState = states.get(context.getPrevIndex());
                            Direction prevDirection = prevState.getDirection();
                            if (prevDirection != nextDirection) {
                                context.setFoundTo(true);
                                state.incrementCapacity();
                                LOGGER.debug("{} {} {}", 31, context, state);
                                buildRoute(states, context, state.getTo(), command.getTo(), state.getTo());
                                LOGGER.debug("{} {} {}", 32, context, state);
                                break;
                            }
                        }
                    }
                }
            }
            context.incrementIndex();
        }
        if (!context.isFoundFrom()) {
            LOGGER.debug("{} {} {}", 41, context, state);
            buildRoute(states, context, state.getTo(), command.getFrom(), command.getTo());
            LOGGER.debug("{} {} {}", 42, context, state);
        } else if (!context.isFoundTo()) {
            LOGGER.debug("{} {} {}", 51, context, state);
            buildRoute(states, context, state.getTo(), command.getTo());
            LOGGER.debug("{} {} {}", 52, context, state);
            LOGGER.debug("{}", states);
        }
        context.decrementCost(Direction.STOP);
        context.decrementCost(Direction.STOP);
        if (context.isBuildRoute()) {
            LOGGER.debug("States: {}", states);
        }
        return context.getCost();
    }

    private int getCurrentStateIndex(States states) {
        int result = states.size() - 1;
        LocalDateTime now = LocalDateTime.now();
        State state = states.get(result);
        while (state.getStart().isAfter(now)) {
            result--;
            state = states.get(result);
        }
        return result;
    }

    private boolean hasNext(States states, int index) {
        return index < states.size() - 1;
    }

    private boolean hasPrev(States states, int index) {
        return index > 0;
    }

    private void buildRoute(States states, Context context, int... floors) {
        for (int i = 0; i < floors.length - 1; i++) {
            int from = floors[i];
            int to = floors[i + 1];
            Direction direction = Direction.getInstance(from, to);
            int[] subRouteFloors = ((direction == Direction.UP) ? IntStream.rangeClosed(from, to) : IntStream.rangeClosed(to, from)).toArray();
            if (direction == Direction.DOWN) {
                ArrayUtils.reverse(subRouteFloors);
            }
            for (int j = 0; j < subRouteFloors.length - 1; j++) {
                int floor = subRouteFloors[j];
                int nextFloor = subRouteFloors[j + 1];
                State state = context.isBuildRoute() ? states.addState(context.getIndex(), floor, nextFloor) : states.createState(floor, nextFloor);
                context.incrementCost(state.getDirection());
                if (context.isBuildRoute()) {
                    context.incrementShift(state.getDirection());
                    context.incrementIndex();
                }
            }
            State state = context.isBuildRoute() ? states.addState(context.getIndex(), to, to) : states.createState(to, to);
            context.incrementCost(state.getDirection());
            if (context.isBuildRoute()) {
                context.incrementShift(state.getDirection());
                context.incrementIndex();
            }
        }
    }


    private class Context {
        private boolean buildRoute;
        private int cost;
        private int shift;
        private int index;
        private boolean foundFrom;
        private boolean foundTo;

        public Context(boolean buildRoute, int index) {
            this.buildRoute = buildRoute;
            this.index = index;
        }

        public boolean isBuildRoute() {
            return buildRoute;
        }

        public int getCost() {
            return cost;
        }

        public void incrementCost(int value) {
            cost += value;
        }


        public void incrementCost(Direction direction) {
            incrementCost(direction.getCost());
        }

        public void decrementCost(Direction direction) {
            incrementCost(-direction.getCost());
        }

        public int getShift() {
            return shift;
        }

        public void incrementShift(int value) {
            shift += value;
        }

        public void incrementShift(Direction direction) {
            incrementShift(direction.getDuration());
        }

        public int getIndex() {
            return index;
        }

        public int getPrevIndex() {
            return index - 1;
        }

        public int getNextIndex() {
            return index + 1;
        }

        public void incrementIndex() {
            index++;
        }

        public boolean isFoundFrom() {
            return foundFrom;
        }

        public void setFoundFrom(boolean foundFrom) {
            this.foundFrom = foundFrom;
        }

        public boolean isFoundTo() {
            return foundTo;
        }

        public void setFoundTo(boolean foundTo) {
            this.foundTo = foundTo;
        }

        @Override
        public String toString() {
            return "Context{" +
                    "cost=" + cost +
                    ", shift=" + shift +
                    ", index=" + index +
                    ", foundFrom=" + foundFrom +
                    ", foundTo=" + foundTo +
                    '}';
        }
    }
}
