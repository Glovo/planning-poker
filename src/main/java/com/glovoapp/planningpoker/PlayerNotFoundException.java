package com.glovoapp.planningpoker;

import static com.glovoapp.planningpoker.ExceptionWithStatus.Status.SERVER_ERROR;

final class PlayerNotFoundException extends ExceptionWithStatus {

    PlayerNotFoundException() {
        super("player could not be found", SERVER_ERROR);
    }

}
