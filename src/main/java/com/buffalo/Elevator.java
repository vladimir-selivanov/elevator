package com.buffalo;

import com.buffalo.transport.Command;
import com.buffalo.transport.Offer;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.RandomUtils;

public class Elevator extends AbstractVerticle {
    @Override
    public void start() {
        Integer number = config().getInteger("number");
        System.out.println("Elevator number " + number + " is stated");
        vertx.eventBus().consumer("request" + number, message -> {
            Command command = (Command) message.body();
            System.out.println("Elevator[" + number + "]: command is " + command);
            Offer offer = new Offer(number, RandomUtils.nextInt(1, 100));
            System.out.println("Elevator[" + number + "]: offer is " + offer);
            message.reply(offer);
        });
    }
}
