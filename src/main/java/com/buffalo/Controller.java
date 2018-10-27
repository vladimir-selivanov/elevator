package com.buffalo;

import com.buffalo.model.Command;
import com.buffalo.model.Offer;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.eventbus.Message;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Controller extends AbstractVerticle {
    @Override
    public void start() {
        int elevatorCount = config().getInteger("elevatorCount");
        vertx.eventBus().consumer("command", message -> {
            Command command = (Command) message.body();
            System.out.println("Controller: command is " + command);

            List<Future> futures = new ArrayList<>(elevatorCount);
            for (int i = 1; i <= elevatorCount; i++) {
                Future<Message<Offer>> future = Future.future();
                vertx.eventBus().send("request" + i, command, future.completer());
                futures.add(future);
            }

            CompositeFuture.join(futures).setHandler(result -> {
                List<Offer> offers = futures.stream().map(future -> ((Message<Offer>) future.result()).body()).collect(Collectors.toList());
                System.out.println("Controller: offers are " + offers);
                Offer offer = offers.stream().min(Comparator.comparingInt(Offer::getCost)).get();
                System.out.println("Controller: elevator number is " + offer.getNumber());
                message.reply(offer.getNumber());
            });
        });
    }
}
