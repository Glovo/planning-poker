package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.WebSocketConnectionsCounter.getConnectionsCount;
import static io.vertx.core.logging.LoggerFactory.getLogger;
import static java.lang.Integer.parseInt;
import static lombok.AccessLevel.PACKAGE;

import io.reactivex.Completable;
import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.reactivex.core.AbstractVerticle;
import io.vertx.reactivex.core.http.ServerWebSocket;
import io.vertx.reactivex.ext.web.Router;
import io.vertx.reactivex.ext.web.handler.StaticHandler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PACKAGE)
final class ServerVerticle extends AbstractVerticle {

    private static final int PORT = parseInt(Configuration.SERVER_PORT.getValue());

    private final Logger log = getLogger(getClass());

    private final Handler<ServerWebSocket> webSocketHandler;

    @Override
    public final Completable rxStart() {
        final Router router = Router.router(vertx);

        router.route("/*")
              .handler(StaticHandler.create("frontend"));

        router.get("/health")
              .handler(context -> context.response()
                                         .setStatusCode(200)
                                         .end("all good, active connections count: " + getConnectionsCount()));

        log.info("starting server on port " + PORT);

        return vertx.createHttpServer()
                    .requestHandler(router)
                    .websocketHandler(webSocketHandler)
                    .rxListen(PORT)
                    .doOnSuccess(server -> log.info("server started listening on port " + PORT))
                    .ignoreElement();
    }

}
