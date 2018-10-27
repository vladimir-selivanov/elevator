package com.buffalo.model;

import java.util.List;

/**
 * Место, базовый абстрактный класс для отражения места пребывания сотрудников
 */
public abstract class Place {
    private String name;
    private List<Person> persons;

    /**
     * Создать место с указанным названием
     *
     * @param name название места
     */
    Place(String name) {
        this.name = name;
    }

    /**
     * @return Название места
     */
    public String getName() {
        return name;
    }

    /**
     * @return Сотрудники, находящиеся в указанном месте
     */
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
