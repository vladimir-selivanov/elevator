package com.buffalo;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;

public class Application extends AbstractVerticle {
    @Override
    public void start(final Future<Void> startFuture) {
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
                System.out.println(clazz.getSimpleName() + " started successfully (deployment identifier: {})" + handler.result());
            } else {
                System.out.println(clazz.getSimpleName() + " deployment failed due to: " + handler.cause());
                //stop();
            }
        });
    }
}
