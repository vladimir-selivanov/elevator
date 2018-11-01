package com.buffalo.algorithm.resctiction;

import com.buffalo.model.Elevator;

/**
 * Максимальная вместимость лифта
 */
public class ElevatorSizeRestriction implements Restriction {
    private Elevator elevator;
    private int maxSize;

    public ElevatorSizeRestriction(Elevator elevator, int maxSize) {
        this.elevator = elevator;
        this.maxSize = maxSize;
    }

    /**
     * @return true превышено макс. количество пассажиров с учётом одного добавляемого пассажира
     */
    @Override
    public boolean denied() {
//        return elevator.getPersons().size() + 1 > maxSize;
        return false;
    }
}
