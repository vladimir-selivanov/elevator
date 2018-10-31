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
        number = config().getInteger("number");
        int capacity = config().getInteger("capacity");
        elevator = new Elevator(number, capacity);

        LOGGER.info("[{}]: stated", number);

        vertx.eventBus().consumer("request" + number, message -> {
            Command command = (Command) message.body();
            LOGGER.info("[{}]: command is {}", number, command);
            Offer offer = new Offer(elevator, command);
            LOGGER.info("[{}]: offer is {}", number, offer);
            message.reply(offer);
        });
    }
}
