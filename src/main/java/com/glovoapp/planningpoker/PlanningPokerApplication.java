package com.glovoapp.planningpoker;

import static io.reactivex.Completable.defer;
import static io.vertx.core.logging.LoggerFactory.getLogger;
import static io.vertx.reactivex.core.Vertx.vertx;

import io.vertx.core.logging.Logger;
import io.vertx.reactivex.core.Vertx;

public final class PlanningPokerApplication {

    private static final Logger LOG = getLogger(PlanningPokerApplication.class);

    public static void main(String... args) {
        final Vertx vertx = vertx();
        vertx.rxDeployVerticle(new ServerVerticle(
            new WebSocketSessionHandlerWrapper<>(
                () -> new WebSocketConnectionHandler(
                    new ApplicationStateHandler()
                )
            )
        ))
             .doOnSuccess(verticleId -> LOG.info("verticle deployed"))
             .doOnError(error -> LOG.error("failed to deploy verticle", error))
             .ignoreElement()
             .onErrorResumeNext(error -> defer(vertx::rxClose))
             .subscribe();
    }

}
