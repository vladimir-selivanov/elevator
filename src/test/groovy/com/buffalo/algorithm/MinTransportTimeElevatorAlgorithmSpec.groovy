package com.buffalo.algorithm

import com.buffalo.algorith.ElevatorAlgorithm
import com.buffalo.algorith.MinTransportTimeElevatorAlgorithm
import com.buffalo.model.State
import com.buffalo.model.States
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

    def "Прямой маршрут от начала #state1, #commandFrom -> #commandTo"() {
        given:
        def states = new States()
        states.add(new State(state1, state1, LocalDateTime.now()))
        Command command = new Command(commandFrom, commandTo)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command, Collections.emptyList())

        then:
        actualCost == cost

        where:
        state1 | commandFrom | commandTo || cost
        1      | 1           | 9         || 40
        9      | 9           | 1         || 40
    }

    def "Прямой маршрут от начала с остановкой #state1 -> #state2 -> #state3, #commandFrom -> #commandTo"() {
        given:
        def states = new States()
        states.add(new State(state1, state1, LocalDateTime.now()))
        states.addRoute(state1, state2)
        states.addRoute(state2, state3)
        Command command = new Command(commandFrom, commandTo)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command, Collections.emptyList())

        then:
        actualCost == cost

        where:
        state1 | state2 | state3 | commandFrom | commandTo || cost
        1      | 4      | 5      | 1           | 5         || 40
        5      | 4      | 1      | 5           | 1         || 40
    }

    def "Прямой маршрут с продлением #state1 -> #state2 -> #state3, #commandFrom -> #commandTo"() {
        given:
        def states = new States()
        states.add(new State(state1, state1, LocalDateTime.now()))
        states.addRoute(state1, state2)
        states.addRoute(state2, state3)
        Command command = new Command(commandFrom, commandTo)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command, Collections.emptyList())

        then:
        actualCost == cost

        where:
        state1 | state2 | state3 | commandFrom | commandTo || cost
        1      | 4      | 5      | 1           | 9         || 80
        1      | 4      | 5      | 4           | 9         || 80
    }

    def "Маршрут с разворотом #state1 -> #state2 -> #state3, #commandFrom -> #commandTo"() {
        given:
        def states = new States()
        states.add(new State(state1, state1, LocalDateTime.now()))
        states.addRoute(state1, state2)
        states.addRoute(state2, state3)
        Command command = new Command(commandFrom, commandTo)

        when:
        int actualCost = elevatorAlgorithm.getCost(states, command, Collections.emptyList())

        then:
        actualCost == cost

        where:
        state1 | state2 | state3 | commandFrom | commandTo || cost
        1      | 4      | 5      | 7           | 3         || 110
    }
}
