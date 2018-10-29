package com.buffalo;

import com.buffalo.model.Elevator;
import com.buffalo.transport.Command;
import com.buffalo.transport.Offer;
import io.vertx.core.AbstractVerticle;

public class ElevatorVerticle extends AbstractVerticle {
    private Elevator elevator;
    private int number;

    @Override
    public void start() {
        elevator = new Elevator();
        number = config().getInteger("number");

        System.out.println("ElevatorVerticle[" + number + "]: stated");

        vertx.eventBus().consumer("request" + number, message -> {
            Command command = (Command) message.body();
            System.out.println("ElevatorVerticle[" + number + "]: command is " + command);
            Offer offer = new Offer(number, elevator.getCost(command));
            System.out.println("ElevatorVerticle[" + number + "]: offer is " + offer);
            message.reply(offer);
        });
    }
}
