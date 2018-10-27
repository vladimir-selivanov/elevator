package com.buffalo.model;

/**
 * Главный объект управления - лифт
 */
public class State extends Place {
    private Floor currentFloor;
    private Floor targetFloor;
    private Section section;

    /**
     * Создать лифт в указанной секции
     *
     * @param section секция, к которой относится лифт
     * @param name    название (номер) лифта
     */
    public State(Section section, String name) {
        super(name);
        this.section = section;
    }

    /**
     * @return Текущий этаж
     */
    public Floor getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(Floor currentFloor) {
        this.currentFloor = currentFloor;
    }

    /**
     * @return Целевой этаж
     */
    public Floor getTargetFloor() {
        return targetFloor;
    }

    public void setTargetFloor(Floor targetFloor) {
        this.targetFloor = targetFloor;
    }

    /**
     * @return Секция лифта
     */
    public Section getSection() {
        return section;
    }
}
