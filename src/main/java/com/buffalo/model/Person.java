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
}
