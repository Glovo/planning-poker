package com.glovoapp.planningpoker;

import static lombok.AccessLevel.PACKAGE;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter(PACKAGE)
abstract class ExceptionWithStatus extends RuntimeException {

    @Getter
    @RequiredArgsConstructor
    enum Status {
        CLIENT_ERROR(1),
        SERVER_ERROR(2),
        CONNECTIONS_LIMIT_REACHED(3),
        UNKNOWN_ERROR(100);

        private final short code;

        Status(final int code) {
            this(validatedCode(code));
        }

        private static short validatedCode(final int code) {
            final short codeAsShort = (short) code;
            if (codeAsShort != code) {
                throw new IllegalArgumentException("invalid code: " + code);
            } else {
                return codeAsShort;
            }
        }
    }

    private final Status status;

    ExceptionWithStatus(final String message, final Status status) {
        super(message);
        this.status = status;
    }

    ExceptionWithStatus(final String message, final Status status, final Throwable cause) {
        super(message, cause);
        this.status = status;
    }

}
