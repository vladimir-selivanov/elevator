package com.buffalo.model;

/**
 * Отсчёт времени
 */
public class Timer {
    public static int getTime() {
        return (int) (System.currentTimeMillis() / 1000);
    }
}
