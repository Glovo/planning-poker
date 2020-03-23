package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.CLIENT_ERROR;

final class MalformedMessageException extends ExceptionWithStatus {

    MalformedMessageException(final Throwable cause) {
        super("malformed message", CLIENT_ERROR, cause);
    }

}
