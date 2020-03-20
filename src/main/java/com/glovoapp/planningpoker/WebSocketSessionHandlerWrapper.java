package com.glovoapp.planningpoker;

import static io.vertx.core.logging.LoggerFactory.getLogger;
import static java.lang.Integer.parseInt;
import static java.lang.System.currentTimeMillis;
import static java.util.Comparator.comparing;
import static lombok.AccessLevel.PACKAGE;
import static lombok.AccessLevel.PRIVATE;

import io.vertx.core.Handler;
import io.vertx.core.logging.Logger;
import io.vertx.reactivex.core.http.ServerWebSocket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = PACKAGE)
final class WebSocketSessionHandlerWrapper<AutoCloseableHandler extends Handler<ServerWebSocket> & AutoCloseable>
    implements Handler<ServerWebSocket> {

    private static final int MAX_SESSIONS = parseInt(Configuration.MAX_SESSIONS.getValue());

    private final Logger log = getLogger(getClass());
    private final ConcurrentHashMap<String, HandlerAndTimeOfInitialization> delegates = new ConcurrentHashMap<>();
    private final Supplier<AutoCloseableHandler> createDelegate;

    @Override
    public final void handle(final ServerWebSocket socket) {
        final String sessionId = socket.path();
        if (delegates.size() >= MAX_SESSIONS) {
            log.info("too many sessions open, removing oldest session");
            delegates.entrySet()
                     .stream()
                     .min(comparing(it -> it.getValue().initializedAt))
                     .ifPresent(oldestSession -> {
                         log.info("closing session with ID: " + oldestSession.getKey());
                         try {
                             oldestSession.getValue().handler.close();
                         } catch (final Exception exception) {
                             throw new RuntimeException(
                                 "unable to close oldest session, will not start a new one",
                                 exception
                             );
                         }
                         log.info("removing session with ID: " + oldestSession.getKey());
                         delegates.remove(oldestSession.getKey());
                     });
        }
        delegates.computeIfAbsent(
            sessionId,
            ignore -> new HandlerAndTimeOfInitialization(currentTimeMillis(), createDelegate.get())
        ).handler.handle(socket);
    }

    @RequiredArgsConstructor(access = PRIVATE)
    private final class HandlerAndTimeOfInitialization {

        private final long initializedAt;
        private final AutoCloseableHandler handler;

    }

}
