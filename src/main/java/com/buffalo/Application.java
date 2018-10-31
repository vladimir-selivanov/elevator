package com.buffalo;

import com.buffalo.transport.Command;
import com.buffalo.transport.CommandCodec;
import com.buffalo.transport.Offer;
import com.buffalo.transport.OfferCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Application extends AbstractVerticle {
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);
    // todo selivanov: Вынести в конфигурацию
    private static final int ELEVATOR_COUNT = 8;
    private static final int ELEVATOR_CAPACITY = 10;

    @Override
    public void start(final Future<Void> startFuture) {
        vertx.eventBus().registerDefaultCodec(Command.class, new CommandCodec());
        vertx.eventBus().registerDefaultCodec(Offer.class, new OfferCodec());

        for (int i = 1; i <= ELEVATOR_COUNT; i++) {
            JsonObject config = new JsonObject();
            config.put("number", i);
            config.put("capacity", ELEVATOR_CAPACITY);
            deploy(ElevatorVerticle.class, new DeploymentOptions().setConfig(config).setInstances(1));
        }
        JsonObject config = new JsonObject();
        config.put("elevatorCount", ELEVATOR_COUNT);
        deploy(ControllerVerticle.class, new DeploymentOptions().setConfig(config).setInstances(1));
        deploy(PersonProviderVerticle.class, new DeploymentOptions().setInstances(1));
        deploy(HelloWorldVerticle.class, new DeploymentOptions().setInstances(1));

        LOGGER.info("Module(s) and/or verticle(s) deployment...DONE");
        //startFuture.complete();
    }

    @Override
    public void stop(final Future<Void> stopFuture) {
        LOGGER.info("Undeploying verticle(s)...DONE");
        LOGGER.info("Application stopped successfully. Enjoy the elevator music while we're offline...");
        stopFuture.complete();
    }

    private void deploy(final Class<? extends AbstractVerticle> clazz, final DeploymentOptions options) {
        vertx.deployVerticle(clazz.getName(), options, handler -> {
            if (handler.succeeded()) {
//                LOGGER.info("{} started successfully (deployment identifier: {})", clazz.getSimpleName(), handler.result());
            } else {
                LOGGER.info("{} deployment failed due to: {}", clazz.getSimpleName(), handler.cause());
                //stop();
            }
        });
    }
}
