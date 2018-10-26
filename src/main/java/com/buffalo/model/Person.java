package com.buffalo.model;

/**
 * Человек, который пользуется лифтом
 *
 * @author zaykovandrey created 26/10/2018.
 */
public class Person {
    private String name;
    private Place currentPlace;
    private Place targetPlace;
    private int waitTime;
    private int moveTime;

    /**
     * @return Имя сотрудника (надо же его как-то назвать!)
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return Текущее место нахождения
     */
    public Place getCurrentPlace() {
        return currentPlace;
    }

    public void setCurrentPlace(Place currentPlace) {
        this.currentPlace = currentPlace;
    }

    /**
     * @return Место, куда нужно попасть
     */
    public Place getTargetPlace() {
        return targetPlace;
    }

    public void setTargetPlace(Place targetPlace) {
        this.targetPlace = targetPlace;
    }

    /**
     * @return Время ожидания, в секундах
     */
    public int getWaitTime() {
        return waitTime;
    }

    public void setWaitTime(int waitTime) {
        this.waitTime = waitTime;
    }

    /**
     * @return Время движения в лифте, в секундах
     */
    public int getMoveTime() {
        return moveTime;
    }

    public void setMoveTime(int moveTime) {
        this.moveTime = moveTime;
    }
}
