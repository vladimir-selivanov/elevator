package com.buffalo;

import com.buffalo.transport.Command;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.RandomUtils;

import java.util.stream.Stream;

public class PersonProviderVerticle extends AbstractVerticle {
    @Override
    public void start() {
        Stream<Command> commands = Stream.of(
                new Command(1, 10),
                new Command(1, 5),
                new Command(1, 1),
                new Command(9, 1),
                new Command(4, 1)
        );

        commands.forEach(command -> {
            vertx.setTimer(RandomUtils.nextInt(1, 10) * 1000L, handler -> {
                System.out.println("PersonProviderVerticle: command is " + command);
                vertx.eventBus().send("command", command, response -> {
                    System.out.println("PersonProviderVerticle: elevator number is " + response.result().body());
                });
            });
        });
    }
}
