package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.SERVER_ERROR;

import com.glovoapp.planningpoker.Message.Action;

final class UnhandledActionException extends ExceptionWithStatus {

    UnhandledActionException(final Action action) {
        super("action is recognized but not handled '" + action + '\'', SERVER_ERROR);
    }

}
