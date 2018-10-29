package com.buffalo;

import com.buffalo.transport.Command;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

public class PersonProviderVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonProviderVerticle.class);

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
                LOGGER.info("Command is {}", command);
                vertx.eventBus().send("command", command, response -> {
                    LOGGER.info("Elevator number is {}", response.result().body());
                });
            });
        });
    }
}
