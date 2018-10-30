package com.buffalo

import com.buffalo.model.State
import com.buffalo.transport.Command
import com.buffalo.transport.Direction
import org.apache.commons.collections4.CollectionUtils
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.LocalDateTime

@Unroll
class MinTransportTimeElevatorAlgorithmSpec extends Specification {
    @Shared
    ElevatorAlgorithm elevatorAlgorithm = new MinTransportTimeElevatorAlgorithm()

    def "Прямой маршрут #from -> #to"() {
        given:
        def states = new ArrayList<>()
        states.add(new State(from, from, LocalDateTime.now()))
        Command command = new Command(from, to)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command)

        then:
        actualCost == cost

        where:
        from | to || cost
        1    | 9  || 40
        9    | 1  || 40
    }

    def "Прямой маршрут с остановкой #from -> #stop -> #to"() {
        given:
        def states = new ArrayList<>()
        states.add(new State(from, from, LocalDateTime.now()))
        addRoute(states, from, stop)
        addRoute(states, stop, to)
        Command command = new Command(from, to)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command)

        then:
        actualCost == cost

        where:
        from | stop | to || cost
        1    | 4    | 5  || 40
        5    | 4    | 1  || 40
    }

    void addState(List<State> states, int from, int to) {
        LocalDateTime now = LocalDateTime.now()
        LocalDateTime start = now
        if (CollectionUtils.isNotEmpty(states)) {
            State lastState = states.get(states.size() - 1)
            // todo selivanov: тут может быть ошибка при построении виртуального графика
            if (lastState.getDirection() != Direction.STOP || now.isBefore(lastState.getEnd())) {
                start = lastState.getEnd()
            }
        }
        states.add(new State(from, to, start))
    }

    void addRoute(List<State> states, int from, int to) {
        if (from < to) {
            for (int i = from; i < to; i++) {
                addState(states, i, i + 1)
            }
        } else if (from > to) {
            for (int i = from; i > to; i--) {
                addState(states, i, i - 1)
            }
        }
        addState(states, to, to)
    }
}
