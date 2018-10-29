package com.buffalo.model;

import com.buffalo.transport.Command;
import com.buffalo.transport.Direction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Главный объект управления - лифт
 */
public class State extends Place {
    private Floor currentFloor;
    private Floor targetFloor;
    private Section section;

    // Эти поля создал для реализации алгоритма. Старые не удалил и к общему виду пока не приводил
    public static final State FIRST = new State(1, 1, LocalDateTime.now());
    private int from;
    private int to;
    private Set<Command> commands;
    private LocalDateTime start;
    private LocalDateTime end;
    private Direction direction;

    public State(int from, int to, LocalDateTime start) {
        super(null);
        this.from = from;
        this.to = to;
        this.commands = new HashSet<>();
        this.start = start;
        this.direction = Direction.getInstance(from, to);
        this.end = this.start.plusSeconds(direction.getCost());
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

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public Set<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        commands.add(command);
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getDuration() {
        return direction.getDuration();
    }

    public int getCost() {
        return direction.getCost();
    }

    public void shift(int seconds) {
        if (seconds < 1) {
            return;
        }
        start = start.plusSeconds(seconds);
        end = end.plusSeconds(seconds);
    }

    @Override
    public String toString() {
        return "State{" +
                "from=" + from +
                ", to=" + to +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
