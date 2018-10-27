package com.buffalo;

import com.buffalo.model.Command;
import com.buffalo.model.CommandCodec;
import com.buffalo.model.Offer;
import com.buffalo.model.OfferCodec;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;

public class Application extends AbstractVerticle {
    private static final int ELEVATOR_COUNT = 8;
    @Override
    public void start(final Future<Void> startFuture) {
        vertx.eventBus().registerDefaultCodec(Command.class, new CommandCodec());
        vertx.eventBus().registerDefaultCodec(Offer.class, new OfferCodec());

        for (int i = 1; i <= ELEVATOR_COUNT; i++) {
            JsonObject config = new JsonObject();
            config.put("number", i);
            deploy(Elevator.class, new DeploymentOptions().setConfig(config).setInstances(1));
        }
        JsonObject config = new JsonObject();
        config.put("elevatorCount", ELEVATOR_COUNT);
        deploy(Controller.class, new DeploymentOptions().setConfig(config).setInstances(1));
        deploy(Client.class, new DeploymentOptions().setInstances(1));
        deploy(HelloWorldVerticle.class, new DeploymentOptions().setInstances(1));

        System.out.println("Module(s) and/or verticle(s) deployment...DONE");
        //startFuture.complete();
    }

    @Override
    public void stop(final Future<Void> stopFuture) {
        System.out.println("Undeploying verticle(s)...DONE");
        System.out.println("Application stopped successfully. Enjoy the elevator music while we're offline...");
        stopFuture.complete();
    }

    private void deploy(final Class<? extends AbstractVerticle> clazz, final DeploymentOptions options) {
        vertx.deployVerticle(clazz.getName(), options, handler -> {
            if (handler.succeeded()) {
//                System.out.println(clazz.getSimpleName() + " started successfully (deployment identifier: {})" + handler.result());
            } else {
                System.out.println(clazz.getSimpleName() + " deployment failed due to: " + handler.cause());
                //stop();
            }
        });
    }
}
