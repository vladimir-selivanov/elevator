package com.buffalo.model;

import org.apache.commons.lang3.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Человек, который пользуется лифтом
 */
public class Person {
    private static final List<Person> personsOnTheWay = new ArrayList<>();
    private static final List<Person> personsDone = new ArrayList<>();

    private String name;
    private Place currentPlace;
    private Place targetPlace;
    private int createTime = 0;
    private int waitTime = 0;
    private int moveTime = 0;

    public Person(String name, int currentFloor, int targetFloor) {
        this.name = name;
        this.currentPlace = new Floor(currentFloor);
        this.targetPlace = new Floor(targetFloor);
        this.createTime = Timer.getTime();

        if (!currentPlace.equals(targetPlace)) {
            synchronized (Person.class) {
                personsOnTheWay.add(this);
            }
        } else {
            synchronized (Person.class) {
                personsDone.add(this);
            }
        }
    }

    /**
     * @return Имя сотрудника (надо же его как-то назвать!)
     */
    public String getName() {
        return name;
    }

    /**
     * @return Текущее место нахождения
     */
    public Place getCurrentPlace() {
        return currentPlace;
    }

    /**
     * @return Тут все люди, которые ещё не доехали
     */
    public static List<Person> getPersonsOnTheWay() {
        return personsOnTheWay;
    }

    /**
     * @return Место, куда нужно попасть
     */
    public Place getTargetPlace() {
        return targetPlace;
    }

    /**
     * @return Тут все люди, которые уже доехали
     */
    public static List<Person> getPersonsDone() {
        return personsDone;
    }

    public void setCurrentPlace(Place currentPlace) {
        this.currentPlace = currentPlace;
        if (this.currentPlace.equals(targetPlace)) {
            synchronized (Person.class) {
                updateTime();
                personsOnTheWay.remove(this);
                personsDone.add(this);
            }
        }
    }

    /**
     * @return Время ожидания, в секундах
     */
    public int getWaitTime() {
        updateTime();
        return waitTime - createTime;
    }

    /**
     * @return Время движения в лифте, в секундах
     */
    public int getMoveTime() {
        updateTime();
        return moveTime - waitTime;
    }

    /**
     * @return Время создания человека ... от Рождества Христова :)
     */
    public int getCreateTime() {
        return createTime;
    }

    void setCreateTime(int createTime) {
        this.createTime = createTime;
    }

    private void updateTime() {
        if (currentPlace instanceof Elevator) {
            moveTime = Timer.getTime();
        } else if (currentPlace instanceof Floor) {
            waitTime = Timer.getTime();
            moveTime = waitTime;
        } else {
            throw new NotImplementedException("Нет реализации расчёта времени для класса " + currentPlace.getClass());
        }
    }
}
