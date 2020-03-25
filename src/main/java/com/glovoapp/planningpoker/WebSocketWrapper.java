package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.UNKNOWN_ERROR;
import static io.reactivex.Completable.error;
import static io.reactivex.Completable.fromAction;
import static io.vertx.core.logging.LoggerFactory.getLogger;
import static java.util.UUID.randomUUID;

import io.reactivex.Completable;
import io.vertx.core.logging.Logger;
import io.vertx.reactivex.core.http.ServerWebSocket;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import lombok.Getter;

@Getter
final class WebSocketWrapper {

    private final Logger log = getLogger(getClass());
    private final UUID id = randomUUID();
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private final ServerWebSocket delegate;
    private final Consumer<WebSocketWrapper> connectionClosedHandler;

    public WebSocketWrapper(final ServerWebSocket delegate,
                            final BiConsumer<String, WebSocketWrapper> messageHandler,
                            final Consumer<WebSocketWrapper> connectionClosedHandler) {
        this.delegate = delegate;
        delegate.textMessageHandler(message -> messageHandler.accept(message, this));
        this.connectionClosedHandler = connectionClosedHandler;
        delegate.closeHandler(onClose -> {
            log.info("close handler called, attempting to send message");
            write("IGNORE").subscribe();
        });
    }

    final Completable write(final String message) {
        if (!isClosed.get()) {
            return delegate.rxWriteTextMessage(message)
                           .onErrorResumeNext(error -> {
                               if (error instanceof IllegalStateException
                                   && "WebSocket is closed".equals(error.getMessage())) {
                                   log.info("socket " + this + " disconnected");
                                   isClosed.set(true);
                                   return fromAction(() -> connectionClosedHandler.accept(this)).andThen(
                                       delegate.rxClose(UNKNOWN_ERROR.getCode(), "connection already closed")
                                   );
                               } else {
                                   return error(error);
                               }
                           });
        } else {
            throw new SocketAlreadyClosedException();
        }
    }

    final boolean isOpen() {
        return !isClosed.get();
    }

    final void close() {
        if (!isClosed.getAndSet(true)) {
            delegate.close();
        } else {
            throw new SocketAlreadyClosedException();
        }
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(final Object other) {
        return other instanceof WebSocketWrapper
            && id.equals(((WebSocketWrapper) other).id);
    }

    @Override
    public String toString() {
        return "WebSocket(id=" + id + ", isClosed=" + isClosed.get() + ")";
    }

}
