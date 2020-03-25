package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.SERVER_ERROR;

final class SocketAlreadyClosedException extends ExceptionWithStatus {

    SocketAlreadyClosedException() {
        super("attempt to use a socket that has already been closed", SERVER_ERROR);
    }

}
