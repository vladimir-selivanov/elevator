package com.buffalo;


public class ElevatorAlgorithmFactory {
    public static ElevatorAlgorithm getInstance() {
        return new RandomElevatorAlgorithm();
//        return new MinTransportTimeElevatorAlgorithm();
    }
}
