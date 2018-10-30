package com.buffalo.model;

import com.buffalo.transport.Direction;
import org.apache.commons.collections4.CollectionUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class States extends ArrayList<State> {
    public void addState(int from, int to) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = now;
        if (CollectionUtils.isNotEmpty(this)) {
            State lastState = get(size() - 1);
            // todo selivanov: тут может быть ошибка при построении виртуального графика
            if (lastState.getDirection() != Direction.STOP || now.isBefore(lastState.getEnd())) {
                start = lastState.getEnd();
            }
        }
        add(new State(from, to, start));
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
