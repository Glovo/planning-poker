package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.CLIENT_ERROR;

final class MalformedMessageException extends ExceptionWithStatus {

    MalformedMessageException(final String messageString) {
        super("malformed message '" + messageString + '\'', CLIENT_ERROR);
    }

}
