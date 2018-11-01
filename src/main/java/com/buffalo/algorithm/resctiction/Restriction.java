package com.buffalo.algorithm.resctiction;

/**
 * Органичение, требующее проверки
 */
public interface Restriction {
    /**
     * @return true, если ограничение сработало; иначе false
     */
    boolean denied();
}
