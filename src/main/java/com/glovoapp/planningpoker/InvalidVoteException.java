package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.CLIENT_ERROR;

final class InvalidVoteException extends ExceptionWithStatus {

    InvalidVoteException(final String vote) {
        super("invalid vote " + vote, CLIENT_ERROR);
    }

}
