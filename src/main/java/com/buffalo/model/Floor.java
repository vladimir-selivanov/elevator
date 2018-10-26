package com.buffalo.model;

/**
 * Этаж. На нём могут находиться люди и лифты
 *
 * @author zaykovandrey created 26/10/2018.
 */
public class Floor extends Place {
    private int number;

    /**
     * Создать этаж с указанным номером
     *
     * @param number номер этажа, 0...бесконечность
     */
    public Floor(int number) {
        super("Этаж № " + number);
    }
}
