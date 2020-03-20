package com.glovoapp.planningpoker;

import java.util.Optional;
import lombok.Getter;

@Getter
enum Configuration {

    /**
     * Port that server will be listening on, also used for web socket connections.
     */
    SERVER_PORT("3000"),
    /**
     * Maximum number of sessions that may be open at the same time.
     */
    MAX_SESSIONS("100"),
    /**
     * Maximum number of web socket connections that may be open at the same time.
     */
    MAX_WEB_SOCKET_CONNECTIONS("300");

    private final String value;

    Configuration(final String defaultValue) {
        value = fromEnvironmentVariable(name()).orElse(defaultValue);
    }

    private static Optional<String> fromEnvironmentVariable(final String name) {
        return Optional.of(name)
                       .map(System::getenv);
    }

}
