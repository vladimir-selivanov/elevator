package com.buffalo.model;

import java.util.List;

/**
 * Место, базовый абстрактный класс для отражения места пребывания сотрудников
 */
public abstract class Place {
    private final int name;
    private List<Person> persons;

    /**
     * Создать место с указанным названием
     *
     * @param name название места
     */
    Place(int name) {
        this.name = name;
    }

    /**
     * @return Название места
     */
    public int getName() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Place)) return false;

        Place place = (Place) o;

        return getName() == place.getName();
    }

    @Override
    public int hashCode() {
        return getName();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() +
                '{' +
                "name='" + name + '\'' +
                ", persons=" + (persons == null ? 0 : persons.size()) +
                '}';
    }
}
