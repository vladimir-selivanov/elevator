package com.buffalo;

import com.buffalo.model.Person;
import com.buffalo.transport.Command;
import io.vertx.core.AbstractVerticle;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import static com.buffalo.model.Floor.MAX_FLOORS;

public class PersonProviderVerticle extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonProviderVerticle.class);

    @Override
    public void start() {
        Stream<Person> persons = Stream.generate(() -> new Person("Person #" + RandomUtils.nextInt(1000, 9999),
                RandomUtils.nextInt(1, MAX_FLOORS),
                RandomUtils.nextInt(1, MAX_FLOORS)));

        persons.forEach(person -> {
            vertx.setTimer(RandomUtils.nextInt(1, 10) * 1000L, handler -> {
                Command command = new Command(person);
                LOGGER.info("Command is {}", command);
                vertx.eventBus().send("command", command, response -> {
                    LOGGER.info("Elevator number is {}", response.result().body());
                });
            });
        });
    }
}
