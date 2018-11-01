package com.buffalo;

import com.buffalo.transport.Command;
import com.buffalo.transport.Offer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class ControllerVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerVerticle.class);

    @Override
    public void start() {
        int elevatorCount = config().getInteger("elevatorCount");
        vertx.eventBus().consumer("command", message -> {
            Command command = (Command) message.body();
            LOGGER.info("Command is {}", command);

            List<Future> futures = new ArrayList<>(elevatorCount);
            for (int i = 1; i <= elevatorCount; i++) {
                Future<Message<Offer>> future = Future.future();
                vertx.eventBus().send("request" + i, command, future.completer());
                futures.add(future);
            }

            CompositeFuture.join(futures).setHandler(result -> {
                List<Offer> offers = futures.stream().map(future -> ((Message<Offer>) future.result()).body()).collect(Collectors.toList());
                LOGGER.info("Offers are {}", offers);
                Offer offer = offers.stream().min(Comparator.comparingInt(Offer::getCost)).get();
                LOGGER.info("Elevator number is {}", offer.getNumber());

                vertx.eventBus().send("transport" + offer.getNumber(), command, response -> {
                    Offer actualOffer = (Offer) response.result().body();
                    LOGGER.info("Actual elevator number is {}", actualOffer.getNumber());
                    message.reply(actualOffer);
                });
            });
        });
    }
}
