package com.glovoapp.planningpoker;

import static io.vertx.core.logging.LoggerFactory.getLogger;
import static java.lang.Math.max;
import static lombok.AccessLevel.PRIVATE;

import io.vertx.core.impl.ConcurrentHashSet;
import io.vertx.core.json.JsonObject;
import io.vertx.core.logging.Logger;
import io.vertx.reactivex.core.Vertx;
import io.vertx.reactivex.core.http.ServerWebSocket;
import io.vertx.reactivex.ext.web.RoutingContext;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor(access = PRIVATE)
final class MetricsService {

    @Getter
    @RequiredArgsConstructor(access = PRIVATE)
    private static class Metrics {

        private final int maximumSimultaneousConnections;
        private final int uniqueUsersCount;

    }

    private static final Logger LOG = getLogger(MetricsService.class);
    private static final AtomicInteger MAXIMUM_SIMULTANEOUS_CONNECTIONS = new AtomicInteger(0);
    private static final ConcurrentHashSet<String> CONNECTED_CLIENT_ADDRESSES = new ConcurrentHashSet<>();

    static void handleMetricsEndpoint(final RoutingContext context) {
        final Metrics metrics = new Metrics(
            MAXIMUM_SIMULTANEOUS_CONNECTIONS.get(),
            CONNECTED_CLIENT_ADDRESSES.size()
        );
        context.response()
               .setStatusCode(200)
               .end(JsonObject.mapFrom(metrics)
                              .encode());
    }

    static void onWebSocketConnectionOpen(final Vertx vertx,
                                          final ServerWebSocket serverWebSocket) {
        executeBlocking(vertx, () -> {
            final int connectionsCount = WebSocketConnectionsCounter.getConnectionsCount();
            final String clientAddress = serverWebSocket.remoteAddress()
                                                        .host();

            CONNECTED_CLIENT_ADDRESSES.add(clientAddress);

            MAXIMUM_SIMULTANEOUS_CONNECTIONS.updateAndGet(currentValue -> max(connectionsCount, currentValue));
        });
    }

    private static void executeBlocking(final Vertx vertx, final Runnable action) {
        vertx.<Void>rxExecuteBlocking(promise -> {
            try {
                action.run();
                promise.complete();
            } catch (final Exception exception) {
                promise.fail(exception);
            }
        }, false).doOnError(error -> LOG.warn("something crashed when processing metrics", error))
                 .onErrorComplete()
                 .subscribe();
    }

}
