package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.CLIENT_ERROR;

final class UnrecognizedActionException extends ExceptionWithStatus {

    UnrecognizedActionException(final String action) {
        super("unrecognized action '" + action + '\'', CLIENT_ERROR);
    }

}
