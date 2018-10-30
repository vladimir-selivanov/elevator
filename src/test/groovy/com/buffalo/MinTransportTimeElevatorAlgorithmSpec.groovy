package com.buffalo

import com.buffalo.model.State
import com.buffalo.transport.Command
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
        System.out.println("cost 1, 1 -> 9 = " + cost)

        then:
        actualCost == cost

        where:
        from | to || cost
        1    | 9  || 40
        9    | 1  || 40
    }
}
