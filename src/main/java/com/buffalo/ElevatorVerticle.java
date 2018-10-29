package com.buffalo;

import com.buffalo.model.Elevator;
import com.buffalo.transport.Command;
import com.buffalo.transport.Offer;
import io.vertx.core.AbstractVerticle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ElevatorVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ElevatorVerticle.class);
    private Elevator elevator;
    private int number;

    @Override
    public void start() {
        elevator = new Elevator();
        number = config().getInteger("number");

        LOGGER.info("[{}]: stated", number);

        vertx.eventBus().consumer("request" + number, message -> {
            Command command = (Command) message.body();
            LOGGER.info("[{}]: command is {}", number, command);
            Offer offer = new Offer(number, elevator.getCost(command));
            LOGGER.info("[{}]: offer is {}", number, offer);
            message.reply(offer);
        });
    }
}
