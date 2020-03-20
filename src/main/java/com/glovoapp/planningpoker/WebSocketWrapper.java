package com.glovoapp.planningpoker;

import static java.util.UUID.randomUUID;
import static lombok.AccessLevel.PACKAGE;

import io.reactivex.Completable;
import io.vertx.reactivex.core.http.ServerWebSocket;
import java.util.UUID;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = PACKAGE)
final class WebSocketWrapper {


    private final UUID id = randomUUID();
    private final ServerWebSocket delegate;

    final Completable write(final String message) {
        if (!delegate.isClosed()) {
            return delegate.rxWriteTextMessage(message);
        } else {
            return Completable.complete();
        }
    }

    final boolean isOpen() {
        return !delegate.isClosed();
    }

    final void close() {
        delegate.close();
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
        return "WebSocket(id=" + id + ")";
    }

}
