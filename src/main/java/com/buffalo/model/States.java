package com.buffalo.model;

import com.buffalo.transport.Direction;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class States extends ArrayList<State> {
    private int capacity;

    public States(int capacity) {
        this.capacity = capacity;
    }

    public State createState(int from, int to) {
        int capacity = this.capacity;
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now;
        if (CollectionUtils.isNotEmpty(this)) {
            State lastState = get(size() - 1);
            // todo selivanov: тут может быть ошибка при построении виртуального графика
            if (lastState.getDirection() != Direction.STOP || now.isBefore(lastState.getEnd())) {
                start = lastState.getEnd();
                capacity = lastState.getCapacity();
            }
        }
        return new State(from, to, capacity, start);
    }

    public State addState(int index, int from, int to) {
        State result = createState(from, to);
        if (index > 0) {
            add(index, result);
        } else {
            add(result);
        }
        return result;
    }

    public State addState(int from, int to) {
        return addState(-1, from, to);
    }

    public void addRoute(int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                addState(i, i + 1);
            }
        } else if (from > to) {
            for (int i = from; i > to; i--) {
                addState(i, i - 1);
            }
        }
        addState(to, to);
    }
}
