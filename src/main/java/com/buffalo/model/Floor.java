package com.buffalo.model;

/**
 * Этаж. На нём могут находиться люди и лифты
 */
public class Floor extends Place {
    public static final int MAX_FLOORS = 16;
    /**
     * Создать этаж с указанным номером
     *
     * @param number номер этажа, 0...бесконечность
     */
    public Floor(int number) {
        super(number);
    }
}
