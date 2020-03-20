package com.glovoapp.planningpoker;

import static java.lang.Integer.parseInt;

import java.util.concurrent.atomic.AtomicInteger;

final class WebSocketConnectionsCounter {

    private static final int MAX_WEB_SOCKET_CONNECTIONS = parseInt(Configuration.MAX_WEB_SOCKET_CONNECTIONS.getValue());
    private static final AtomicInteger CONNECTIONS_COUNT = new AtomicInteger(0);

    static int getConnectionsCount() {
        return CONNECTIONS_COUNT.get();
    }

    static boolean canOpenNewConnection() {
        if (CONNECTIONS_COUNT.incrementAndGet() <= MAX_WEB_SOCKET_CONNECTIONS) {
            return true;
        } else {
            CONNECTIONS_COUNT.decrementAndGet();
            return false;
        }
    }

    static void onConnectionClosed() {
        CONNECTIONS_COUNT.decrementAndGet();
    }

}
