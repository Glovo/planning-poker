package com.glovoapp.planningpoker;

import java.util.Optional;
import lombok.Getter;

@Getter
enum Configuration {

    SERVER_PORT("3000"),
    MAX_SESSIONS("100");

    private final String value;

    Configuration(final String defaultValue) {
        value = fromEnvironmentVariable(name()).orElse(defaultValue);
    }

    private static Optional<String> fromEnvironmentVariable(final String name) {
        return Optional.of(name)
                       .map(System::getenv);
    }

}
